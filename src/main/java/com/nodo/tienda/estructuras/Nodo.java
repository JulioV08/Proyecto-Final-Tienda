package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Usuario;

/**
 * Nodo usado por la lista de usuarios.
 */
public class Nodo {

    private Usuario dato;
    private Nodo siguiente;

    public Nodo(Usuario dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Usuario getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
