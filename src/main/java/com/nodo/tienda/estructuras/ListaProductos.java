package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;

/**
 * Lista simplemente enlazada para guardar productos.
 */
public class ListaProductos {

    private NodoProducto cabeza;
    private int tamanio;

    public ListaProductos() {
        cabeza = null;
        tamanio = 0;
    }

    public void agregar(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoProducto actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public Producto obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoProducto actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public int tamanio() {
        return tamanio;
    }
}
