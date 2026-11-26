package com.nodo.tienda.controladores;

import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class TarjetaControlador {

    @FXML
    private ImageView imagen;

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

    private CatalogoControlador catalogo;

    public void setProducto(Producto producto, CatalogoControlador catalogo) {
        this.producto = producto;
        this.catalogo = catalogo;

        Imagenes.aplicar(imagen, producto);
        categoria.setText(producto.getCategoria().toUpperCase());
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecioTexto());
        stock.setText(producto.estaDisponible()
                ? "Stock: " + producto.getStock()
                : "Agotado");

        etiqueta.setVisible(producto.isDestacado());
        etiqueta.setManaged(producto.isDestacado());
    }

    @FXML
    private void onAgregar() {
        if (producto != null && catalogo != null) {
            catalogo.agregarAlCarrito(producto);
        }
    }

    @FXML
    private void onFavorito() {
        if (producto != null && catalogo != null) {
            catalogo.alternarDeseo(producto);
        }
    }

    @FXML
    private void onVerDetalle() {
        if (producto != null && catalogo != null) {
            catalogo.verDetalle(producto);
        }
    }
}
