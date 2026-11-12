package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Compra;

/**
 * Pila enlazada para guardar el historial de compras.
 */
public class Pila {

    private NodoCompra cima;
    private int tamanio;

    public Pila() {
        cima = null;
        tamanio = 0;
    }

    public void apilar(Compra compra) {
        NodoCompra nuevo = new NodoCompra(compra);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    public Compra desapilar() {
        if (cima == null) {
            return null;
        }
        Compra dato = cima.getDato();
        cima = cima.getSiguiente();
        tamanio--;
        return dato;
    }

    public Compra obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoCompra actual = cima;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cima == null;
    }
}
