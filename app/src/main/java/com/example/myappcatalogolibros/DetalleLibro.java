package com.example.myappcatalogolibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappcatalogolibros.AppDatabase.AppDatabase;
import com.example.myappcatalogolibros.Dao.Libro_Dao;
import com.example.myappcatalogolibros.Entities.Libro;
import com.example.myappcatalogolibros.Factories.RetrofitFactory;
import com.example.myappcatalogolibros.Services.CatLibrosService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleLibro extends AppCompatActivity {
    AppDatabase db;
    Libro libs = new Libro();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        String contactJson = getIntent().getStringExtra("LIBRO");
        Libro librito = new Gson().fromJson(contactJson, Libro.class);
        Log.i("APP_VJ20202", "llega hasat a qui");

        TextView Titulo = findViewById(R.id.editTitulo);
        TextView Resumen = findViewById(R.id.editResumen);
        ImageView Imagen = findViewById(R.id.imageView);

        Titulo.setText(librito.Titulo);
        Resumen.setText(librito.Resumen);
        Picasso.get().load(librito.CaratulaUrl).into(Imagen);



        Button edit = findViewById(R.id.btnEditar);
        Button btnTienda = findViewById(R.id.btnTiendaVer);
        Button btnAddFavorito = findViewById(R.id.btnAddFavorito);
        Button btndeleteFavorito = findViewById(R.id.btnDeleteFAv);
        btndeleteFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitFactory.build(DetalleLibro.this);
                CatLibrosService service = retrofit.create(CatLibrosService.class);
                Call<Libro> call = service.delete(librito.Id);
                call.enqueue(new Callback<Libro>() {
                    @Override
                    public void onResponse(Call<Libro> call, Response<Libro> response) {

                    }

                    @Override
                    public void onFailure(Call<Libro> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(DetalleLibro.this,MainActivity.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = Titulo.getText().toString();
                String resumen = Resumen.getText().toString();

                librito.Titulo = titulo;
                librito.Resumen = resumen;

                Retrofit retrofit = RetrofitFactory.build(DetalleLibro.this);
                CatLibrosService service = retrofit.create(CatLibrosService.class);
                Call<Libro> call = service.edit(librito.Id,librito);
                call.enqueue(new Callback<Libro>() {
                    @Override
                    public void onResponse(Call<Libro> call, Response<Libro> response) {

                    }

                    @Override
                    public void onFailure(Call<Libro> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(DetalleLibro.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleLibro.this,MapsActivity.class);
                String contact2JSON = new Gson().toJson(librito);
                intent.putExtra("LIBRO", contact2JSON);
                startActivity(intent);
            }
        });
        btnAddFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = AppDatabase.getDatabase(getApplicationContext());



                Retrofit retrofit = RetrofitFactory.build(DetalleLibro.this);
                CatLibrosService service = retrofit.create(CatLibrosService.class);
                Call<Libro> call = service.getUnoBook(librito.Id);
                call.enqueue(new Callback<Libro>() {
                    @Override
                    public void onResponse(Call<Libro> call, Response<Libro> response) {
                        if(!response.isSuccessful()) {
                            Log.e("APP_VJ20202", "Error de aplicación");
                        } else {
                            Log.i("APP_VJ20202", "Respuesta Correcta");
                            libs = response.body();
                            Log.i("APP_VJ20202", "muestra:" + new Gson().toJson(libs));
                            saveInDatabase(libs);
                            Log.i("APP_VJ20202", "cadena sd:" + new Gson().toJson(libs));


                        }
                    }

                    @Override
                    public void onFailure(Call<Libro> call, Throwable t) {
                        Log.e("APP_VJ20202", "No hubo conectividad con el servicio web");
                    }
                });
                Log.i("APP_VJ20202", "cadena:" + new Gson().toJson(libs));
                Intent intent = new Intent(DetalleLibro.this,MainActivity.class);
                startActivity(intent);


            }
        });

    }
    private void saveInDatabase(Libro libs) {
        Log.i("APP_VJ20202", "llega:");
        Libro_Dao dao = db.libroDAO();
        dao.create(libs);

    }
}