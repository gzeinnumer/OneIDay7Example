package com.gzeinnumer.oneiday7example.data;

import com.gzeinnumer.oneiday7example.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    //https://talentpool.oneindonesia.id/api/           user/login
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password);

    //https://talentpool.oneindonesia.id/api/           user/resgiter
    @FormUrlEncoded
    @POST("user/resgiter")
    Call<LoginResponse> register(@Field("username") String username,
                                    @Field("password") String password);


}
