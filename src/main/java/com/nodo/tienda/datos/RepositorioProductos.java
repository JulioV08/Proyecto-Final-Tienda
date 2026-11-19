package com.nodo.tienda.datos;

import com.nodo.tienda.estructuras.ListaProductos;
import com.nodo.tienda.modelo.Producto;


public class RepositorioProductos {

    private static final ListaProductos productos = new ListaProductos();

    static {
        cargarDatosDePrueba();
    }

    private RepositorioProductos() {
    }

    public static void agregar(Producto producto) {
        productos.agregar(producto);
    }

    public static Producto obtener(int indice) {
        return productos.obtener(indice);
    }

    public static int tamanio() {
        return productos.tamanio();
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
