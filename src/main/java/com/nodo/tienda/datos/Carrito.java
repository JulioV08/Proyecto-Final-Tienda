package com.nodo.tienda.datos;

import java.time.LocalDate;
import java.util.Locale;

import com.nodo.tienda.estructuras.Cola;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;

/**
 * Carrito de compras en memoria. Usa una cola circular.
 */
public class Carrito {

    private static final Cola cola = new Cola();

    private Carrito() {
    }

    public static void agregar(Producto producto) {
        cola.encolar(producto);
    }

    public static Producto obtener(int indice) {
        return cola.obtener(indice);
    }

    public static int tamanio() {
        return cola.tamanio();
    }

    public static int total() {
        int total = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            total += cola.obtener(i).getPrecio();
        }
        return total;
    }

    public static String totalTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", total());
    }

    public static void vaciar() {
        cola.vaciar();
    }

    public static void finalizarCompra() {
        if (cola.estaVacia()) {
            return;
        }

        int cantidad = 0;
        int total = 0;
        while (!cola.estaVacia()) {
            Producto producto = cola.desencolar();
            cantidad++;
            total += producto.getPrecio();
        }

        LocalDate hoy = LocalDate.now();
        String fecha = hoy.getDayOfMonth() + "/" + hoy.getMonthValue() + "/" + hoy.getYear();
        Historial.agregar(new Compra(fecha, cantidad, total));
    }
}
