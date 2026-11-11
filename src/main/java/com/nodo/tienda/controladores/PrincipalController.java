package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Sesion;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador de la pantalla principal (por ahora recibe al usuario).
 */
public class PrincipalController {

    @FXML
    private Label bienvenida;

    @FXML
    private Label rol;

    @FXML
    public void initialize() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario != null) {
            bienvenida.setText("Hola, " + usuario.getNombre() + " 👋");
            rol.setText(usuario.getRol()
                    + " · puedes explorar la tienda con tu cuenta.");
        } else {
            bienvenida.setText("Bienvenido a nodo");
            rol.setText("Inicia sesión para ver tu cuenta.");
        }
    }

    @FXML
    private void onIrAlCatalogo() {
        App.irA("catalogo");
    }

    @FXML
    private void onCerrarSesion() {
        Sesion.cerrar();
        App.irA("login");
    }
}
