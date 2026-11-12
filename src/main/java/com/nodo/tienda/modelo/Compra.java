package com.nodo.tienda.modelo;

import java.util.Locale;

/**
 * Registro de una compra realizada.
 */
public class Compra {

    private final String fecha;
    private final int cantidad;
    private final int total;

    public Compra(String fecha, int cantidad, int total) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTotal() {
        return total;
    }

    public String getTotalTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", total);
    }

    @Override
    public String toString() {
        return fecha + " · " + cantidad
                + (cantidad == 1 ? " producto" : " productos")
                + " · " + getTotalTexto();
    }
}
