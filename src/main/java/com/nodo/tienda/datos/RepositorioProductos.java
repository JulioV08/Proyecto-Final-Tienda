package com.nodo.tienda.datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nodo.tienda.estructuras.ListaProductos;
import com.nodo.tienda.modelo.Producto;


public class RepositorioProductos {

    private static final Path ARCHIVO = Paths.get("productos.txt");
    private static final ListaProductos productos = new ListaProductos();
    private static int siguienteId = 1;
    private static int umbralStock = 80;
    private static boolean semillaAplicada = false;

    static {
        cargar();
        if (!semillaAplicada) {
            if (productos.tamanio() == 0) {
                cargarDatosDePrueba();
            }
            asegurarProductosNuevos();
            semillaAplicada = true;
            guardar();
        }
        int mayorStock = 0;
        for (int i = 0; i < productos.tamanio(); i++) {
            mayorStock = Math.max(mayorStock, productos.obtener(i).getStock());
        }
        if (umbralStock < 1 || umbralStock < mayorStock) {
            umbralStock = Math.max(80, mayorStock);
            guardar();
        }
    }

    private RepositorioProductos() {
    }

    public static void agregar(Producto producto) {
        producto.setId(siguienteId);
        siguienteId++;
        productos.agregar(producto);
        guardar();
    }

    public static Producto obtener(int indice) {
        return productos.obtener(indice);
    }

    public static Producto buscarPorId(int id) {
        return productos.buscarPorId(id);
    }

    public static boolean eliminarPorId(int id) {
        boolean eliminado = productos.eliminarPorId(id);
        if (eliminado) {
            guardar();
        }
        return eliminado;
    }

    public static int getUmbralStock() {
        return umbralStock;
    }

    public static void setUmbralStock(int umbralStock) {
        RepositorioProductos.umbralStock = umbralStock;
        guardar();
    }

    public static int tamanio() {
        return productos.tamanio();
    }

    public static void guardar() {
        try (BufferedWriter escritor = Files.newBufferedWriter(ARCHIVO, StandardCharsets.UTF_8)) {
            escritor.write("SEED|2");
            escritor.newLine();
            escritor.write("UMBRAL|" + umbralStock);
            escritor.newLine();
            for (int i = 0; i < productos.tamanio(); i++) {
                Producto producto = productos.obtener(i);
                escritor.write(producto.getId() + "|"
                        + limpio(producto.getNombre()) + "|"
                        + limpio(producto.getCategoria()) + "|"
                        + producto.getPrecio() + "|"
                        + producto.getStock() + "|"
                        + limpio(producto.getIcono()) + "|"
                        + producto.isDestacado() + "|"
                        + limpio(producto.getDescripcion()) + "|"
                        + (producto.getRutaImagen() == null ? "-" : limpio(producto.getRutaImagen())));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron guardar los productos", e);
        }
    }

    private static String limpio(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    private static void cargar() {
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
                if (datos[0].equals("SEED")) {
                    semillaAplicada = true;
                    continue;
                }
                if (datos[0].equals("UMBRAL") && datos.length >= 2) {
                    try {
                        umbralStock = Integer.parseInt(datos[1]);
                    } catch (NumberFormatException e) {
                        umbralStock = 5;
                    }
                    continue;
                }
                if (datos.length < 9) {
                    continue;
                }
                Producto producto = new Producto(datos[1], datos[2],
                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
                        datos[5], Boolean.parseBoolean(datos[6]), datos[7]);
                int id = Integer.parseInt(datos[0]);
                producto.setId(id);
                if (!datos[8].equals("-")) {
                    producto.setRutaImagen(datos[8]);
                }
                productos.agregar(producto);
                siguienteId = Math.max(siguienteId, id + 1);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron cargar los productos guardados", e);
        }
    }

    private static void asegurarProductosNuevos() {
        agregarSiFalta("Consola Nodo Play", "Consolas", 899000, 7, "🎮", false,
                "Consola de juegos con control inalámbrico, gráficos nítidos y entretenimiento para toda la familia.");
        agregarSiFalta("Celular Nodo X", "Celulares", 1299000, 9, "📱", true,
                "Pantalla amplia, cámara de alta resolución y batería de larga duración para tu día a día.");
        agregarSiFalta("Nevera Nodo Fresh", "Electrodomésticos", 1599000, 4, "🧊", false,
                "Refrigerador compacto con espacio inteligente, enfriamiento uniforme y bajo consumo.");

        agregarSiFalta("Audífonos Nodo Beat", "Audio", 119900, 9, "♪", false,
                "Audífonos ligeros con sonido equilibrado y almohadillas cómodas.",
                "/com/nodo/tienda/vistas/imagenes/audifonos.png");
        agregarSiFalta("Barra de sonido Nodo Wave", "Audio", 349900, 6, "▭", false,
                "Barra de sonido delgada con graves potentes para tu televisor.",
                "/com/nodo/tienda/vistas/imagenes/barra.png");

        agregarSiFalta("Silla Nodo Ergo", "Escritorio", 459000, 5, "▤", false,
                "Silla ergonómica con soporte lumbar y altura ajustable.",
                "/com/nodo/tienda/vistas/imagenes/silla.png");
        agregarSiFalta("Base Cooling Nodo", "Escritorio", 129900, 11, "❄", false,
                "Base de enfriamiento con ventiladores silenciosos para laptop.",
                "/com/nodo/tienda/vistas/imagenes/cooling.png");

        agregarSiFalta("Lámpara circular Nodo Ring", "Iluminación", 189900, 8, "◯", true,
                "Lámpara circular con luz suave e ideal para escritorio y videos.",
                "/com/nodo/tienda/vistas/imagenes/lampara-ring.png");
        agregarSiFalta("Tira LED Nodo Glow", "Iluminación", 89900, 14, "━", false,
                "Tira de luces LED flexible con varios tonos de iluminación.",
                "/com/nodo/tienda/vistas/imagenes/tira-led.png");

        agregarSiFalta("Hub USB Nodo Link", "Accesorios", 79900, 16, "⊞", false,
                "Concentrador USB compacto para conectar varios dispositivos.",
                "/com/nodo/tienda/vistas/imagenes/hub.png");
        agregarSiFalta("Soporte Nodo Stand", "Accesorios", 59900, 20, "△", false,
                "Soporte ajustable para teléfono o tablet en escritorio.",
                "/com/nodo/tienda/vistas/imagenes/soporte.png");

        agregarSiFalta("Consola Nodo Mini", "Consolas", 599000, 5, "▣", false,
                "Consola retro compacta con juegos clásicos y control inalámbrico.",
                "/com/nodo/tienda/vistas/imagenes/consola-mini.png");
        agregarSiFalta("Control Nodo Pro", "Consolas", 259900, 12, "✦", false,
                "Control inalámbrico de respuesta rápida para tus partidas.",
                "/com/nodo/tienda/vistas/imagenes/control.png");

        agregarSiFalta("Celular Nodo Lite", "Celulares", 799000, 10, "▯", false,
                "Teléfono ligero con pantalla nítida y batería para todo el día.",
                "/com/nodo/tienda/vistas/imagenes/celular-lite.png");
        agregarSiFalta("Tablet Nodo Tab", "Celulares", 999000, 7, "▭", false,
                "Tablet de 10 pulgadas para estudio, trabajo y entretenimiento.",
                "/com/nodo/tienda/vistas/imagenes/tablet.png");

        agregarSiFalta("Microondas Nodo Heat", "Electrodomésticos", 699000, 6, "□", false,
                "Microondas compacto con potencia ajustable y temporizador.",
                "/com/nodo/tienda/vistas/imagenes/microondas.png");
        agregarSiFalta("Cafetera Nodo Brew", "Electrodomésticos", 389000, 8, "☕", false,
                "Cafetera de goteo con filtro reutilizable y jarra térmica.",
                "/com/nodo/tienda/vistas/imagenes/cafetera.png");
    }

    private static void agregarSiFalta(String nombre, String categoria, int precio, int stock,
                                       String icono, boolean destacado, String descripcion) {
        agregarSiFalta(nombre, categoria, precio, stock, icono, destacado, descripcion, null);
    }

    private static void agregarSiFalta(String nombre, String categoria, int precio, int stock,
                                       String icono, boolean destacado, String descripcion, String imagen) {
        for (int i = 0; i < productos.tamanio(); i++) {
            if (productos.obtener(i).getNombre().equals(nombre)) {
                return;
            }
        }
        Producto producto = new Producto(nombre, categoria, precio, stock, icono, destacado, descripcion);
        if (imagen != null) {
            producto.setRutaImagen(imagen);
        }
        agregar(producto);
    }

    private static void cargarDatosDePrueba() {
        agregar(new Producto("Auriculares Aura", "Audio", 89900, 12, "🎧", true,
                "Sonido envolvente, almohadillas suaves y hasta 24 horas de batería para acompañarte todo el día."));
        agregar(new Producto("Teclado mecánico Tacto", "Escritorio", 79900, 10, "⌨", false,
                "Teclas mecánicas con respuesta precisa, estructura compacta y una experiencia cómoda para trabajar o jugar."));
        agregar(new Producto("Altavoz portátil Pulso", "Audio", 59900, 8, "🔊", false,
                "Sonido potente en un formato compacto, resistencia a salpicaduras y batería para llevarlo contigo."));
        agregar(new Producto("Ratón inalámbrico Trazo", "Escritorio", 34900, 15, "🖱", false,
                "Diseño ergonómico, seguimiento preciso y conexión inalámbrica estable para tu escritorio."));
        agregar(new Producto("Lámpara de escritorio Halo", "Iluminación", 49900, 6, "💡", false,
                "Luz cálida regulable y diseño minimalista para crear un espacio de trabajo cómodo."));
        agregar(new Producto("Cargador inalámbrico Base", "Accesorios", 29900, 20, "🔌", false,
                "Carga rápida y compatible con tus dispositivos; mantén tu escritorio ordenado y siempre listo."));
        agregar(new Producto("Monitor Lumen 24", "Escritorio", 139900, 9, "🖥", false,
                "Pantalla de 24 pulgadas con resolución nítida y marcos delgados para ganar espacio en tu escritorio."));
        agregar(new Producto("Micrófono Claridad", "Audio", 69900, 11, "🎤", false,
                "Captación clara y reducción de ruido, ideal para llamadas, grabaciones y contenido en línea."));
        agregar(new Producto("Batería externa Porta", "Accesorios", 55900, 18, "🔋", false,
                "Energía portátil para cargar tus dispositivos en cualquier lugar con conexión rápida y estable."));
    }
}
