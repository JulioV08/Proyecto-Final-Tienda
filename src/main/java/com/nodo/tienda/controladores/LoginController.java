package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.datos.Sesion;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la pantalla de inicio de sesión.
 */
public class LoginController {

    @FXML
    private TextField correo;

    @FXML
    private PasswordField contrasena;

    @FXML
    private Label mensaje;

    @FXML
    private void onLogin() {
        String correoIngresado = correo.getText() == null ? "" : correo.getText().trim();
        String contrasenaIngresada = contrasena.getText() == null ? "" : contrasena.getText();

        if (correoIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
            mostrarMensaje("Completa el usuario y la contraseña.");
            return;
        }

        Usuario usuario = RepositorioUsuarios
                .autenticar(correoIngresado, contrasenaIngresada);

        if (usuario == null) {
            mostrarMensaje("Usuario o contraseña incorrectos. Inténtalo de nuevo.");
            return;
        }

        Sesion.iniciar(usuario);
        App.irA("catalogo");
    }

    @FXML
    private void onRegistrar() {
        App.irA("registro");
    }

    @FXML
    private void onOlvidar() {
        mostrarMensaje("Contacta al administrador de la tienda para recuperar tu cuenta.");
    }

    private void mostrarMensaje(String texto) {
        mensaje.setText(texto);
        mensaje.setVisible(true);
        mensaje.setManaged(true);
    }
}
