package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ConfiguracionControlador {

    @FXML
    private TextField nombre;

    @FXML
    private TextField apellidos;

    @FXML
    private TextField correo;

    @FXML
    private TextField telefono;

    @FXML
    private PasswordField contrasena;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }
        nombre.setText(actual.getNombre());
        apellidos.setText(actual.getApellidos());
        correo.setText(actual.getCorreo());
        telefono.setText(actual.getTelefono());
        contrasena.setText(actual.getContrasena());
    }

    @FXML
    private void onGuardar() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null) {
            App.irA("login");
            return;
        }
        String nombreNuevo = nombre.getText() == null ? "" : nombre.getText().trim();
        String apellidosNuevos = apellidos.getText() == null ? "" : apellidos.getText().trim();
        String correoNuevo = correo.getText() == null ? "" : correo.getText().trim();
        String telefonoNuevo = telefono.getText() == null ? "" : telefono.getText().trim();
        String contrasenaNueva = contrasena.getText() == null ? "" : contrasena.getText().trim();

        if (nombreNuevo.isEmpty() || apellidosNuevos.isEmpty() || correoNuevo.isEmpty()
                || telefonoNuevo.isEmpty() || contrasenaNueva.isEmpty()) {
            mensaje.setText("Completa todos los datos.");
            return;
        }
        if (!correoNuevo.contains("@") || !correoNuevo.contains(".")) {
            mensaje.setText("Escribe un correo válido.");
            return;
        }
        if (actual.esCuentaPrincipal() && !correoNuevo.equalsIgnoreCase(actual.getCorreo())) {
            mensaje.setText("No puedes cambiar el correo de la cuenta principal.");
            return;
        }
        Usuario existente = RepositorioUsuarios.buscarPorCorreo(correoNuevo);
        if (existente != null && existente != actual) {
            mensaje.setText("Ese correo ya está registrado por otro usuario.");
            return;
        }

        actual.setNombre(nombreNuevo);
        actual.setApellidos(apellidosNuevos);
        actual.setCorreo(correoNuevo);
        actual.setTelefono(telefonoNuevo);
        actual.setContrasena(contrasenaNueva);
        RepositorioUsuarios.guardar();

        mensaje.setText("Datos actualizados correctamente.");
        Alertas.info("Configuración guardada", "Tus datos se actualizaron correctamente.");
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
