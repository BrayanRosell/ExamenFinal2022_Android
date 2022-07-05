package com.example.myappcatalogolibros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.myappcatalogolibros.Adapter.LibroAdapter;
import com.example.myappcatalogolibros.AppDatabase.AppDatabase;
import com.example.myappcatalogolibros.Dao.Libro_Dao;
import com.example.myappcatalogolibros.Entities.Libro;
import com.google.gson.Gson;

import java.util.List;

public class ListarFavoritos extends AppCompatActivity {
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_favoritos);

        db = AppDatabase.getDatabase(getApplicationContext());
        Libro_Dao dao = db.libroDAO();
        List<Libro> libs = dao.getAll();

        LibroAdapter adapter = new LibroAdapter(libs);
        RecyclerView rv = findViewById(R.id.rvLibros2);
        Log.i("APP_VJ20202", "cadenafinal:" + new Gson().toJson(libs));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
    }
}