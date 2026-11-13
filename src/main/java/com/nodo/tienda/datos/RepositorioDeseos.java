package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.ListaDeseos;
import com.nodo.tienda.modelo.Producto;

/**
 * Repositorio de la lista de deseos en memoria.
 */
public class RepositorioDeseos {

    private static final ListaDeseos deseos = new ListaDeseos();

    private RepositorioDeseos() {
    }

    public static void agregar(Producto producto) {
        deseos.agregar(producto);
    }

    public static void eliminar(Producto producto) {
        for (int i = 0; i < deseos.tamanio(); i++) {
            if (deseos.obtener(i).getNombre().equals(producto.getNombre())) {
                deseos.eliminar(i);
                return;
            }
        }
    }

    public static void eliminar(int indice) {
        deseos.eliminar(indice);
    }

    public static boolean contiene(Producto producto) {
        return deseos.contiene(producto);
    }

    public static Producto obtener(int indice) {
        return deseos.obtener(indice);
    }

    public static int tamanio() {
        return deseos.tamanio();
    }
}
