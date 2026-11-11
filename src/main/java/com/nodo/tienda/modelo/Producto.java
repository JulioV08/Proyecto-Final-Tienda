package com.nodo.tienda.modelo;

import java.util.Locale;

/**
 * Producto disponible en el catálogo de la tienda.
 */
public class Producto {

    private final String nombre;
    private final String categoria;
    private final int precio;
    private final int stock;
    private final String icono;
    private final boolean destacado;

    public Producto(String nombre, String categoria, int precio, int stock,
                    String icono, boolean destacado) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.icono = icono;
        this.destacado = destacado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public String getPrecioTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", precio);
    }

    public int getStock() {
        return stock;
    }

    public String getIcono() {
        return icono;
    }

    public boolean isDestacado() {
        return destacado;
    }
}
