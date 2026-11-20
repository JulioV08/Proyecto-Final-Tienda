package com.nodo.tienda.datos;

import java.time.LocalDate;
import java.util.Locale;

import com.nodo.tienda.estructuras.Cola;
import com.nodo.tienda.estructuras.Pila;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;


public class Carrito {

    private static final Cola<Producto> cola = new Cola<>();
    private static final Pila<Compra> historial = new Pila<>();

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

    public static int cantidad(Producto producto) {
        int cantidad = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static int tamanioUnico() {
        int cantidad = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            if (!apareceAntes(cola.obtener(i), i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static Producto obtenerUnico(int indice) {
        if (indice < 0 || indice >= tamanioUnico()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        int posicion = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            Producto producto = cola.obtener(i);
            if (!apareceAntes(producto, i)) {
                if (posicion == indice) {
                    return producto;
                }
                posicion++;
            }
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
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

    public static String subtotalTexto(Producto producto) {
        int subtotal = producto.getPrecio() * cantidad(producto);
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", subtotal);
    }

    public static void quitarUno(Producto producto) {
        for (int i = cola.tamanio() - 1; i >= 0; i--) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cola.eliminar(i);
                return;
            }
        }
    }

    public static void quitar(Producto producto) {
        for (int i = cola.tamanio() - 1; i >= 0; i--) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cola.eliminar(i);
            }
        }
    }

    public static void vaciar() {
        cola.vaciar();
    }

    private static boolean mismoProducto(Producto primero, Producto segundo) {
        return primero.getNombre().equals(segundo.getNombre());
    }

    private static boolean apareceAntes(Producto producto, int limite) {
        for (int i = 0; i < limite; i++) {
            if (mismoProducto(cola.obtener(i), producto)) {
                return true;
            }
        }
        return false;
    }

    public static int tamanioHistorial() {
        return historial.tamanio();
    }

    public static Compra obtenerCompra(int indice) {
        return historial.obtener(indice);
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
        historial.apilar(new Compra(fecha, cantidad, total));
    }
}
