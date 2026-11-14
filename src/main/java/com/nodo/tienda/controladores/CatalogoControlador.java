package com.nodo.tienda.controladores;

import java.io.IOException;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


public class CatalogoControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label rol;

    @FXML
    private Label contador;

    @FXML
    private Label mensaje;

    @FXML
    private Label recomendado;

    @FXML
    private FlowPane contenedorProductos;

    @FXML
    private ComboBox<String> orden;

    @FXML
    private TextField busqueda;

    @FXML
    private ListView<String> categorias;

    @FXML
    private ListView<String> busquedas;

    @FXML
    private Slider precio;

    @FXML
    private CheckBox soloDisponibles;

    @FXML
    public void initialize() {
        Usuario usuario = RepositorioUsuarios.getUsuario();
        if (usuario == null) {
            saludo.setText("Invitado");
            rol.setText("Sin sesión");
        } else {
            saludo.setText(usuario.getNombreCompleto());
            rol.setText(usuario.getRol());
        }

        orden.getItems().addAll("Recomendados", "Precio: menor a mayor", "Precio: mayor a menor");
        orden.setValue("Recomendados");
        categorias.getSelectionModel().selectFirst();
        busquedas.setPlaceholder(new Label("Aún no hay búsquedas."));
        recomendado.setText("Disponible en el próximo avance.");
        cargarProductos();
    }

    private void cargarProductos() {
        contenedorProductos.getChildren().clear();
        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            cargarProducto(RepositorioProductos.obtener(i));
        }
        contador.setText(RepositorioProductos.tamanio() + " productos para ti");
    }

    private void cargarProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("vistas/producto.fxml"));
            Parent tarjeta = loader.load();
            TarjetaControlador controlador = loader.getController();
            controlador.setProducto(producto, this);
            contenedorProductos.getChildren().add(tarjeta);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la tarjeta del producto", e);
        }
    }

    public void verDetalle(Producto producto) {
        DetalleControlador.seleccionar(producto);
        App.irA("detalle");
    }

    @FXML
    private void onBuscar() {
        cargarProductos();
    }

    @FXML
    private void onSiguienteRecomendado() {
        recomendado.setText("Disponible en el próximo avance.");
    }

    @FXML
    private void onLimpiarBusquedas() {
        busquedas.getItems().clear();
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
