package com.example.myappcatalogolibros.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "libros")
public class Libro {
    @PrimaryKey(autoGenerate = true)
    public  int Id;
    public  String CaratulaUrl;
    public  String Titulo;
    public  String Resumen;
    public  String Tienda;

    public Libro(int id, String caratulaUrl, String titulo, String resumen, String tienda) {
        Id = id;
        CaratulaUrl = caratulaUrl;
        Titulo = titulo;
        Resumen = resumen;
        Tienda = tienda;
    }

    public Libro() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCaratulaUrl() {
        return CaratulaUrl;
    }

    public void setCaratulaUrl(String caratulaUrl) {
        CaratulaUrl = caratulaUrl;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getResumen() {
        return Resumen;
    }

    public void setResumen(String resumen) {
        Resumen = resumen;
    }

    public String getTienda() {
        return Tienda;
    }

    public void setTienda(String tienda) {
        Tienda = tienda;
    }
}
