package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class CuentaControlador {

    @FXML
    private Label nombre;

    @FXML
    private Label correo;

    @FXML
    private Label telefono;

    @FXML
    private Label rol;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }
        nombre.setText("Nombre: " + actual.getNombreCompleto());
        correo.setText("Correo: " + actual.getCorreo());
        telefono.setText("Teléfono: " + actual.getTelefono());
        rol.setText("Rol: " + actual.getRol());
    }

    @FXML
    private void onEditar() {
        App.irA("configuracion");
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
