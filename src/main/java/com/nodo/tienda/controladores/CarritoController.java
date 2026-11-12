package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controlador de la vista del carrito.
 */
public class CarritoController {

    @FXML
    private ListView<String> lista;

    @FXML
    private Label total;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        actualizar();
    }

    private void actualizar() {
        lista.getItems().clear();
        for (int i = 0; i < Carrito.tamanio(); i++) {
            lista.getItems().add(Carrito.obtener(i).toString());
        }
        total.setText("Total: " + Carrito.totalTexto());
        mensaje.setText(Carrito.tamanio() == 0 ? "Tu carrito está vacío." : "");
    }

    @FXML
    private void onFinalizar() {
        if (Carrito.tamanio() == 0) {
            mensaje.setText("Agrega productos antes de finalizar.");
            return;
        }
        Carrito.finalizarCompra();
        App.irA("historial");
    }

    @FXML
    private void onVaciar() {
        Carrito.vaciar();
        actualizar();
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
