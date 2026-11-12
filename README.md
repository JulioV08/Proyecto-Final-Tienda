# Proyecto-Final-Tienda
# nodo — Plataforma E-Commerce

Proyecto final para la asignatura de Programación. 

## Prototipo Interactivo
Puedes consultar el diseño completo y el prototipo funcional en el siguiente enlace:
https://www.figma.com/design/YmSj5F8fhFY8r8YTP63SRG/Actividad-06-Prototipo?node-id=0-1&t=yt6cCPw93LSG8Rxb-1

## Módulos Diseñados
- *Acceso:* Login y Registro de usuarios.
- *Cliente:* Catálogo con filtros, Historial de Compras y Lista de Favoritos.
- *Administrador:* Dashboard de Gestión de Inventario (con búsqueda por ID y edición) y Formulario de Registro de Productos.

## Integrantes
- Julio Cesar Vidal Herazo
- Camilo Andrés Palmera Fernández
- Juan Andrés Torres Peña

## Requisitos
- JDK 25
- Apache Maven 3.9+
- NetBeans 27 (recomendado) y Scene Builder 26

## Cómo ejecutar en NetBeans
1. Abrir NetBeans y seleccionar **File > Open Project**.
2. Elegir la carpeta del repositorio (contiene el archivo `pom.xml`).
3. Ejecutar con el botón **Run** (F6) o desde la terminal:

```bash
mvn javafx:run
```

## Historial de avances

| Fecha | Avance | Descripción |
|---|---|---|
| 10/11/2026 | Avance 1 | Acceso de usuarios: login, registro y sesión. |
| 11/11/2026 | Avance 2 | Catálogo de productos con tarjetas y precios en pesos colombianos. |
| 12/11/2026 | Avance 3 | Catálogo funcional, carrito con `Cola` circular e historial con `Pila`. |

El historial completo de commits está disponible en:
https://github.com/JulioV08/Proyecto-Final-Tienda/commits/main

## Avance 1 — Acceso de usuarios
- Inicio de sesión y registro con validaciones.
- Los usuarios se guardan en memoria con `Nodo` y `ListaEnlazada`.
- Cuentas de prueba:
  - Cliente: `lucia.martin@email.com` / `123456`
  - Administrador: `admin@nodo.com` / `admin123`

## Avance 2 — Catálogo de productos
- Catálogo con encabezado, buscador, filtros visuales y tarjetas de producto.
- Al iniciar sesión, el catálogo se abre directamente.
- Precios en pesos colombianos, categoría, stock y calificación.
- Los productos se guardan en memoria con `NodoProducto` y `ListaProductos`.
- `Nodo` y `ListaEnlazada` quedaron específicos para `Usuario`.
- Se agregó `Launcher` para ejecutar el proyecto desde NetBeans con el botón Run.
- El acceso, el registro y la sesión siguen funcionando igual.

## Avance 3 — Catálogo funcional, carrito y compras
- Filtros por categoría, precio, disponibilidad y búsqueda.
- Orden por precio y contador de resultados.
- Carrito con `Cola` circular, contador en el encabezado y total en pesos.
- `Mis compras` con `Pila`; cada compra finalizada queda registrada.
- El carrito y el historial se guardan en memoria, igual que los usuarios y los productos.

