package com.example.astromedics.services;

import com.example.astromedics.model.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("{email_id}")
    Call<Person>getUser(@Path(value = "email_id", encoded = true) String url);

    @POST("app/user/createUser")
    Call<Void> saveUser(@Body Person person);
}
