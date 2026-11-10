package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.ListaEnlazada;
import com.nodo.tienda.modelo.Usuario;


public class RepositorioUsuarios {

    private static final ListaEnlazada usuarios = new ListaEnlazada();
    private static Usuario usuarioActual;

    static {
        cargarDatosDePrueba();
    }

    private RepositorioUsuarios() {
    }

    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static void registrar(Usuario usuario) {
        usuarios.agregar(usuario);
    }

    public static Usuario buscarPorCorreo(String correo) {
        return usuarios.buscarPorCorreo(correo);
    }

    public static boolean existeCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }

    public static Usuario autenticar(String correo, String contrasena) {
        Usuario usuario = buscarPorCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }

    public static int tamanio() {
        return usuarios.tamanio();
    }

    private static void cargarDatosDePrueba() {
        registrar(new Usuario("Lucía", "Martín", "lucia.martin@email.com",
                "+34 600 000 000", "12/05/1998", "123456", "Cliente"));
        registrar(new Usuario("Admin", "Nodo", "admin@nodo.com",
                "300 000 0000", "01/01/1990", "admin123", "Administrador"));
    }
}
