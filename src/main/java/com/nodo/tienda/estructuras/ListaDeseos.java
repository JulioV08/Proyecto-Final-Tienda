package com.nodo.tienda.estructuras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.modelo.Producto;


public class ListaDeseos {

    private static final Path ARCHIVO = Paths.get("deseos.txt");
    private static NodoProducto primero;
    private static int tamanio;
    private static boolean cargado = false;

    private ListaDeseos() {
    }

    private static void asegurarCarga() {
        if (!cargado) {
            cargar();
            cargado = true;
        }
    }

    public static void agregar(Producto producto) {
        asegurarCarga();
        if (contieneDirecto(producto)) {
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
        guardar();
    }

    public static boolean contiene(Producto producto) {
        asegurarCarga();
        return contieneDirecto(producto);
    }

    private static boolean contieneDirecto(Producto producto) {
        NodoProducto actual = primero;
        while (actual != null) {
            if (actual.getDato().getNombre().equals(producto.getNombre())) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public static void eliminar(Producto producto) {
        asegurarCarga();
        for (int i = 0; i < tamanio; i++) {
            if (obtener(i).getNombre().equals(producto.getNombre())) {
                eliminar(i);
                return;
            }
        }
    }

    public static void eliminar(int indice) {
        asegurarCarga();
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
        guardar();
    }

    public static Producto obtener(int indice) {
        asegurarCarga();
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        NodoProducto actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public static int tamanio() {
        asegurarCarga();
        return tamanio;
    }

    private static void cargar() {
        if (!Files.exists(ARCHIVO)) {
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(ARCHIVO, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    Producto producto = RepositorioProductos.buscarPorId(Integer.parseInt(linea.trim()));
                    if (producto != null && !contieneDirecto(producto)) {
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
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la lista de deseos", e);
        }
    }

    private static void guardar() {
        try (BufferedWriter escritor = Files.newBufferedWriter(ARCHIVO, StandardCharsets.UTF_8)) {
            for (int i = 0; i < tamanio; i++) {
                escritor.write(String.valueOf(obtener(i).getId()));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar la lista de deseos", e);
        }
    }
}
