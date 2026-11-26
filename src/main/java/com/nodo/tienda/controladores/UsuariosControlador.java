package com.nodo.tienda.controladores;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class UsuariosControlador {

    @FXML
    private TableView<Usuario> tabla;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colCorreo;

    @FXML
    private TableColumn<Usuario, String> colRol;

    @FXML
    private TextField busqueda;

    @FXML
    private Label mensaje;

    private int[] indices = new int[0];

    @FXML
    public void initialize() {
        if (RepositorioUsuarios.getUsuario() == null
                || !RepositorioUsuarios.getUsuario().esAdministrador()) {
            App.irA("login");
            return;
        }

        colNombre.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getNombreCompleto()));
        colCorreo.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getCorreo()));
        colRol.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getRol()));

        actualizar();
    }

    private void actualizar() {
        String texto = busqueda.getText() == null ? "" : busqueda.getText().trim().toLowerCase();
        tabla.getItems().clear();

        int cantidad = 0;
        for (int i = 0; i < RepositorioUsuarios.tamanio(); i++) {
            Usuario usuario = RepositorioUsuarios.obtener(i);
            if (!texto.isEmpty() && !usuario.getNombreCompleto().toLowerCase().contains(texto)
                    && !usuario.getCorreo().toLowerCase().contains(texto)) {
                continue;
            }
            tabla.getItems().add(usuario);
            cantidad++;
        }

        indices = new int[cantidad];
        int posicion = 0;
        for (int i = 0; i < RepositorioUsuarios.tamanio(); i++) {
            Usuario usuario = RepositorioUsuarios.obtener(i);
            if (!texto.isEmpty() && !usuario.getNombreCompleto().toLowerCase().contains(texto)
                    && !usuario.getCorreo().toLowerCase().contains(texto)) {
                continue;
            }
            indices[posicion] = i;
            posicion++;
        }
        mensaje.setText("Usuarios: " + cantidad);
    }

    private int indiceSeleccionado() {
        int fila = tabla.getSelectionModel().getSelectedIndex();
        if (fila < 0 || fila >= indices.length) {
            mensaje.setText("Selecciona un usuario de la tabla.");
            return -1;
        }
        return indices[fila];
    }

    @FXML
    private void onBuscar() {
        actualizar();
    }

    private boolean protegido(int indice) {
        Usuario usuario = RepositorioUsuarios.obtener(indice);
        if (usuario == RepositorioUsuarios.getUsuario() || usuario.esCuentaPrincipal()) {
            mensaje.setText("No puedes modificar la cuenta principal del administrador.");
            return true;
        }
        return false;
    }

    @FXML
    private void onHacerAdmin() {
        int indice = indiceSeleccionado();
        if (indice < 0 || protegido(indice)) {
            return;
        }
        RepositorioUsuarios.hacerAdmin(indice);
        actualizar();
        mensaje.setText("El usuario ahora es administrador.");
        Alertas.info("Rol actualizado", "El usuario ahora tiene privilegios de administrador.");
    }

    @FXML
    private void onQuitarAdmin() {
        int indice = indiceSeleccionado();
        if (indice < 0 || protegido(indice)) {
            return;
        }
        RepositorioUsuarios.quitarAdmin(indice);
        actualizar();
        mensaje.setText("El usuario ahora es cliente.");
        Alertas.info("Rol actualizado", "El usuario ya no es administrador.");
    }

    @FXML
    private void onEliminar() {
        int indice = indiceSeleccionado();
        if (indice < 0 || protegido(indice)) {
            return;
        }
        if (!Alertas.confirmar("Eliminar usuario", "¿Seguro que quieres eliminar este usuario?")) {
            return;
        }
        RepositorioUsuarios.eliminar(indice);
        actualizar();
        mensaje.setText("Usuario eliminado.");
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
