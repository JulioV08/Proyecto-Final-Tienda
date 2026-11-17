package com.nodo.tienda.estructuras;


public class Pila<T> {

    private NodoPila<T> cima;
    private int tamanio;

    public Pila() {
        cima = null;
        tamanio = 0;
    }

    public void apilar(T dato) {
        NodoPila<T> nuevo = new NodoPila<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    public T desapilar() {
        if (cima == null) {
            return null;
        }
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        tamanio--;
        return dato;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoPila<T> actual = cima;
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

    public void vaciar() {
        cima = null;
        tamanio = 0;
    }
}


class NodoPila<T> {

    private T dato;
    private NodoPila<T> siguiente;

    public NodoPila(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public NodoPila<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila<T> siguiente) {
        this.siguiente = siguiente;
    }
}
