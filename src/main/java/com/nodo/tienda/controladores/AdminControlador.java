package com.nodo.tienda.controladores;

import java.util.Locale;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AdminControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label usuario;

    @FXML
    private Label productos;

    @FXML
    private Label unidades;

    @FXML
    private Label agotados;

    @FXML
    private Label valor;

    @FXML
    private Label bajoStock;

    @FXML
    private ListView<String> inventario;

    @FXML
    private TextField buscarId;

    @FXML
    private Label idProducto;

    @FXML
    private TextField nombre;

    @FXML
    private TextField categoria;

    @FXML
    private TextField precio;

    @FXML
    private TextField stock;

    @FXML
    private TextArea descripcion;

    @FXML
    private Label mensaje;

    private Producto productoEditando;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }

        saludo.setText("Hola, " + actual.getNombre() + " " + actual.getApellidos());
        usuario.setText(actual.getNombre() + " " + actual.getApellidos() + " · Administrador");
        actualizarResumen();
        onLimpiar();
    }

    private void actualizarResumen() {
        int totalUnidades = 0;
        int totalAgotados = 0;
        int totalBajoStock = 0;
        long valorInventario = 0;
        inventario.getItems().clear();

        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            totalUnidades += producto.getStock();
            if (producto.getStock() == 0) {
                totalAgotados++;
            }
            if (producto.getStock() > 0 && producto.getStock() <= 5) {
                totalBajoStock++;
            }
            valorInventario += (long) producto.getPrecio() * producto.getStock();
            inventario.getItems().add(i + 1 + ". " + producto.getNombre() + " · "
                    + producto.getCategoria() + " · " + producto.getStock() + " unidades · "
                    + producto.getPrecioTexto());
        }

        productos.setText(String.valueOf(RepositorioProductos.tamanio()));
        unidades.setText(String.valueOf(totalUnidades));
        agotados.setText(String.valueOf(totalAgotados));
        valor.setText(String.format(Locale.forLanguageTag("es-CO"), "$%,d", valorInventario));
        bajoStock.setText("Stock bajo: " + totalBajoStock
                + (totalBajoStock == 1 ? " producto" : " productos"));
    }

    @FXML
    private void onBuscar() {
        String texto = texto(buscarId);
        if (texto.isEmpty()) {
            mostrar("Escribe el nombre del producto.");
            return;
        }

        Producto encontrado = null;
        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto candidato = RepositorioProductos.obtener(i);
            if (candidato.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                encontrado = candidato;
                break;
            }
        }

        if (encontrado == null) {
            productoEditando = null;
            idProducto.setText("Producto sin seleccionar");
            limpiarFormulario();
            mostrar("No existe un producto con ese nombre.");
            return;
        }

        productoEditando = encontrado;
        idProducto.setText(encontrado.getNombre());
        nombre.setText(encontrado.getNombre());
        categoria.setText(encontrado.getCategoria());
        precio.setText(String.valueOf(encontrado.getPrecio()));
        stock.setText(String.valueOf(encontrado.getStock()));
        descripcion.setText(encontrado.getDescripcion());
        mostrar("Producto encontrado. Puedes editar sus datos.");
    }

    @FXML
    private void onGuardar() {
        if (productoEditando == null) {
            mostrar("Primero busca un producto por su nombre.");
            return;
        }

        String nombreNuevo = texto(nombre);
        String categoriaNueva = texto(categoria);
        String descripcionNueva = texto(descripcion);
        if (nombreNuevo.isEmpty() || categoriaNueva.isEmpty() || descripcionNueva.isEmpty()) {
            mostrar("Completa el nombre, la categoría y la descripción.");
            return;
        }

        int precioNuevo;
        int stockNuevo;
        try {
            precioNuevo = Integer.parseInt(texto(precio));
            stockNuevo = Integer.parseInt(texto(stock));
        } catch (NumberFormatException e) {
            mostrar("El precio y el stock deben ser números.");
            return;
        }

        if (precioNuevo < 0 || stockNuevo < 0) {
            mostrar("El precio y el stock no pueden ser negativos.");
            return;
        }

        productoEditando.setNombre(nombreNuevo);
        productoEditando.setCategoria(categoriaNueva);
        productoEditando.setPrecio(precioNuevo);
        productoEditando.setStock(stockNuevo);
        productoEditando.setDescripcion(descripcionNueva);

        actualizarResumen();
        mostrar("Cambios guardados en " + productoEditando.getNombre() + ".");
    }

    @FXML
    private void onLimpiar() {
        productoEditando = null;
        buscarId.clear();
        nombre.clear();
        categoria.clear();
        precio.clear();
        stock.clear();
        descripcion.clear();
        idProducto.setText("Producto sin seleccionar");
        mostrar("Busca un producto por su nombre para editarlo.");
    }

    private String texto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String texto(TextArea campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void limpiarFormulario() {
        nombre.clear();
        categoria.clear();
        precio.clear();
        stock.clear();
        descripcion.clear();
    }

    private void mostrar(String texto) {
        mensaje.setText(texto);
    }

    @FXML
    private void onRegistrarProducto() {
        App.irA("nuevo-producto");
    }

    @FXML
    private void onCatalogo() {
        App.irA("catalogo");
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
