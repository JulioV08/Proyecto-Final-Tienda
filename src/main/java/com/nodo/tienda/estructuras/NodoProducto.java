package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;

/**
 * Nodo usado por la lista de productos.
 */
public class NodoProducto {

    private Producto dato;
    private NodoProducto siguiente;

    public NodoProducto(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Producto getDato() {
        return dato;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }
}
