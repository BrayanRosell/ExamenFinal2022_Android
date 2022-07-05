package com.example.myappcatalogolibros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myappcatalogolibros.Adapter.LibroAdapter;
import com.example.myappcatalogolibros.Entities.Libro;
import com.example.myappcatalogolibros.Factories.RetrofitFactory;
import com.example.myappcatalogolibros.Services.CatLibrosService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListarTodos extends AppCompatActivity {
    List<Libro> libros = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);
        FloatingActionButton btnRegister = findViewById(R.id.fab);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarTodos.this,RegistrarActivity.class);
                startActivity(intent);

            }
        });
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
                    LibroAdapter adapter = new LibroAdapter(libros);

                    RecyclerView rv = findViewById(R.id.rvLibros);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                Log.e("APP_VJ20202", "No hubo conectividad con el servicio web");
            }
        });

    }
}