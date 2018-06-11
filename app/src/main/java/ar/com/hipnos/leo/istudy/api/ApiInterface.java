package ar.com.hipnos.leo.istudy.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    Call<RegistrationResponse> register(@Field("email") String email, @Field("password") String password );
}
