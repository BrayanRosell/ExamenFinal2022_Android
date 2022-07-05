package com.example.myappcatalogolibros.Services;

import com.example.myappcatalogolibros.Entities.Libro;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CatLibrosService {
    @GET("Book")
    retrofit2.Call<List<Libro>> getBooks();
    @POST("Book")
    retrofit2.Call<Libro> create(@Body Libro libro);
    @PUT("Book/{id}")
    retrofit2.Call<Libro> edit(@Path("id") int Id, @Body Libro libro);
    @GET("Book/{id}")
    retrofit2.Call<Libro> getUnoBook(@Path("id") int Id);
    @DELETE("Book/{id}")
    retrofit2.Call<Libro> delete(@Path("id") int Id);


}
