package com.nodo.tienda.datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Locale;

import com.nodo.tienda.estructuras.Cola;
import com.nodo.tienda.estructuras.Pila;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;


public class Carrito {

    private static final Path CARRITO = Paths.get("carrito.txt");
    private static final Path HISTORIAL = Paths.get("historial.txt");
    private static final Cola<Producto> cola = new Cola<>();
    private static final Pila<Compra> historial = new Pila<>();
    private static boolean cargado = false;

    private Carrito() {
    }

    private static void asegurarCarga() {
        if (!cargado) {
            cargar();
            cargado = true;
        }
    }

    public static void agregar(Producto producto) {
        asegurarCarga();
        if (producto == null || cantidad(producto) >= producto.getStock()) {
            return;
        }
        cola.encolar(producto);
        guardarCarrito();
    }

    public static Producto obtener(int indice) {
        asegurarCarga();
        return cola.obtener(indice);
    }

    public static int tamanio() {
        asegurarCarga();
        return cola.tamanio();
    }

    public static int cantidad(Producto producto) {
        asegurarCarga();
        int cantidad = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static int tamanioUnico() {
        asegurarCarga();
        int cantidad = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            if (!apareceAntes(cola.obtener(i), i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static Producto obtenerUnico(int indice) {
        asegurarCarga();
        if (indice < 0 || indice >= tamanioUnico()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        int posicion = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            Producto producto = cola.obtener(i);
            if (!apareceAntes(producto, i)) {
                if (posicion == indice) {
                    return producto;
                }
                posicion++;
            }
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
    }

    public static int total() {
        asegurarCarga();
        int total = 0;
        for (int i = 0; i < cola.tamanio(); i++) {
            total += cola.obtener(i).getPrecio();
        }
        return total;
    }

    public static String totalTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", total());
    }

    public static String subtotalTexto(Producto producto) {
        int subtotal = producto.getPrecio() * cantidad(producto);
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", subtotal);
    }

    public static void quitarUno(Producto producto) {
        asegurarCarga();
        for (int i = cola.tamanio() - 1; i >= 0; i--) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cola.eliminar(i);
                guardarCarrito();
                return;
            }
        }
    }

    public static void quitar(Producto producto) {
        asegurarCarga();
        for (int i = cola.tamanio() - 1; i >= 0; i--) {
            if (mismoProducto(cola.obtener(i), producto)) {
                cola.eliminar(i);
            }
        }
        guardarCarrito();
    }

    public static void vaciar() {
        asegurarCarga();
        cola.vaciar();
        guardarCarrito();
    }

    private static boolean mismoProducto(Producto primero, Producto segundo) {
        return primero.getNombre().equals(segundo.getNombre());
    }

    private static boolean apareceAntes(Producto producto, int limite) {
        for (int i = 0; i < limite; i++) {
            if (mismoProducto(cola.obtener(i), producto)) {
                return true;
            }
        }
        return false;
    }

    public static int tamanioHistorial() {
        asegurarCarga();
        return historial.tamanio();
    }

    public static Compra obtenerCompra(int indice) {
        asegurarCarga();
        return historial.obtener(indice);
    }

    public static void finalizarCompra() {
        asegurarCarga();
        if (cola.estaVacia()) {
            return;
        }

        int cantidad = 0;
        int total = 0;
        Producto[] comprados = new Producto[cola.tamanio()];
        int posicion = 0;
        while (!cola.estaVacia()) {
            Producto producto = cola.desencolar();
            comprados[posicion] = producto;
            posicion++;
            cantidad++;
            total += producto.getPrecio();
        }

        for (int i = 0; i < comprados.length; i++) {
            Producto producto = comprados[i];
            if (producto == null || apareceAntesCompra(comprados, i)) {
                continue;
            }
            int unidades = 0;
            for (int j = 0; j < comprados.length; j++) {
                if (comprados[j] != null && comprados[j].getNombre().equals(producto.getNombre())) {
                    unidades++;
                }
            }
            producto.setStock(Math.max(0, producto.getStock() - unidades));
        }
        RepositorioProductos.guardar();

        LocalDate hoy = LocalDate.now();
        String fecha = hoy.getDayOfMonth() + "/" + hoy.getMonthValue() + "/" + hoy.getYear();
        historial.apilar(new Compra(fecha, cantidad, total, comprados));
        RepositorioUsuarios.incrementarPedidos();
        guardarCarrito();
        guardarHistorial();
    }

    private static boolean apareceAntesCompra(Producto[] productos, int limite) {
        for (int i = 0; i < limite; i++) {
            if (productos[i] != null && productos[i].getNombre().equals(productos[limite].getNombre())) {
                return true;
            }
        }
        return false;
    }

    private static void cargar() {
        cargarCarrito();
        cargarHistorial();
    }

    private static void cargarCarrito() {
        if (!Files.exists(CARRITO)) {
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(CARRITO, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    Producto producto = RepositorioProductos.buscarPorId(Integer.parseInt(linea.trim()));
                    if (producto != null) {
                        cola.encolar(producto);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar el carrito", e);
        }
    }

    private static void cargarHistorial() {
        if (!Files.exists(HISTORIAL)) {
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(HISTORIAL, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split("\\|", -1);
                if (datos.length < 4) {
                    continue;
                }
                int cantidad;
                int total;
                try {
                    cantidad = Integer.parseInt(datos[1]);
                    total = Integer.parseInt(datos[2]);
                } catch (NumberFormatException e) {
                    continue;
                }
                String[] ids = datos[3].split(",", -1);
                Producto[] productos = new Producto[ids.length];
                int indice = 0;
                for (String id : ids) {
                    if (id.trim().isEmpty()) {
                        continue;
                    }
                    try {
                        Producto producto = RepositorioProductos.buscarPorId(Integer.parseInt(id.trim()));
                        if (producto != null) {
                            productos[indice] = producto;
                            indice++;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
                Producto[] reales = new Producto[indice];
                System.arraycopy(productos, 0, reales, 0, indice);
                historial.apilar(new Compra(datos[0], cantidad, total, reales));
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar el historial", e);
        }
    }

    private static void guardarCarrito() {
        try (BufferedWriter escritor = Files.newBufferedWriter(CARRITO, StandardCharsets.UTF_8)) {
            for (int i = 0; i < cola.tamanio(); i++) {
                escritor.write(String.valueOf(cola.obtener(i).getId()));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar el carrito", e);
        }
    }

    private static void guardarHistorial() {
        try (BufferedWriter escritor = Files.newBufferedWriter(HISTORIAL, StandardCharsets.UTF_8)) {
            for (int i = 0; i < historial.tamanio(); i++) {
                Compra compra = historial.obtener(i);
                StringBuilder ids = new StringBuilder();
                Producto[] productos = compra.getProductos();
                for (int j = 0; j < productos.length; j++) {
                    if (j > 0) {
                        ids.append(",");
                    }
                    ids.append(productos[j].getId());
                }
                escritor.write(compra.getFecha() + "|" + compra.getCantidad() + "|"
                        + compra.getTotal() + "|" + ids);
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar el historial", e);
        }
    }
}
