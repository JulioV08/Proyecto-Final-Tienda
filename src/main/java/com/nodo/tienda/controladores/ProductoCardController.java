package com.nodo.tienda.controladores;

import com.nodo.tienda.datos.RepositorioDeseos;
import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controlador de una tarjeta del catálogo.
 */
public class ProductoCardController {

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
    private Button favorito;

    private Producto producto;

    private CatalogoController catalogo;

    public void setProducto(Producto producto, CatalogoController catalogo) {
        this.producto = producto;
        this.catalogo = catalogo;

        icono.setText(producto.getIcono());
        categoria.setText(producto.getCategoria().toUpperCase());
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecioTexto());
        stock.setText("Stock: " + producto.getStock());

        etiqueta.setVisible(producto.isDestacado());
        etiqueta.setManaged(producto.isDestacado());
        actualizarFavorito();
    }

    private void actualizarFavorito() {
        if (favorito != null && producto != null) {
            favorito.setText(RepositorioDeseos.contiene(producto) ? "♥" : "♡");
        }
    }

    @FXML
    private void onFavorito() {
        if (producto != null && catalogo != null) {
            catalogo.alternarDeseo(producto);
            actualizarFavorito();
        }
    }

    @FXML
    private void onAgregar() {
        if (producto != null && catalogo != null) {
            catalogo.agregarAlCarrito(producto);
        }
    }
}
