# nodo — Login y catálogo

Avance del proyecto de Programación. Esta entrega incluye únicamente el acceso de usuarios y el catálogo de productos.

## Módulos de esta entrega

- Login de usuarios con validaciones.
- Registro de cuentas con confirmación de contraseña.
- Sesión de usuario y cierre de sesión.
- Catálogo con tarjetas de producto.
- Detalle del producto.
- Búsqueda por nombre o categoría.
- Filtros por categoría, precio y disponibilidad.
- Orden por precio.
- Búsquedas recientes y productos recomendados.

## Estructuras propias

- `ListaEnlazada`: usuarios registrados.
- `ListaProductos`: productos del catálogo.
- `Pila`: historial de búsquedas recientes.
- `Cola`: rotación de productos recomendados.

Las estructuras usan nodos propios y no se utiliza `java.util.Stack`.

## Cuentas de prueba

- Cliente: `lucia.martin@email.com` / `123456`
- Administrador: `admin@nodo.com` / `admin123`

## Requisitos

- JDK 25
- Apache Maven 3.9+
- NetBeans 27 (recomendado) y Scene Builder 26

## Cómo ejecutar

Desde NetBeans:

1. Abrir la carpeta del proyecto que contiene `pom.xml`.
2. Ejecutar con el botón **Run** (F6).

Desde la terminal:

```bash
mvn javafx:run
```

## Estructura del proyecto

```text
src/main/java/com/nodo/tienda/
  App.java
  Launcher.java
  controladores/
  datos/
  estructuras/
  modelo/
src/main/resources/com/nodo/tienda/vistas/
  login.fxml
  registro.fxml
  catalogo.fxml
  producto.fxml
  detalle.fxml
```

## Avances

| Fecha | Avance | Descripción |
|---|---|---|
| 10/11/2026 | Avance 1 | Estructura del proyecto, usuarios y login con lista enlazada. |
| 11/11/2026 | Avance 2 | Registro de usuarios con validaciones. |
| 12/11/2026 | Avance 3 | Modelo de producto y catálogo inicial con lista propia. |
| 13/11/2026 | Avance 4 | Catálogo visual con tarjetas de producto. |
| 14/11/2026 | Avance 5 | Detalle del producto. |
| 15/11/2026 | Avance 6 | Búsqueda por nombre y filtro por categoría. |
| 16/11/2026 | Avance 7 | Filtro por precio, disponibilidad, orden y contador. |
| 17/11/2026 | Avance 8 | Pila de búsquedas recientes y cola de recomendados. |
| 18/11/2026 | Avance 9 | Validaciones finales, sesión y documentación. |

## Avance 1 — Acceso de usuarios

- Estructura base del proyecto JavaFX.
- Modelo `Usuario` con rol de cliente o administrador.
- Usuarios guardados en memoria con `Nodo` y `ListaEnlazada`.
- Inicio de sesión con validaciones.
- Sesión del usuario y cierre de sesión.

## Avance 2 — Registro de usuarios

- Formulario con nombre, apellidos, correo, teléfono, fecha de nacimiento y contraseña.
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

## Avance 8 — Pila de búsquedas y cola de recomendados

- Las búsquedas confirmadas con Enter se guardan en una `Pila` propia.
- La pila muestra las últimas seis búsquedas, de la más reciente a la más antigua.
- El botón `Limpiar` vacía la pila.
- Los productos disponibles se cargan en una `Cola` circular de recomendados.
- El botón `Ver otro` rota la cola y muestra el siguiente producto.
- Se usan `ListaEnlazada`, `ListaProductos`, `Pila` y `Cola`.

## Avance 9 — Validaciones finales y documentación

- Confirmación de contraseña en el registro.
- Validación de nombre y apellidos con letras.
- Validación de fecha de nacimiento no futura.
- Mensajes de error y confirmación más claros.
- Documentación de la entrega y de las estructuras usadas.
- El catálogo muestra el nombre y el rol del usuario autenticado.
