package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.estructuras.ListaDeseos;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;


public class DeseosControlador {

    @FXML
    private ListView<HBox> lista;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        lista.setFixedCellSize(64);
        lista.setCellFactory(vista -> new ListCell<HBox>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : item);
            }
        });
        actualizar();
    }

    private void actualizar() {
        lista.getItems().clear();
        for (int i = 0; i < ListaDeseos.tamanio(); i++) {
            HBox fila = new HBox(12);
            fila.setAlignment(Pos.CENTER_LEFT);
            fila.getChildren().add(Imagenes.crear(ListaDeseos.obtener(i), 48, 48));
            fila.getChildren().add(new Label(ListaDeseos.obtener(i).getNombre()));
            lista.getItems().add(fila);
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
