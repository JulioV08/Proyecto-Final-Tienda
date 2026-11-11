package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.ListaProductos;
import com.nodo.tienda.modelo.Producto;

/**
 * Repositorio de productos en memoria.
 */
public class RepositorioProductos {

    private static final ListaProductos productos = new ListaProductos();

    static {
        cargarDatosDePrueba();
    }

    public static void agregar(Producto producto) {
        productos.agregar(producto);
    }

    public static Producto obtener(int indice) {
        return productos.obtener(indice);
    }

    public static int tamanio() {
        return productos.tamanio();
    }

    private static void cargarDatosDePrueba() {
        agregar(new Producto("Auriculares Aura", "Audio", 89900, 12, "🎧", true));
        agregar(new Producto("Teclado mecánico Tacto", "Escritorio", 79900, 10, "⌨", false));
        agregar(new Producto("Altavoz portátil Pulso", "Audio", 59900, 8, "🔊", false));
        agregar(new Producto("Ratón inalámbrico Trazo", "Escritorio", 34900, 15, "🖱", false));
        agregar(new Producto("Lámpara de escritorio Halo", "Iluminación", 49900, 6, "💡", false));
        agregar(new Producto("Cargador inalámbrico Base", "Accesorios", 29900, 20, "🔌", false));
    }
}
