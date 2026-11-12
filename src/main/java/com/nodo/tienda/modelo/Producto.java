package com.nodo.tienda.modelo;

import java.util.Locale;


public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private int precio;
    private int stock;
    private String icono;
    private boolean destacado;
    private String descripcion;

    public Producto(String nombre, String categoria, int precio, int stock,
                    String icono, boolean destacado, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.icono = icono;
        this.destacado = destacado;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getPrecioTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", precio);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean estaDisponible() {
        return stock > 0;
    }

    @Override
    public String toString() {
        return nombre + " · " + getPrecioTexto();
    }
}
