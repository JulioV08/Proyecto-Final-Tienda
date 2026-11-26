package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.util.Alertas;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class CarritoControlador {

    @FXML
    private ListView<HBox> lista;

    @FXML
    private Label total;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        lista.setFixedCellSize(72);
        lista.setPlaceholder(new Label("Tu carrito está vacío."));
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
        int unicos = Carrito.tamanioUnico();
        for (int i = 0; i < unicos; i++) {
            Producto producto = Carrito.obtenerUnico(i);

            HBox fila = new HBox(12);
            fila.setAlignment(Pos.CENTER_LEFT);

            VBox datos = new VBox(4);
            datos.getChildren().add(new Label(producto.getNombre()));
            datos.getChildren().add(new Label("x" + Carrito.cantidad(producto)
                    + " · " + Carrito.subtotalTexto(producto)));

            Button menos = new Button("−");
            menos.setOnAction(e -> quitarUno(producto));

            Button mas = new Button("+");
            mas.setOnAction(e -> agregarUno(producto));

            Button quitar = new Button("Quitar");
            quitar.setOnAction(e -> quitarProducto(producto));

            Region espacio = new Region();
            espacio.setPrefWidth(8.0);

            fila.getChildren().add(Imagenes.crear(producto, 56, 56));
            fila.getChildren().add(datos);
            fila.getChildren().add(espacio);
            fila.getChildren().add(menos);
            fila.getChildren().add(mas);
            fila.getChildren().add(quitar);
            lista.getItems().add(fila);
        }
        total.setText(Carrito.totalTexto());
        boolean vacio = unicos == 0;
        mensaje.setText(vacio ? "Agrega productos desde el catálogo." : " ");
        mensaje.setVisible(vacio);
        mensaje.setManaged(vacio);
    }

    private void agregarUno(Producto producto) {
        if (Carrito.cantidad(producto) < producto.getStock()) {
            Carrito.agregar(producto);
            mensaje.setText(" ");
            actualizar();
        } else {
            mensaje.setText("No hay más unidades disponibles de " + producto.getNombre() + ".");
            mensaje.setVisible(true);
            mensaje.setManaged(true);
        }
    }

    private void quitarUno(Producto producto) {
        Carrito.quitarUno(producto);
        actualizar();
    }

    private void quitarProducto(Producto producto) {
        Carrito.quitar(producto);
        actualizar();
    }

    @FXML
    private void onVaciar() {
        if (Carrito.tamanio() == 0) {
            return;
        }
        if (Alertas.confirmar("Vaciar carrito", "¿Seguro que quieres quitar todos los productos?")) {
            Carrito.vaciar();
            actualizar();
        }
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
        Compra compra = Carrito.obtenerCompra(0);
        String cliente = RepositorioUsuarios.getUsuario() == null
                ? "Invitado" : RepositorioUsuarios.getUsuario().getNombreCompleto();
        Alertas.factura("Compra realizada · " + compra.getNumero(), compra.getFactura(cliente));
        App.irA("historial");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }
}
