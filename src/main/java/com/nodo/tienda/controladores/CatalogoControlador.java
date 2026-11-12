package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class CatalogoControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label rol;

    @FXML
    private ListView<String> lista;

    @FXML
    public void initialize() {
        Usuario usuario = RepositorioUsuarios.getUsuario();
        if (usuario == null) {
            saludo.setText("Invitado");
            rol.setText("Sin sesión");
        } else {
            saludo.setText(usuario.getNombreCompleto());
            rol.setText(usuario.getRol());
        }

        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            lista.getItems().add(producto.getIcono() + "  " + producto.getNombre()
                    + " · " + producto.getCategoria()
                    + " · " + producto.getPrecioTexto()
                    + " · Stock: " + producto.getStock());
        }
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
