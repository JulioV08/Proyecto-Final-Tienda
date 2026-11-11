package com.nodo.tienda.controladores;

import java.io.IOException;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.Sesion;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * Controlador del catálogo de productos.
 */
public class CatalogoController {

    @FXML
    private Label saludo;

    @FXML
    private Label contador;

    @FXML
    private FlowPane contenedorProductos;

    @FXML
    private ComboBox<String> orden;

    @FXML
    public void initialize() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario != null) {
            saludo.setText(usuario.getNombre() + " " + usuario.getApellidos());
        } else {
            saludo.setText("Invitado");
        }

        orden.getItems().addAll("Recomendados", "Precio: menor a mayor", "Precio: mayor a menor");
        orden.setValue("Recomendados");

        int total = RepositorioProductos.tamanio();
        contador.setText(total + (total == 1 ? " producto para ti" : " productos para ti"));

        for (int i = 0; i < total; i++) {
            cargarProducto(RepositorioProductos.obtener(i));
        }
    }

    private void cargarProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("vistas/producto.fxml"));
            Parent tarjeta = loader.load();
            ProductoCardController controlador = loader.getController();
            controlador.setProducto(producto);
            contenedorProductos.getChildren().add(tarjeta);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la tarjeta del producto", e);
        }
    }

    @FXML
    private void onVolver() {
        App.irA("principal");
    }
}
