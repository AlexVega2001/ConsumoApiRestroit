package com.example.consumoapirestroit.interfaces;

import com.example.consumoapirestroit.models.Indicador;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GorestService {
    public static final String base_url = "https://gorest.co.in/public/v1/users";

    @GET("data") Call<Indicador> getPost();
}
