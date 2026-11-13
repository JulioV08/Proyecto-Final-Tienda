package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioDeseos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controlador de la lista de deseos.
 */
public class DeseosController {

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
        for (int i = 0; i < RepositorioDeseos.tamanio(); i++) {
            lista.getItems().add(RepositorioDeseos.obtener(i).toString());
        }
        mensaje.setText(RepositorioDeseos.tamanio() == 0
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
        RepositorioDeseos.eliminar(indice);
        actualizar();
        mensaje.setText("Producto eliminado de la lista.");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
