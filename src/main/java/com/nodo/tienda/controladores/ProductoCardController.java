package com.nodo.tienda.controladores;

import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
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
    }

    @FXML
    private void onAgregar() {
        if (producto != null && catalogo != null) {
            catalogo.agregarAlCarrito(producto);
        }
    }
}
