package com.nodo.tienda.datos;

import com.nodo.tienda.modelo.Usuario;

/**
 * Mantiene al usuario que tiene la sesión activa.
 */
public class Sesion {

    private static Usuario usuarioActual;

    private Sesion() {
    }

    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static void cerrar() {
        usuarioActual = null;
    }
}
