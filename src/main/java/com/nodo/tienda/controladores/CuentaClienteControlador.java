package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;


public class CuentaClienteControlador {

    @FXML
    private Label nombre;

    @FXML
    private Label correo;

    @FXML
    private Label telefono;

    @FXML
    private Label rol;

    @FXML
    private PasswordField contrasenaActual;

    @FXML
    private PasswordField contrasenaNueva;

    @FXML
    private PasswordField contrasenaRepetida;

    @FXML
    private Label mensaje;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null) {
            App.irA("login");
            return;
        }
        nombre.setText("Nombre: " + actual.getNombreCompleto());
        correo.setText("Correo: " + actual.getCorreo());
        telefono.setText("Teléfono: " + actual.getTelefono());
        rol.setText("Rol: " + actual.getRol());
    }

    @FXML
    private void onGuardarContrasena() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null) {
            App.irA("login");
            return;
        }
        String actualTexto = texto(contrasenaActual);
        String nueva = texto(contrasenaNueva);
        String repetida = texto(contrasenaRepetida);

        if (actualTexto.isEmpty() || nueva.isEmpty() || repetida.isEmpty()) {
            mensaje.setText("Completa los tres campos de contraseña.");
            return;
        }
        if (!actualTexto.equals(actual.getContrasena())) {
            mensaje.setText("La contraseña actual no es correcta.");
            return;
        }
        if (nueva.length() < 6) {
            mensaje.setText("La nueva contraseña debe tener al menos 6 caracteres.");
            return;
        }
        if (!nueva.equals(repetida)) {
            mensaje.setText("Las contraseñas nuevas no coinciden.");
            return;
        }

        actual.setContrasena(nueva);
        RepositorioUsuarios.guardar();
        contrasenaActual.clear();
        contrasenaNueva.clear();
        contrasenaRepetida.clear();
        mensaje.setText("Contraseña actualizada correctamente.");
        Alertas.info("Contraseña actualizada", "Ya puedes iniciar sesión con tu nueva contraseña.");
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

    private String texto(PasswordField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }
}
