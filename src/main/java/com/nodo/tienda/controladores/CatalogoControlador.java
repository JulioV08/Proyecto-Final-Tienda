package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class CatalogoControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label rol;

    @FXML
    public void initialize() {
        Usuario usuario = RepositorioUsuarios.getUsuario();
        if (usuario == null) {
            saludo.setText("Invitado");
            rol.setText("Sin sesión");
            return;
        }
        saludo.setText(usuario.getNombreCompleto());
        rol.setText(usuario.getRol());
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
