package ar.com.hipnos.leo.istudy.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;


import java.util.List;

import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import ar.com.hipnos.leo.istudy.api.response.LoginResponse;
import ar.com.hipnos.leo.istudy.api.response.RegistrationResponse;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private static String grant_type = "password";
    private static Integer client_id = 2;
    private static String client_secret = "xbZbLbIgRhVFWC4YQCqBnKAPog5toRjxwKPZtk24";

//    private static String getToken(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//    }

    private static ApiInterface getApi(){
        Retrofit retrofit =new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://192.168.1.37/")
                .build();

        return retrofit.create(ApiInterface.class);
    }

    /**
     * @param email
     * @param password
     * @param callback
     */
    public static void register(String email, String password, Callback<RegistrationResponse> callback){
        getApi().register(email,password).enqueue(callback);
    }

    /**
     * @param username
     * @param password
     * @param callback
     */
    public static void login(String username, String password, Callback<LoginResponse> callback){
        getApi().login(grant_type, client_id, client_secret, username, password).enqueue(callback);
    }

    public static void getCarreras(String authorization, Callback<List<Carrera>> callback){
        getApi().getCarreras(authorization).enqueue(callback);
    }
}
