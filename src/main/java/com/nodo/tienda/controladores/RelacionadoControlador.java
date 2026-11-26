package com.nodo.tienda.controladores;

import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class RelacionadoControlador {

    @FXML
    private ImageView imagen;

    @FXML
    private Label categoria;

    @FXML
    private Label nombre;

    @FXML
    private Label precio;

    private Producto producto;

    private DetalleControlador detalle;

    public void setProducto(Producto producto, DetalleControlador detalle) {
        this.producto = producto;
        this.detalle = detalle;

        Imagenes.aplicar(imagen, producto);
        categoria.setText(producto.getCategoria().toUpperCase());
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecioTexto());
    }

    @FXML
    private void onVer() {
        if (producto != null && detalle != null) {
            detalle.verRelacionado(producto);
        }
    }
}
