package com.nodo.tienda.estructuras;

import com.nodo.tienda.modelo.Producto;

/**
 * Lista enlazada para guardar los productos favoritos.
 */
public class ListaDeseos {

    private NodoProducto primero;
    private int tamanio;

    public ListaDeseos() {
        primero = null;
        tamanio = 0;
    }

    public void agregar(Producto producto) {
        if (contiene(producto)) {
            return;
        }

        NodoProducto nuevo = new NodoProducto(producto);
        if (primero == null) {
            primero = nuevo;
        } else {
            NodoProducto actual = primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public boolean contiene(Producto producto) {
        NodoProducto actual = primero;
        while (actual != null) {
            if (actual.getDato().getNombre().equals(producto.getNombre())) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public void eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        if (indice == 0) {
            primero = primero.getSiguiente();
        } else {
            NodoProducto anterior = primero;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
        }
        tamanio--;
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
}
