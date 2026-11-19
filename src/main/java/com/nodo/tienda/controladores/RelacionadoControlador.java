package com.nodo.tienda.controladores;

import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class RelacionadoControlador {

    @FXML
    private Label icono;

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

        icono.setText(producto.getIcono());
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
