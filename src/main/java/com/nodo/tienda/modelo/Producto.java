package com.nodo.tienda.modelo;

import java.io.File;
import java.util.Locale;


public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private int precio;
    private int stock;
    private String icono;
    private boolean destacado;
    private String descripcion;
    private String rutaImagen;

    public Producto(String nombre, String categoria, int precio, int stock,
                    String icono, boolean destacado, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.icono = icono;
        this.destacado = destacado;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getPrecioTexto() {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", precio);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getImagen() {
        if (rutaImagen != null && !rutaImagen.isEmpty()
                && (rutaImagen.startsWith("/") || new File(rutaImagen).exists())) {
            return rutaImagen;
        }
        String nombreMin = nombre == null ? "" : nombre.toLowerCase();
        if (nombreMin.contains("auricular")) {
            return "/com/nodo/tienda/vistas/imagenes/auriculares.png";
        }
        if (nombreMin.contains("teclado")) {
            return "/com/nodo/tienda/vistas/imagenes/teclado.png";
        }
        if (nombreMin.contains("altavoz")) {
            return "/com/nodo/tienda/vistas/imagenes/altavoz.png";
        }
        if (nombreMin.contains("ratón") || nombreMin.contains("raton")) {
            return "/com/nodo/tienda/vistas/imagenes/raton.png";
        }
        if (nombreMin.contains("lámpara") || nombreMin.contains("lampara")) {
            return "/com/nodo/tienda/vistas/imagenes/lampara.png";
        }
        if (nombreMin.contains("cargador")) {
            return "/com/nodo/tienda/vistas/imagenes/cargador.png";
        }
        if (nombreMin.contains("monitor")) {
            return "/com/nodo/tienda/vistas/imagenes/monitor.png";
        }
        if (nombreMin.contains("micrófono") || nombreMin.contains("microfono")) {
            return "/com/nodo/tienda/vistas/imagenes/microfono.png";
        }
        if (nombreMin.contains("batería") || nombreMin.contains("bateria")) {
            return "/com/nodo/tienda/vistas/imagenes/bateria.png";
        }
        if (nombreMin.contains("consola") || nombreMin.contains("control")) {
            return "/com/nodo/tienda/vistas/imagenes/consola.png";
        }
        if (nombreMin.contains("celular") || nombreMin.contains("teléfono") || nombreMin.contains("telefono")) {
            return "/com/nodo/tienda/vistas/imagenes/celular.png";
        }
        if (nombreMin.contains("nevera") || nombreMin.contains("refrigerador") || nombreMin.contains("electrodoméstico")) {
            return "/com/nodo/tienda/vistas/imagenes/electrodomestico.png";
        }
        return "/com/nodo/tienda/vistas/imagenes/producto.png";
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean estaDisponible() {
        return stock > 0;
    }

    @Override
    public String toString() {
        return nombre + " · " + getPrecioTexto();
    }
}
