package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.ListaEnlazada;
import com.nodo.tienda.modelo.Usuario;

/**
 * Repositorio de usuarios en memoria.
 */
public class RepositorioUsuarios {

    private static final ListaEnlazada<Usuario> usuarios = new ListaEnlazada<>();

    static {
        cargarDatosDePrueba();
    }

    public static void registrar(Usuario usuario) {
        usuarios.agregar(usuario);
    }

    public static Usuario buscarPorCorreo(String correo) {
        for (int i = 0; i < usuarios.tamanio(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                return usuario;
            }
        }
        return null;
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

    private static void cargarDatosDePrueba() {
        registrar(new Usuario("Lucía", "Martín", "lucia.martin@email.com",
                "+34 600 000 000", "12/05/1998", "123456", "Cliente"));
        registrar(new Usuario("Admin", "Nodo", "admin@nodo.com",
                "300 000 0000", "01/01/1990", "admin123", "Administrador"));
    }
}
