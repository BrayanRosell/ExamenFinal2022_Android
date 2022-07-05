package com.example.myappcatalogolibros.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappcatalogolibros.DetalleLibro;
import com.example.myappcatalogolibros.Entities.Libro;
import com.example.myappcatalogolibros.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {
    List<Libro> books;
    public LibroAdapter(List<Libro> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder vh, int position) {

        View itemView = vh.itemView;

        Libro libro = books.get(position);

        TextView tvTitulo = itemView.findViewById(R.id.tvTitulo);
        ImageView ivAvatar = itemView.findViewById(R.id.ivCaratula );


        tvTitulo.setText(libro.Titulo);
        Picasso.get().load(libro.CaratulaUrl).into(ivAvatar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetalleLibro.class);

                String contactJSON = new Gson().toJson(libro);
                intent.putExtra("LIBRO", contactJSON);
                //intent.putExtra("titulo", contact.Titulo);


                itemView.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return books.size();
    }

    class LibroViewHolder extends RecyclerView.ViewHolder{

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }
}

