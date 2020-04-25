package com.example.astromedics;

import android.app.Application;
import android.util.Log;

import com.example.astromedics.services.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static final String BASE_URL = "https://my-projecto-260001.appspot.com/";

    private static App INSTANCE;

    private UserService userService;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        Log.i(TAG,"Running on create..............................");
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                // for serialization
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.userService=retrofit.create(UserService.class);
    }

    public UserService getUserService(){
        return this.userService;
    }

}
