package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Usuario;

/**
 * Lista simplemente enlazada para guardar usuarios.
 */
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

    public int tamanio() {
        return tamanio;
    }
}
