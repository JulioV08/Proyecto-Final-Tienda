package com.nodo.tienda.modelo;


public class Usuario {

    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String fechaNacimiento;
    private String contrasena;
    private String rol;
    private int pedidos;

    public Usuario(String nombre, String apellidos, String correo, String telefono,
                   String fechaNacimiento, String contrasena, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.rol = rol;
        this.pedidos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public void incrementarPedidos() {
        pedidos++;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public boolean esAdministrador() {
        return "Administrador".equals(rol);
    }

    public boolean esCuentaPrincipal() {
        return "admin@nodo.com".equals(correo);
    }
}
