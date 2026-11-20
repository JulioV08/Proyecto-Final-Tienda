package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class CarritoControlador {

    @FXML
    private ListView<String> lista;

    @FXML
    private Label total;

    @FXML
    private Label mensaje;

    private Producto[] items = new Producto[0];

    @FXML
    public void initialize() {
        lista.setPlaceholder(new Label("Tu carrito está vacío."));
        actualizar();
    }

    private void actualizar() {
        lista.getItems().clear();
        int unicos = Carrito.tamanioUnico();
        items = new Producto[unicos];
        for (int i = 0; i < unicos; i++) {
            Producto producto = Carrito.obtenerUnico(i);
            items[i] = producto;
            lista.getItems().add(producto.getNombre() + " · x" + Carrito.cantidad(producto)
                    + " · " + Carrito.subtotalTexto(producto));
        }
        total.setText(Carrito.totalTexto());
        boolean vacio = unicos == 0;
        mensaje.setText(vacio ? "Agrega productos desde el catálogo." : "");
        mensaje.setVisible(vacio);
        mensaje.setManaged(vacio);
    }

    private Producto seleccionado() {
        int indice = lista.getSelectionModel().getSelectedIndex();
        if (indice < 0 || indice >= items.length) {
            mensaje.setText("Selecciona un producto de la lista.");
            mensaje.setVisible(true);
            mensaje.setManaged(true);
            return null;
        }
        return items[indice];
    }

    @FXML
    private void onAumentar() {
        Producto producto = seleccionado();
        if (producto != null && Carrito.cantidad(producto) < producto.getStock()) {
            Carrito.agregar(producto);
            actualizar();
        }
    }

    @FXML
    private void onDisminuir() {
        Producto producto = seleccionado();
        if (producto != null) {
            Carrito.quitarUno(producto);
            actualizar();
        }
    }

    @FXML
    private void onQuitar() {
        Producto producto = seleccionado();
        if (producto != null) {
            Carrito.quitar(producto);
            actualizar();
        }
    }

    @FXML
    private void onVaciar() {
        Carrito.vaciar();
        actualizar();
    }

    @FXML
    private void onFinalizar() {
        if (Carrito.tamanio() == 0) {
            mensaje.setText("No hay productos para finalizar la compra.");
            mensaje.setVisible(true);
            mensaje.setManaged(true);
            return;
        }
        Carrito.finalizarCompra();
        App.irA("historial");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
