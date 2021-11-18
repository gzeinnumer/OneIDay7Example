package com.gzeinnumer.oneiday7example.data;

import com.gzeinnumer.oneiday7example.data.ApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    //private static final String base_url = "http://10.0.2.2/retrofit/";
    private static final String base_url = "https://talentpool.oneindonesia.id/api/";

    private static Retrofit setInit(){
        OkHttpClient client = new TokenInterceptor().getClient();
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiService getInstance(){
        return setInit().create(ApiService.class);
    }
}
