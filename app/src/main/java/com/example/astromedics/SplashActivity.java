package com.example.astromedics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.astromedics.model.Person;
import com.example.astromedics.services.UserService;
import com.example.astromedics.util.SharedPreferencesUtils;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.example.astromedics.views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth=FirebaseAuth.getInstance();
        this.userService=App.get().getUserService();
        Intent intentMain=new Intent(this, MainActivity.class);
        Log.i("SPLASH_ACT",this.mAuth.getCurrentUser()==null?"NOT USER LOGGED":"USER LOOGED: "+this.mAuth.getCurrentUser().getEmail());
        if(this.mAuth.getCurrentUser()!=null){
            verifyData(this.mAuth.getCurrentUser().getEmail());
        }else{
            startActivity(intentMain);
        }
    }

    private void verifyData(String email){
        if(!SharedPreferencesUtils.hasSharedPreferenceKey("userLoggedIn")){
            this.userService.getUser(email).subscribe(person -> {
                if(person!=null){
                    redirect(person.isDoctor());
                }
            },err->{
                Log.e("SPLASH_ACT","error getting user: "+err.getMessage());
            });
        }else{
            Person pInShared=SharedPreferencesUtils.getSharedPref("userLoggedIn",Person.class);
            Log.i("SPLASH_ACT","Splash user in shared preferences: "+pInShared==null?"null":pInShared.toString());
            redirect(pInShared.isDoctor());
        }
    }

    private void redirect(boolean doctor) {
        if(doctor){
            Intent intent=new Intent(this, HomeTherapist.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this, HomeUserActivity.class);
            startActivity(intent);
        }
    }
}
