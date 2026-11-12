package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.Pila;
import com.nodo.tienda.modelo.Compra;

/**
 * Historial de compras en memoria. Usa una pila.
 */
public class Historial {

    private static final Pila pila = new Pila();

    private Historial() {
    }

    public static void agregar(Compra compra) {
        pila.apilar(compra);
    }

    public static Compra obtener(int indice) {
        return pila.obtener(indice);
    }

    public static int tamanio() {
        return pila.tamanio();
    }
}
