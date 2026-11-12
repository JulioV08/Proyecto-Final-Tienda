package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Historial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controlador del historial de compras.
 */
public class HistorialController {

    @FXML
    private ListView<String> lista;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        lista.getItems().clear();
        for (int i = 0; i < Historial.tamanio(); i++) {
            lista.getItems().add(Historial.obtener(i).toString());
        }
        mensaje.setText(Historial.tamanio() == 0 ? "Todavía no tienes compras." : "");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
