package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.estructuras.ListaDeseos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class DeseosControlador {

    @FXML
    private ListView<String> lista;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        actualizar();
    }

    private void actualizar() {
        lista.getItems().clear();
        for (int i = 0; i < ListaDeseos.tamanio(); i++) {
            lista.getItems().add(ListaDeseos.obtener(i).toString());
        }
        mensaje.setText(ListaDeseos.tamanio() == 0
                ? "Todavía no tienes productos en tu lista."
                : " ");
    }

    @FXML
    private void onQuitar() {
        int indice = lista.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            mensaje.setText("Selecciona un producto para quitarlo.");
            return;
        }
        ListaDeseos.eliminar(indice);
        actualizar();
        mensaje.setText("Producto eliminado de la lista.");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
