package com.example.myappcatalogolibros.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myappcatalogolibros.Entities.Libro;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Path;

@Dao
public interface Libro_Dao {
    @Query("SELECT * FROM libros")
    List<Libro> getAll();
    @Insert
    void create(Libro libro);
    @Query("DELETE FROM libros")
    void deleteAll();

    /*@Update
    void updateAll();*/



}
