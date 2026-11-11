package com.nodo.tienda.modelo;

/**
 * Representa a una persona registrada en la tienda.
 */
public class Usuario {

    private final String nombre;
    private final String apellidos;
    private final String correo;
    private final String telefono;
    private final String fechaNacimiento;
    private final String contrasena;
    private final String rol;

    public Usuario(String nombre, String apellidos, String correo, String telefono,
                   String fechaNacimiento, String contrasena, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }
}
