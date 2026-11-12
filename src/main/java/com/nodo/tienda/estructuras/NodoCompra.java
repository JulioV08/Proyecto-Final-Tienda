package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Compra;

/**
 * Nodo usado por la pila de compras.
 */
public class NodoCompra {

    private Compra dato;
    private NodoCompra siguiente;

    public NodoCompra(Compra dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Compra getDato() {
        return dato;
    }

    public NodoCompra getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCompra siguiente) {
        this.siguiente = siguiente;
    }
}
