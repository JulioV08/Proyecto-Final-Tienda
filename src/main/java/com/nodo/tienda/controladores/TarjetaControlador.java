package com.nodo.tienda.controladores;

import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class TarjetaControlador {

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

    private CatalogoControlador catalogo;

    public void setProducto(Producto producto, CatalogoControlador catalogo) {
        this.producto = producto;
        this.catalogo = catalogo;

        icono.setText(producto.getIcono());
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
    private void onVerDetalle() {
        if (producto != null && catalogo != null) {
            catalogo.verDetalle(producto);
        }
    }

    @FXML
    private void onDetalle(MouseEvent evento) {
        Object origen = evento.getTarget();
        while (origen instanceof Node) {
            if (origen instanceof Button) {
                return;
            }
            origen = ((Node) origen).getParent();
        }
        onVerDetalle();
    }
}
