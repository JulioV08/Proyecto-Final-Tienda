package com.nodo.tienda.controladores;

import java.util.Locale;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;


public class PedidosControlador {

    @FXML
    private TableView<Compra> tabla;

    @FXML
    private TableColumn<Compra, String> colFecha;

    @FXML
    private TableColumn<Compra, String> colCantidad;

    @FXML
    private TableColumn<Compra, String> colProductos;

    @FXML
    private TableColumn<Compra, String> colTotal;

    @FXML
    private TableColumn<Compra, String> colEstado;

    @FXML
    private Label totalPedidos;

    @FXML
    private Label totalUnidades;

    @FXML
    private Label totalVentas;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        if (RepositorioUsuarios.getUsuario() == null
                || !RepositorioUsuarios.getUsuario().esAdministrador()) {
            App.irA("login");
            return;
        }

        colFecha.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getFecha()));
        colCantidad.setCellValueFactory(d -> new ReadOnlyStringWrapper(String.valueOf(d.getValue().getCantidad())));
        colProductos.setCellValueFactory(d -> new ReadOnlyStringWrapper(nombres(d.getValue())));
        colProductos.setCellFactory(columna -> new TableCell<Compra, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label etiqueta = new Label(item);
                    etiqueta.setWrapText(true);
                    etiqueta.setMaxWidth(Double.MAX_VALUE);
                    setGraphic(etiqueta);
                }
            }
        });
        colTotal.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getTotalTexto()));
        colEstado.setCellValueFactory(d -> new ReadOnlyStringWrapper("Completado"));
        colEstado.setCellFactory(columna -> new TableCell<Compra, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label etiqueta = new Label("● " + item);
                    etiqueta.setTextFill(Color.web("#1b8553"));
                    etiqueta.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(etiqueta);
                }
            }
        });

        int unidades = 0;
        int ventas = 0;
        tabla.getItems().clear();
        for (int i = 0; i < Carrito.tamanioHistorial(); i++) {
            Compra compra = Carrito.obtenerCompra(i);
            tabla.getItems().add(compra);
            unidades += compra.getCantidad();
            ventas += compra.getTotal();
        }

        totalPedidos.setText(String.valueOf(Carrito.tamanioHistorial()));
        totalUnidades.setText(String.valueOf(unidades));
        totalVentas.setText(String.format(Locale.forLanguageTag("es-CO"), "$%,d", ventas));
        mensaje.setText(Carrito.tamanioHistorial() == 0
                ? "Cuando se finalice una compra aparecerá aquí."
                : "Pedidos: " + Carrito.tamanioHistorial() + " · Unidades: " + unidades);
    }

    private String nombres(Compra compra) {
        StringBuilder texto = new StringBuilder();
        Producto[] productos = compra.getProductos();
        for (int i = 0; i < productos.length; i++) {
            String nombre = productos[i].getNombre();
            boolean repetido = false;
            for (int j = 0; j < i; j++) {
                if (productos[j].getNombre().equals(nombre)) {
                    repetido = true;
                    break;
                }
            }
            if (repetido) {
                continue;
            }
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append(nombre);
        }
        return texto.length() == 0 ? "-" : texto.toString();
    }

    @FXML
    private void onVolver() {
        App.irA("admin");
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
