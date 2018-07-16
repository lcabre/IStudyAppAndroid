package ar.com.hipnos.leo.istudy.api;

import java.util.List;

import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import ar.com.hipnos.leo.istudy.api.modell.Materia;
import ar.com.hipnos.leo.istudy.api.response.LoginResponse;
import ar.com.hipnos.leo.istudy.api.response.RegistrationResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/register")
    Call<RegistrationResponse> register(@Field("email") String email, @Field("password") String password );

    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginResponse> login(
        @Field("grant_type") String grant_type,
        @Field("client_id") Integer client_id,
        @Field("client_secret") String client_secret,
        @Field("username") String username,
        @Field("password") String password
    );

    @GET("api/carreras")
    Call<List<Carrera>> getCarreras(@Header("Authorization") String authorization);

    @GET("api/carreras/list")
    Call<List<Carrera>> getListaCarreras(@Header("Authorization") String authorization);

    @GET("api/carreras/{idCarrera}/join")
    Call<ResponseBody> joinCarrera(@Header("Authorization") String authorization, @Path("idCarrera") Integer idCarrera);

    @GET("api/carreras/{idCarrera}/materias")
    Call<List<Materia>> getMaterias(@Header("Authorization") String authorization, @Path("idCarrera") Integer idCarrera);

    @GET("api/materias/{idMateria}")
    Call<Materia> getMateria(@Header("Authorization") String authorization, @Path("idMateria") Integer idMateria);

}
