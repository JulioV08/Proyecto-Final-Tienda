package com.nodo.tienda.controladores;

import java.util.Locale;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Compra;
import com.nodo.tienda.modelo.Producto;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class ReportesControlador {

    @FXML
    private Label productos;

    @FXML
    private Label unidades;

    @FXML
    private Label valor;

    @FXML
    private Label pedidos;

    @FXML
    private Label usuarios;

    @FXML
    private Label umbral;

    @FXML
    private Label detalleStock;

    @FXML
    private BarChart<String, Number> graficaCategorias;

    @FXML
    private ProgressBar barSalud;

    @FXML
    public void initialize() {
        if (RepositorioUsuarios.getUsuario() == null
                || !RepositorioUsuarios.getUsuario().esAdministrador()) {
            App.irA("login");
            return;
        }

        int totalUnidades = 0;
        int audio = 0;
        int escritorio = 0;
        int iluminacion = 0;
        int accesorios = 0;
        int consolas = 0;
        int celulares = 0;
        int electrodomesticos = 0;
        int disponibles = 0;
        int agotados = 0;
        int bajoStock = 0;
        long valorInventario = 0;

        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            totalUnidades += producto.getStock();
            valorInventario += (long) producto.getPrecio() * producto.getStock();

            if (producto.getStock() == 0) {
                agotados++;
            } else {
                disponibles++;
            }
            if (producto.getStock() > 0 && producto.getStock() <= 5) {
                bajoStock++;
            }

            String categoria = producto.getCategoria();
            if ("Audio".equals(categoria)) {
                audio += producto.getStock();
            } else if ("Escritorio".equals(categoria)) {
                escritorio += producto.getStock();
            } else if ("Iluminación".equals(categoria)) {
                iluminacion += producto.getStock();
            } else if ("Accesorios".equals(categoria)) {
                accesorios += producto.getStock();
            } else if ("Consolas".equals(categoria)) {
                consolas += producto.getStock();
            } else if ("Celulares".equals(categoria)) {
                celulares += producto.getStock();
            } else if ("Electrodomésticos".equals(categoria)) {
                electrodomesticos += producto.getStock();
            }
        }

        int totalVentas = 0;
        for (int i = 0; i < Carrito.tamanioHistorial(); i++) {
            Compra compra = Carrito.obtenerCompra(i);
            totalVentas += compra.getTotal();
        }

        productos.setText(String.valueOf(RepositorioProductos.tamanio()));
        unidades.setText(String.valueOf(totalUnidades));
        valor.setText(formato(valorInventario));
        pedidos.setText(formato(totalVentas));
        usuarios.setText("Usuarios: " + RepositorioUsuarios.tamanio());
        umbral.setText("Tope máximo: " + RepositorioProductos.getUmbralStock());
        detalleStock.setText("Disponibles: " + disponibles
                + " · Stock bajo: " + bajoStock
                + " · Agotados: " + agotados);

        String[] nombres = {"Accesorios", "Audio", "Celulares", "Consolas",
                "Escritorio", "Electrodomésticos", "Iluminación"};
        int[] cantidades = {accesorios, audio, celulares, consolas,
                escritorio, electrodomesticos, iluminacion};
        for (int i = 0; i < cantidades.length - 1; i++) {
            for (int j = i + 1; j < cantidades.length; j++) {
                if (cantidades[j] > cantidades[i]) {
                    int aux = cantidades[i];
                    cantidades[i] = cantidades[j];
                    cantidades[j] = aux;
                    String temporal = nombres[i];
                    nombres[i] = nombres[j];
                    nombres[j] = temporal;
                }
            }
        }
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Unidades");
        for (int i = 0; i < nombres.length; i++) {
            serie.getData().add(new XYChart.Data<>(nombres[i], cantidades[i]));
        }
        graficaCategorias.getData().clear();
        graficaCategorias.getData().add(serie);

        int total = RepositorioProductos.tamanio();
        barSalud.setProgress(total == 0 ? 0 : (double) disponibles / total);
    }

    private String formato(long valor) {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", valor);
    }

    @FXML
    private void onVolver() {
        App.irA("admin");
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
