package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;


public class ListaDeseos {

    private static NodoProducto primero;
    private static int tamanio;

    private ListaDeseos() {
    }

    public static void agregar(Producto producto) {
        if (contiene(producto)) {
            return;
        }

        NodoProducto nuevo = new NodoProducto(producto);
        if (primero == null) {
            primero = nuevo;
        } else {
            NodoProducto actual = primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public static boolean contiene(Producto producto) {
        NodoProducto actual = primero;
        while (actual != null) {
            if (actual.getDato().getNombre().equals(producto.getNombre())) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public static void eliminar(Producto producto) {
        for (int i = 0; i < tamanio; i++) {
            if (obtener(i).getNombre().equals(producto.getNombre())) {
                eliminar(i);
                return;
            }
        }
    }

    public static void eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        if (indice == 0) {
            primero = primero.getSiguiente();
        } else {
            NodoProducto anterior = primero;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
        }
        tamanio--;
    }

    public static Producto obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        NodoProducto actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public static int tamanio() {
        return tamanio;
    }
}
