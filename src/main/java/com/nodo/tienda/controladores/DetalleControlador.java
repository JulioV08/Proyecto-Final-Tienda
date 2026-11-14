package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class DetalleControlador {

    private static Producto productoSeleccionado;

    @FXML
    private Label icono;

    @FXML
    private Label etiqueta;

    @FXML
    private Label categoria;

    @FXML
    private Label nombre;

    @FXML
    private Label precio;

    @FXML
    private Label stock;

    @FXML
    private Label descripcion;

    public static void seleccionar(Producto producto) {
        productoSeleccionado = producto;
    }

    @FXML
    public void initialize() {
        Producto producto = productoSeleccionado;
        if (producto == null && RepositorioProductos.tamanio() > 0) {
            producto = RepositorioProductos.obtener(0);
        }
        if (producto == null) {
            return;
        }

        icono.setText(producto.getIcono());
        categoria.setText(producto.getCategoria().toUpperCase());
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecioTexto());
        descripcion.setText(producto.getDescripcion());
        etiqueta.setVisible(producto.isDestacado());
        etiqueta.setManaged(producto.isDestacado());
        stock.setText(producto.estaDisponible()
                ? "Disponible · " + producto.getStock() + " unidades"
                : "Agotado por el momento");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
