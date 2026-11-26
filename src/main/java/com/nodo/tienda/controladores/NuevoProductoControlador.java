package com.nodo.tienda.controladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
    private ImageView vistaImagen;

    @FXML
    private Label rutaImagen;

    @FXML
    private Label mensaje;

    private String rutaSeleccionada;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }
        usuario.setText(actual.getNombreCompleto() + " · Administrador");
        categoria.getItems().addAll("Audio", "Escritorio", "Iluminación", "Accesorios",
                "Consolas", "Celulares", "Electrodomésticos");
        onLimpiar();
    }

    @FXML
    private void onElegirImagen() {
        FileChooser selector = new FileChooser();
        selector.setTitle("Elegir imagen del producto");
        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        Stage escenario = (Stage) nombre.getScene().getWindow();
        File archivo = selector.showOpenDialog(escenario);
        if (archivo == null) {
            return;
        }
        try {
            String nombreArchivo = nombreSeguro(archivo.getName());
            Path carpeta = Paths.get("src", "main", "resources", "com", "nodo", "tienda",
                    "vistas", "imagenes");
            Files.createDirectories(carpeta);
            Path destino = carpeta.resolve(nombreArchivo);
            int contador = 1;
            while (Files.exists(destino)) {
                int punto = nombreArchivo.lastIndexOf('.');
                String base = punto > 0 ? nombreArchivo.substring(0, punto) : nombreArchivo;
                String extension = punto > 0 ? nombreArchivo.substring(punto) : "";
                destino = carpeta.resolve(base + "_" + contador + extension);
                contador++;
            }
            Files.copy(archivo.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
            rutaSeleccionada = "/com/nodo/tienda/vistas/imagenes/" + destino.getFileName();
            vistaImagen.setImage(new Image(destino.toUri().toString()));
            rutaImagen.setText(destino.getFileName().toString());
            mostrar("Imagen copiada a la carpeta del proyecto: " + destino.getFileName());
        } catch (IOException e) {
            mostrar("No se pudo copiar la imagen al proyecto.");
        }
    }

    private String nombreSeguro(String nombreArchivo) {
        String seguro = nombreArchivo.replaceAll("[^a-zA-Z0-9._-]", "_").toLowerCase();
        if (seguro.isEmpty()) {
            return "producto.png";
        }
        return seguro;
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
        if (stockNuevo > RepositorioProductos.getUmbralStock()) {
            mostrar("El stock no puede superar el tope de "
                    + RepositorioProductos.getUmbralStock() + " unidades.");
            return;
        }

        String iconoNuevo = texto(icono);
        if (iconoNuevo.isEmpty()) {
            iconoNuevo = "📦";
        }

        Producto nuevo = new Producto(nombreNuevo, categoriaNueva, precioNuevo, stockNuevo,
                iconoNuevo, destacado.isSelected(), descripcionNueva);
        if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
            nuevo.setRutaImagen(rutaSeleccionada);
        }

        RepositorioProductos.agregar(nuevo);
        limpiarCampos();
        mostrar("Producto #" + nuevo.getId() + " registrado correctamente.");
        Alertas.info("Producto registrado", "El producto #" + nuevo.getId() + " se agregó al inventario.");
        App.irA("admin");
    }

    @FXML
    private void onLimpiar() {
        limpiarCampos();
        mostrar("Completa el formulario para registrar un producto.");
    }

    private void limpiarCampos() {
        nombre.clear();
        categoria.setValue(null);
        precio.clear();
        stock.clear();
        icono.clear();
        descripcion.clear();
        destacado.setSelected(false);
        vistaImagen.setImage(null);
        rutaImagen.setText("Sin imagen seleccionada");
        rutaSeleccionada = null;
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
