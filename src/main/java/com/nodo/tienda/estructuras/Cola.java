package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;

/**
 * Cola circular enlazada para guardar el carrito.
 */
public class Cola {

    private NodoProducto primero;
    private NodoProducto ultimo;
    private int tamanio;

    public Cola() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public void encolar(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
            nuevo.setSiguiente(nuevo);
        } else {
            nuevo.setSiguiente(primero);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        tamanio++;
    }

    public Producto desencolar() {
        if (primero == null) {
            return null;
        }
        Producto dato = primero.getDato();
        if (primero == ultimo) {
            primero = null;
            ultimo = null;
        } else {
            primero = primero.getSiguiente();
            ultimo.setSiguiente(primero);
        }
        tamanio--;
        return dato;
    }

    public Producto obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoProducto actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public void vaciar() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }
}
