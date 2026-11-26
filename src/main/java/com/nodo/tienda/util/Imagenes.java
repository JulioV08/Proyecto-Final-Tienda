package com.nodo.tienda.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nodo.tienda.modelo.Producto;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public final class Imagenes {

    private Imagenes() {
    }

    public static Image cargar(Producto producto) {
        String ruta = producto.getImagen();
        if (ruta == null || ruta.isEmpty()) {
            return null;
        }
        try {
            InputStream flujo;
            if (ruta.startsWith("/")) {
                flujo = Imagenes.class.getResourceAsStream(ruta);
                if (flujo == null) {
                    Path archivo = Paths.get("src", "main", "resources" + ruta);
                    if (Files.exists(archivo)) {
                        flujo = Files.newInputStream(archivo);
                    }
                }
            } else {
                flujo = new FileInputStream(ruta);
            }
            if (flujo == null) {
                return null;
            }
            return new Image(flujo);
        } catch (Exception e) {
            return null;
        }
    }

    public static ImageView crear(Producto producto, double ancho, double alto) {
        ImageView vista = new ImageView();
        vista.setFitWidth(ancho);
        vista.setFitHeight(alto);
        vista.setPreserveRatio(true);
        vista.setSmooth(true);
        Image imagen = cargar(producto);
        if (imagen != null) {
            vista.setImage(imagen);
        }
        return vista;
    }

    public static void aplicar(ImageView vista, Producto producto) {
        Image imagen = cargar(producto);
        if (imagen != null) {
            vista.setImage(imagen);
        }
    }
}
