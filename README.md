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

## Avance 3 — Modelo de producto y catálogo inicial

- Modelo `Producto` con nombre, categoría, precio, stock, ícono, descripción y estado destacado.
- Productos guardados en memoria con `NodoProducto` y `ListaProductos`.
- IDs automáticos y consecutivos para cada producto.
- Catálogo inicial en forma de lista con los datos principales.
- Seis productos de prueba de distintas categorías.

## Avance 4 — Catálogo visual

- Encabezado con marca, buscador, usuario y rol.
- Tarjetas de producto con ícono, categoría, nombre, precio, stock y etiqueta de destacado.
- Tarjetas construidas con `producto.fxml` y cargadas dinámicamente dentro de un `FlowPane`.
- Distribución visual con filtros, recomendados y búsquedas recientes.
- Vista de detalle rápida para consultar la descripción del producto.

## Avance 5 — Detalle del producto

- Al seleccionar una tarjeta se abre la vista de detalle.
- La vista muestra ícono, categoría, nombre, precio, stock y descripción.
- La etiqueta de producto destacado solo aparece cuando corresponde.
- El usuario puede volver al catálogo o cerrar sesión desde la vista.
- La información se toma directamente del producto seleccionado en la `ListaProductos`.

## Avance 6 — Búsqueda y categorías

- Búsqueda por nombre o categoría mientras se escribe.
- Filtro por categoría desde una lista de opciones.
- La búsqueda y la categoría se combinan para refinar el catálogo.
- Contador de productos visibles.
- Mensaje cuando no hay resultados.
- El filtrado recorre la `ListaProductos` sin usar colecciones de Java.

## Avance 7 — Precio, disponibilidad y orden

- Filtro por precio máximo con un control deslizante.
- Opción para mostrar únicamente productos con stock.
- Orden por precio de menor a mayor o de mayor a menor.
- El contador se actualiza con cada combinación de filtros.
- La tarjeta muestra `Agotado` cuando el stock llega a cero.

## Requisitos

- JDK 25
- Apache Maven 3.9+
- NetBeans 27 (recomendado) y Scene Builder 26

## Cómo ejecutar

```bash
mvn javafx:run
```
