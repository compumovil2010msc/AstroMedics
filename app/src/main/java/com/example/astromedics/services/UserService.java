package com.example.astromedics.services;

import com.example.astromedics.model.Person;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("{email_id}")
    Single<Person> getUser(@Path(value = "email_id", encoded = true) String url);

    @POST("app/user/createUser")
    Single<Response<ResponseBody>> saveUser(@Body Person person, @Header("authtoken") String token);
}
