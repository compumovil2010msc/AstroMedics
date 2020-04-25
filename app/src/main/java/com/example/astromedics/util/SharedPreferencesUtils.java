package com.example.astromedics.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.astromedics.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import androidx.annotation.NonNull;

public class SharedPreferencesUtils {

    private static ObjectMapper mapper=new ObjectMapper();

    public static synchronized <T> T getSharedPref(String key, Class<T> clazz) {
        SharedPreferences preferences=App.get().getSharedPreferences("shared",App.get().MODE_PRIVATE);
        if(hasSharedPreferenceKey(key)){
            try {
                if(clazz==String.class){
                    return (T) preferences.getString(key,null);
                }else{
                    return mapper.readValue(preferences.getString(key,null),clazz);
                }
            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }else {
            throw new NoSuchElementException("Not found key");
        }
    }

    public static synchronized <T> List<T> getSharedPrefList(String key, Class<T[]> clazz) {
        SharedPreferences preferences=App.get().getSharedPreferences("shared",App.get().MODE_PRIVATE);
        if(hasSharedPreferenceKey(key)){
            try {
                return Arrays.asList(mapper.readValue(preferences.getString(key,null),clazz));
            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }else {
            throw new NoSuchElementException("Not found key");
        }
    }

    public static synchronized <T> void persistPref(@NonNull String key, @NonNull T object){
        SharedPreferences preferences=App.get().getSharedPreferences("shared",App.get().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        try {
            String objInString;
            if(object instanceof String){
                objInString=object.toString();
                prefsEditor.putString(key,object.toString());
            }else{
                 objInString= mapper.writeValueAsString(object);
                prefsEditor.putString(key,objInString);
            }
            prefsEditor.commit();
            Log.i("SharedPreferencesUtils","Saved: "+objInString);
        }catch (JsonProcessingException e){
            Log.e("SharedPreferencesUtils","Error parsing obj");
        }
    }

    public static synchronized  boolean hasSharedPreferenceKey(String key){
        SharedPreferences preferences=App.get().getSharedPreferences("shared",App.get().MODE_PRIVATE);
        return preferences.contains(key);
    }

    public static synchronized boolean removeKey(String key){
        SharedPreferences preferences=App.get().getSharedPreferences("shared",App.get().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        try{
            prefsEditor.remove(key);
            prefsEditor.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
