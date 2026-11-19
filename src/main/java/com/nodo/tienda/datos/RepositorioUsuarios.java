package com.nodo.tienda.datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nodo.tienda.estructuras.ListaEnlazada;
import com.nodo.tienda.modelo.Usuario;


public class RepositorioUsuarios {

    private static final Path ARCHIVO = Paths.get("usuarios.txt");
    private static final ListaEnlazada usuarios = new ListaEnlazada();
    private static Usuario usuarioActual;

    static {
        cargarDatosDePrueba();
        cargarUsuariosGuardados();
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
        guardarUsuarios();
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
        usuarios.agregar(new Usuario("Lucía", "Martín", "lucia.martin@email.com",
                "+34 600 000 000", "12/05/1998", "123456", "Cliente"));
    }

    private static void cargarUsuariosGuardados() {
        if (!Files.exists(ARCHIVO)) {
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(ARCHIVO, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split("\\|", -1);
                if (datos.length < 7) {
                    continue;
                }
                if (!existeCorreo(datos[2])) {
                    usuarios.agregar(new Usuario(datos[0], datos[1], datos[2], datos[3],
                            datos[4], datos[5], datos[6]));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron cargar los usuarios guardados", e);
        }
    }

    private static void guardarUsuarios() {
        try (BufferedWriter escritor = Files.newBufferedWriter(ARCHIVO, StandardCharsets.UTF_8)) {
            for (int i = 0; i < usuarios.tamanio(); i++) {
                Usuario usuario = usuarios.obtener(i);
                escritor.write(usuario.getNombre() + "|" + usuario.getApellidos() + "|"
                        + usuario.getCorreo() + "|" + usuario.getTelefono() + "|"
                        + usuario.getFechaNacimiento() + "|" + usuario.getContrasena() + "|"
                        + usuario.getRol());
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron guardar los usuarios", e);
        }
    }
}
