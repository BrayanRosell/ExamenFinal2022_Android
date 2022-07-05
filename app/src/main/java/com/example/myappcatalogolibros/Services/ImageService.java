package com.example.myappcatalogolibros.Services;
import com.example.myappcatalogolibros.Entities.Image;
import com.example.myappcatalogolibros.Entities.ImagePost;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageService {
    @POST("image")
    retrofit2.Call<Image> create(@Body ImagePost body);

}
