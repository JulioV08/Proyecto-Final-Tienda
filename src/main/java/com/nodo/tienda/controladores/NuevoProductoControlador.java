package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class NuevoProductoControlador {

    @FXML
    private Label usuario;

    @FXML
    private TextField nombre;

    @FXML
    private ComboBox<String> categoria;

    @FXML
    private TextField precio;

    @FXML
    private TextField stock;

    @FXML
    private TextField icono;

    @FXML
    private TextArea descripcion;

    @FXML
    private CheckBox destacado;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }

        usuario.setText(actual.getNombre() + " " + actual.getApellidos() + " · Administrador");
        onLimpiar();
    }

    @FXML
    private void onRegistrar() {
        String nombreNuevo = texto(nombre);
        String categoriaNueva = categoria.getValue() == null ? "" : categoria.getValue().trim();
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

        String iconoNuevo = texto(icono);
        if (iconoNuevo.isEmpty()) {
            iconoNuevo = "📦";
        }

        Producto nuevo = new Producto(nombreNuevo, categoriaNueva, precioNuevo, stockNuevo,
                iconoNuevo, destacado.isSelected(), descripcionNueva);

        RepositorioProductos.agregar(nuevo);
        limpiarCampos();
        mostrar("Producto " + nuevo.getNombre() + " registrado correctamente.");
    }

    @FXML
    private void onLimpiar() {
        limpiarCampos();
        mostrar("Completa los datos del producto. El ID se asigna automáticamente.");
    }

    private void limpiarCampos() {
        nombre.clear();
        categoria.setValue(null);
        precio.clear();
        stock.clear();
        icono.setText("📦");
        descripcion.clear();
        destacado.setSelected(false);
    }

    private String texto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String texto(TextArea campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void mostrar(String texto) {
        mensaje.setText(texto);
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
