package com.example.astromedics.session;

import com.google.firebase.auth.FirebaseAuth;

public class Session {
    private FirebaseAuth mAuth;
    private static Session instance;
    private String email;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
            instance.mAuth = FirebaseAuth.getInstance();
        }
        return instance;
    }

    public String getEmail() {
        return mAuth.getCurrentUser()
                    .getEmail();
    }

    public void logOut(){
        mAuth.signOut();
    }
}
