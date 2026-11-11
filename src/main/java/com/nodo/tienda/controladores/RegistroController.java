package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la pantalla de registro de nuevos clientes.
 */
public class RegistroController {

    @FXML
    private TextField nombre;

    @FXML
    private TextField apellidos;

    @FXML
    private TextField correo;

    @FXML
    private TextField telefono;

    @FXML
    private DatePicker fechaNacimiento;

    @FXML
    private PasswordField contrasena;

    @FXML
    private Label mensaje;

    @FXML
    private void onCrearCuenta() {
        String nombreIngresado = texto(nombre);
        String apellidosIngresados = texto(apellidos);
        String correoIngresado = texto(correo);
        String telefonoIngresado = texto(telefono);
        String contrasenaIngresada = texto(contrasena);

        if (nombreIngresado.isEmpty() || apellidosIngresados.isEmpty()
                || correoIngresado.isEmpty() || telefonoIngresado.isEmpty()
                || contrasenaIngresada.isEmpty() || fechaNacimiento.getValue() == null) {
            mostrarMensaje("Completa todos los campos para crear tu cuenta.");
            return;
        }

        if (!correoIngresado.contains("@") || !correoIngresado.contains(".")) {
            mostrarMensaje("Escribe un correo electrónico válido.");
            return;
        }

        if (contrasenaIngresada.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        if (RepositorioUsuarios.existeCorreo(correoIngresado)) {
            mostrarMensaje("Ya existe una cuenta registrada con ese correo.");
            return;
        }

        String fecha = fechaNacimiento.getValue().toString();

        Usuario nuevoUsuario = new Usuario(nombreIngresado, apellidosIngresados,
                correoIngresado, telefonoIngresado, fecha, contrasenaIngresada, "Cliente");

        RepositorioUsuarios.registrar(nuevoUsuario);
        mostrarMensaje("¡Cuenta creada con éxito! Ya puedes iniciar sesión.");
    }

    @FXML
    private void onVolver() {
        App.irA("login");
    }

    private String texto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void mostrarMensaje(String texto) {
        mensaje.setText(texto);
        mensaje.setVisible(true);
        mensaje.setManaged(true);
    }
}
