package com.example.astromedics;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private FirebaseAuth mAuth;
    private boolean refreshingToken=false;
    private String token;



    @Override
    public Response intercept(Chain chain) throws IOException {
        this.mAuth=FirebaseAuth.getInstance();
        final Request request=chain.request();
        if(request.url().toString().contains("/app")){
            this.mAuth.getAccessToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if(task.isSuccessful()){
                        Log.i("INTERCEPTOR", "Token: "+task.getResult().getToken());
                        request.newBuilder()
                                .addHeader("authtoken",task.getResult().getToken())
                                .build();
                    }
                }
            });
            return chain.proceed(request);
        }else{
            return chain.proceed(request);
        }
    }
}
