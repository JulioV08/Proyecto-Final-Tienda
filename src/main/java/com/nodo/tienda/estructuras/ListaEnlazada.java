package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Usuario;


public class ListaEnlazada {

    private Nodo cabeza;
    private int tamanio;

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    public void agregar(Usuario usuario) {
        Nodo nuevo = new Nodo(usuario);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public Usuario obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public Usuario buscarPorCorreo(String correo) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getDato().getCorreo().equalsIgnoreCase(correo)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public void eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        if (indice == 0) {
            cabeza = cabeza.getSiguiente();
        } else {
            Nodo anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
        }
        tamanio--;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}


class Nodo {

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
