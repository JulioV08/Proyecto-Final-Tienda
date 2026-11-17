package com.nodo.tienda.estructuras;


public class Cola<T> {

    private NodoCola<T> primero;
    private NodoCola<T> ultimo;
    private int tamanio;

    public Cola() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public void encolar(T dato) {
        NodoCola<T> nuevo = new NodoCola<>(dato);
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

    public T desencolar() {
        if (primero == null) {
            return null;
        }
        T dato = primero.getDato();
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

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoCola<T> actual = primero;
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


class NodoCola<T> {

    private T dato;
    private NodoCola<T> siguiente;

    public NodoCola(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public NodoCola<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola<T> siguiente) {
        this.siguiente = siguiente;
    }
}
