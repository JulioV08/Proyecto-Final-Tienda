# nodo — Login y catálogo

Avance del proyecto de Programación. En esta entrega se trabaja únicamente el acceso de usuarios y el catálogo de productos.

## Avance 1 — Acceso de usuarios

- Estructura base del proyecto JavaFX.
- Modelo `Usuario` con rol de cliente o administrador.
- Usuarios guardados en memoria con `Nodo` y `ListaEnlazada`.
- Inicio de sesión con validaciones.
- Sesión del usuario y cierre de sesión.
- Cuentas de prueba:
  - Cliente: `lucia.martin@email.com` / `123456`
  - Administrador: `admin@nodo.com` / `admin123`

## Avance 2 — Registro de usuarios

- Formulario de registro con nombre, apellidos, correo, teléfono, fecha de nacimiento y contraseña.
- Validación de campos obligatorios, correo y longitud de la contraseña.
- Verificación de correos duplicados.
- Las cuentas nuevas se agregan a la `ListaEnlazada` de usuarios.
- Al crear la cuenta se limpian los campos y se muestra el mensaje de confirmación.

## Requisitos

- JDK 25
- Apache Maven 3.9+
- NetBeans 27 (recomendado) y Scene Builder 26

## Cómo ejecutar

```bash
mvn javafx:run
```
