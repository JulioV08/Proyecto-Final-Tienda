package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;


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

    public Producto buscarPorId(int id) {
        NodoProducto actual = cabeza;
        while (actual != null) {
            if (actual.getDato().getId() == id) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean eliminarPorId(int id) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.getDato().getId() == id) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return true;
        }
        NodoProducto anterior = cabeza;
        while (anterior.getSiguiente() != null) {
            if (anterior.getSiguiente().getDato().getId() == id) {
                anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
                tamanio--;
                return true;
            }
            anterior = anterior.getSiguiente();
        }
        return false;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}


class NodoProducto {

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
