package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;


public class HistorialControlador {

    @FXML
    private ListView<HBox> lista;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        lista.setFixedCellSize(60);
        lista.setCellFactory(vista -> new ListCell<HBox>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : item);
            }
        });

        lista.getItems().clear();
        for (int i = 0; i < Carrito.tamanioHistorial(); i++) {
            Compra compra = Carrito.obtenerCompra(i);
            HBox fila = new HBox(10);
            fila.setAlignment(Pos.CENTER_LEFT);

            Producto[] productos = compra.getProductos();
            int mostrados = 0;
            for (int j = 0; j < productos.length && mostrados < 4; j++) {
                if (yaMostrado(productos, j)) {
                    continue;
                }
                fila.getChildren().add(Imagenes.crear(productos[j], 44, 44));
                mostrados++;
            }

            fila.getChildren().add(new Label(compra.toString()));
            lista.getItems().add(fila);
        }
        mensaje.setText(Carrito.tamanioHistorial() == 0 ? "Todavía no tienes compras." : "");
    }

    private boolean yaMostrado(Producto[] productos, int indice) {
        for (int i = 0; i < indice; i++) {
            if (productos[i].getNombre().equals(productos[indice].getNombre())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
