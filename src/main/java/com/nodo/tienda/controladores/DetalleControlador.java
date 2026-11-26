package com.nodo.tienda.controladores;

import java.io.IOException;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.estructuras.ListaDeseos;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.util.Imagenes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


public class DetalleControlador {

    private static Producto productoSeleccionado;

    @FXML
    private ImageView imagen;

    @FXML
    private Label etiqueta;

    @FXML
    private Label categoria;

    @FXML
    private Label nombre;

    @FXML
    private Label precio;

    @FXML
    private Label stock;

    @FXML
    private Label descripcion;

    @FXML
    private VBox seccionRelacionados;

    @FXML
    private FlowPane relacionados;

    public static void seleccionar(Producto producto) {
        productoSeleccionado = producto;
    }

    @FXML
    public void initialize() {
        Producto producto = productoSeleccionado;
        if (producto == null && RepositorioProductos.tamanio() > 0) {
            producto = RepositorioProductos.obtener(0);
        }
        if (producto == null) {
            return;
        }

        Imagenes.aplicar(imagen, producto);
        categoria.setText(producto.getCategoria().toUpperCase());
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecioTexto());
        descripcion.setText(producto.getDescripcion());
        etiqueta.setVisible(producto.isDestacado());
        etiqueta.setManaged(producto.isDestacado());
        stock.setText(producto.estaDisponible()
                ? "Disponible · " + producto.getStock() + " unidades"
                : "Agotado por el momento");

        cargarRelacionados(producto);
    }

    private void cargarRelacionados(Producto producto) {
        relacionados.getChildren().clear();
        int encontrados = 0;
        for (int i = 0; i < RepositorioProductos.tamanio() && encontrados < 3; i++) {
            Producto candidato = RepositorioProductos.obtener(i);
            if (!candidato.getNombre().equals(producto.getNombre())
                    && candidato.getCategoria().equals(producto.getCategoria())) {
                cargarRelacionado(candidato);
                encontrados++;
            }
        }

        boolean hayRelacionados = encontrados > 0;
        seccionRelacionados.setVisible(hayRelacionados);
        seccionRelacionados.setManaged(hayRelacionados);
    }

    private void cargarRelacionado(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("vistas/relacionado.fxml"));
            Parent tarjeta = loader.load();
            RelacionadoControlador controlador = loader.getController();
            controlador.setProducto(producto, this);
            relacionados.getChildren().add(tarjeta);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar el producto relacionado", e);
        }
    }

    public void verRelacionado(Producto producto) {
        seleccionar(producto);
        App.irA("detalle");
    }

    @FXML
    private void onAgregar() {
        if (productoSeleccionado != null && productoSeleccionado.estaDisponible()) {
            Carrito.agregar(productoSeleccionado);
        }
    }

    @FXML
    private void onFavorito() {
        if (productoSeleccionado != null) {
            if (ListaDeseos.contiene(productoSeleccionado)) {
                ListaDeseos.eliminar(productoSeleccionado);
            } else {
                ListaDeseos.agregar(productoSeleccionado);
            }
        }
    }

    @FXML
    private void onCarrito() {
        App.irA("carrito");
    }

    @FXML
    private void onVolver() {
        App.irA("catalogo");
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
