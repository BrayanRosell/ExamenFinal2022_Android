package com.example.myappcatalogolibros.AppDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myappcatalogolibros.Dao.Libro_Dao;
import com.example.myappcatalogolibros.Entities.Libro;

@Database(entities = {Libro.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Libro_Dao libroDAO();

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "com.example.androidvj20221.database.contacts.db")
                .allowMainThreadQueries()
                .build();
    }
}
