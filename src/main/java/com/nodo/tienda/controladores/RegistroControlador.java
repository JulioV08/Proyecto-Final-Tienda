package com.nodo.tienda.controladores;

import java.time.LocalDate;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegistroControlador {

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
    private PasswordField repetirContrasena;

    @FXML
    private Label mensaje;

    @FXML
    private void onCrearCuenta() {
        String nombreIngresado = texto(nombre);
        String apellidosIngresados = texto(apellidos);
        String correoIngresado = texto(correo);
        String telefonoIngresado = texto(telefono);
        String contrasenaIngresada = texto(contrasena);
        String contrasenaRepetida = texto(repetirContrasena);

        if (nombreIngresado.isEmpty() || apellidosIngresados.isEmpty()
                || correoIngresado.isEmpty() || telefonoIngresado.isEmpty()
                || contrasenaIngresada.isEmpty() || contrasenaRepetida.isEmpty()
                || fechaNacimiento.getValue() == null) {
            mostrarMensaje("Completa todos los campos para crear tu cuenta.");
            return;
        }

        if (!nombreIngresado.matches("[\\p{L} ]+") || !apellidosIngresados.matches("[\\p{L} ]+")) {
            mostrarMensaje("El nombre y los apellidos solo deben contener letras.");
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

        if (!contrasenaIngresada.equals(contrasenaRepetida)) {
            mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }

        if (fechaNacimiento.getValue().isAfter(LocalDate.now())) {
            mostrarMensaje("La fecha de nacimiento no puede ser futura.");
            return;
        }

        if (RepositorioUsuarios.existeCorreo(correoIngresado)) {
            mostrarMensaje("Ya existe una cuenta registrada con ese correo.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombreIngresado, apellidosIngresados,
                correoIngresado, telefonoIngresado, fechaNacimiento.getValue().toString(),
                contrasenaIngresada, "Cliente");

        RepositorioUsuarios.registrar(nuevoUsuario);
        limpiarCampos();
        mostrarMensaje("¡Cuenta creada con éxito! Ya puedes iniciar sesión.");
        Alertas.info("Cuenta creada", "Ya puedes iniciar sesión con tu correo.");
    }

    @FXML
    private void onVolver() {
        App.irA("login");
    }

    private String texto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void limpiarCampos() {
        nombre.clear();
        apellidos.clear();
        correo.clear();
        telefono.clear();
        fechaNacimiento.setValue(null);
        contrasena.clear();
        repetirContrasena.clear();
    }

    private void mostrarMensaje(String texto) {
        mensaje.setText(texto);
        mensaje.setVisible(true);
        mensaje.setManaged(true);
    }
}
