package com.example.myappcatalogolibros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myappcatalogolibros.AppDatabase.AppDatabase;
import com.example.myappcatalogolibros.Dao.Libro_Dao;
import com.example.myappcatalogolibros.Entities.Libro;
import com.example.myappcatalogolibros.Factories.RetrofitFactory;
import com.example.myappcatalogolibros.Services.CatLibrosService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteFavorito extends AppCompatActivity {

    List<Libro> libros = new ArrayList<>();
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorito);
        String contactJson = getIntent().getStringExtra("LIBRO");
        Libro librito = new Gson().fromJson(contactJson, Libro.class);


        Retrofit retrofit = RetrofitFactory.build(this);
        CatLibrosService service = retrofit.create(CatLibrosService.class);
        Call<List<Libro>> call = service.getBooks();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if(!response.isSuccessful()) {
                    Log.e("APP_VJ20202", "Error de aplicación");
                } else {
                    Log.i("APP_VJ20202", "Respuesta Correcta");
                    libros = response.body();
                    Log.i("APP_VJ20202", "muestra:" + new Gson().toJson(libros));
                    saveInDatabase(libros);
                    Log.i("APP_VJ20202", "cadena:" + new Gson().toJson(libros));



                    /*PeliculaAdapter adapter = new PeliculaAdapter(peliculas);

                    RecyclerView rv = findViewById(R.id.rvContacts);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);*/

                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                Log.e("APP_VJ20202", "No hubo conectividad con el servicio web");
            }
        });
    }
    private void saveInDatabase(List<Libro> libros) {
        Log.i("APP_VJ20202", "llega:");
        Libro_Dao dao = db.libroDAO();
        dao.deleteAll();
        for (Libro item : libros){
            dao.create(item);
        }
    }
}