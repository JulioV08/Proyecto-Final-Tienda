package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class HistorialControlador {

    @FXML
    private ListView<String> lista;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        lista.getItems().clear();
        for (int i = 0; i < Carrito.tamanioHistorial(); i++) {
            lista.getItems().add(Carrito.obtenerCompra(i).toString());
        }
        mensaje.setText(Carrito.tamanioHistorial() == 0 ? "Todavía no tienes compras." : "");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
