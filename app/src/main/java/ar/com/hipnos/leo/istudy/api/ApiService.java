package ar.com.hipnos.leo.istudy.api;

import com.google.gson.Gson;


import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private static ApiInterface getApi(){
        Retrofit retrofit =new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://192.168.1.37/api/")
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static void register(String email, String password, Callback<RegistrationResponse> callback){
        getApi().register(email,password).enqueue(callback);
    }
}
