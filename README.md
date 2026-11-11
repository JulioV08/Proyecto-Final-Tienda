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

El historial completo de commits está disponible en:
https://github.com/JulioV08/Proyecto-Final-Tienda/commits/main

## Avance 1 — Acceso de usuarios
- Inicio de sesión y registro con validaciones.
- Los usuarios se guardan en memoria con `Nodo` y `ListaEnlazada`.
- Cuentas de prueba:
  - Cliente: `lucia.martin@email.com` / `123456`
  - Administrador: `admin@nodo.com` / `admin123`

