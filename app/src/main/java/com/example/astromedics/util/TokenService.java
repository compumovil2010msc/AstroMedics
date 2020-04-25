package com.example.astromedics.util;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import io.reactivex.Observable;


public class TokenService{
    public static Observable<String> getTokenObservable(){
        return Observable.create(emitter -> {
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user==null){
                Log.i("TOKEN_SERVICE","User not logged in firebase");
                emitter.onError(new RuntimeException("Not logged in firebase"));
                emitter.onComplete();
            }else{
                user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                            emitter.onNext(token);
                        }else{
                            emitter.onError(new RuntimeException("Failed to get token"));
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
