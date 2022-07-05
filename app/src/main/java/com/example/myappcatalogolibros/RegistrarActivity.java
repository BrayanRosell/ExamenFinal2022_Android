package com.example.myappcatalogolibros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myappcatalogolibros.Entities.Image;
import com.example.myappcatalogolibros.Entities.ImagePost;
import com.example.myappcatalogolibros.Entities.Libro;
import com.example.myappcatalogolibros.Factories.RetrofitFactory;
import com.example.myappcatalogolibros.Factories.RetrofitImageFactory;
import com.example.myappcatalogolibros.Services.CatLibrosService;
import com.example.myappcatalogolibros.Services.ImageService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrarActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1000;
    static final int REQUEST_CAMERA_PERMISSION = 100;
    ImageView ivImage;
    String imageBASE64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnTomarfoto = findViewById(R.id.btnTomarFoto);
        EditText Titulo = findViewById(R.id.etxtTitulo);
        EditText Resumen = findViewById(R.id.eTxtResumen);
        EditText Tienda = findViewById(R.id.eTxtTienda);
        ivImage = findViewById(R.id.imageCaratulaedtxt);

        btnTomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }


            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitFactory.build(getApplicationContext());
                CatLibrosService service = retrofit.create(CatLibrosService.class);

                Libro libro = new Libro();
                libro.Titulo =Titulo.getText().toString();
                libro.Resumen =Resumen.getText().toString();
                libro.Tienda =Tienda.getText().toString();

                ImagePost imagePost = new ImagePost();
                imagePost.image = imageBASE64;
                Retrofit retrofitImage = RetrofitImageFactory.build(getApplicationContext());
                ImageService imageService = retrofitImage.create(ImageService.class);

                Call<Image> callImage = imageService.create(imagePost);
                callImage.enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(Call<Image> call, Response<Image> response) {
                        libro.CaratulaUrl = response.body().data.link;
                        Call<Libro> callMovie = service.create(libro);

                        callMovie.enqueue(new Callback<Libro>() {
                            @Override
                            public void onResponse(Call<Libro> call, Response<Libro> response) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Libro> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {

                    }
                });


                Intent intent = new Intent(RegistrarActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            imageBASE64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            ivImage.setImageBitmap(imageBitmap);

        }

    }
}