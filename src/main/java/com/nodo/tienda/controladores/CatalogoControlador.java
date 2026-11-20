package com.nodo.tienda.controladores;

import java.io.IOException;
import java.util.Locale;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


public class CatalogoControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label rol;

    @FXML
    private Label contador;

    @FXML
    private Label mensaje;

    @FXML
    private FlowPane contenedorProductos;

    @FXML
    private ComboBox<String> orden;

    @FXML
    private TextField busqueda;

    @FXML
    private ListView<String> categorias;

    @FXML
    private Slider precio;

    @FXML
    private CheckBox soloDisponibles;

    @FXML
    private Label precioActual;

    @FXML
    private TextField precioExacto;

    @FXML
    public void initialize() {
        Usuario usuario = RepositorioUsuarios.getUsuario();
        if (usuario == null) {
            saludo.setText("Invitado");
            rol.setText("Sin sesión");
        } else {
            saludo.setText(usuario.getNombreCompleto());
            rol.setText(usuario.getRol());
        }

        orden.getItems().clear();
        orden.getItems().addAll("Recomendados", "Precio: menor a mayor", "Precio: mayor a menor");
        orden.setValue("Recomendados");
        categorias.getSelectionModel().selectFirst();

        precioActual.setText("Hasta " + formatoPrecio((int) precio.getValue()));
        precioExacto.setText(String.valueOf((int) precio.getValue()));

        busqueda.textProperty().addListener((observable, anterior, nuevo) -> aplicarFiltros());
        precio.valueProperty().addListener((observable, anterior, nuevo) -> {
            int valor = nuevo.intValue();
            precioActual.setText("Hasta " + formatoPrecio(valor));
            precioExacto.setText(String.valueOf(valor));
            aplicarFiltros();
        });
        soloDisponibles.setOnAction(evento -> aplicarFiltros());
        orden.setOnAction(evento -> aplicarFiltros());
        categorias.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, nuevo) -> aplicarFiltros());

        aplicarFiltros();
    }

    private void aplicarFiltros() {
        String categoria = categorias.getSelectionModel().getSelectedItem();
        if (categoria == null) {
            categoria = "Todas";
        }
        String texto = busqueda.getText() == null ? "" : busqueda.getText().trim().toLowerCase();

        Producto[] visibles = new Producto[RepositorioProductos.tamanio()];
        int cantidad = 0;
        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            if (cumpleFiltros(producto, categoria, texto)) {
                visibles[cantidad] = producto;
                cantidad++;
            }
        }

        ordenar(visibles, cantidad);
        contenedorProductos.getChildren().clear();
        for (int i = 0; i < cantidad; i++) {
            cargarProducto(visibles[i]);
        }

        contador.setText(cantidad + (cantidad == 1 ? " producto para ti" : " productos para ti"));
        boolean vacio = cantidad == 0;
        mensaje.setText(vacio ? "No encontramos productos con esos filtros." : "");
        mensaje.setVisible(vacio);
        mensaje.setManaged(vacio);
    }

    private boolean cumpleFiltros(Producto producto, String categoria, String texto) {
        if (!categoria.equals("Todas") && !producto.getCategoria().equals(categoria)) {
            return false;
        }
        if (producto.getPrecio() > precio.getValue()) {
            return false;
        }
        if (soloDisponibles.isSelected() && !producto.estaDisponible()) {
            return false;
        }
        return texto.isEmpty()
                || producto.getNombre().toLowerCase().contains(texto)
                || producto.getCategoria().toLowerCase().contains(texto);
    }

    private void ordenar(Producto[] visibles, int cantidad) {
        if (orden.getValue() == null || orden.getValue().equals("Recomendados")) {
            return;
        }

        boolean menorAMayor = orden.getValue().equals("Precio: menor a mayor");
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = i + 1; j < cantidad; j++) {
                boolean cambiar = menorAMayor
                        ? visibles[j].getPrecio() < visibles[i].getPrecio()
                        : visibles[j].getPrecio() > visibles[i].getPrecio();
                if (cambiar) {
                    Producto auxiliar = visibles[i];
                    visibles[i] = visibles[j];
                    visibles[j] = auxiliar;
                }
            }
        }
    }

    private void cargarProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("vistas/producto.fxml"));
            Parent tarjeta = loader.load();
            TarjetaControlador controlador = loader.getController();
            controlador.setProducto(producto, this);
            contenedorProductos.getChildren().add(tarjeta);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la tarjeta del producto", e);
        }
    }

    public void verDetalle(Producto producto) {
        DetalleControlador.seleccionar(producto);
        App.irA("detalle");
    }

    public void agregarAlCarrito(Producto producto) {
        if (producto != null && producto.estaDisponible()) {
            Carrito.agregar(producto);
        }
    }

    @FXML
    private void onCarrito() {
        App.irA("carrito");
    }

    @FXML
    private void onBuscar() {
        aplicarFiltros();
    }

    @FXML
    private void onAplicarPrecio() {
        String texto = precioExacto.getText() == null
                ? ""
                : precioExacto.getText().replaceAll("[^0-9]", "");
        int valor = 150000;
        if (!texto.isEmpty()) {
            try {
                valor = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                valor = (int) precio.getValue();
            }
        }
        valor = Math.max(0, Math.min(150000, valor));
        precio.setValue(valor);
        precioExacto.setText(String.valueOf(valor));
    }

    @FXML
    private void onPrecio50() {
        precio.setValue(50000);
    }

    @FXML
    private void onPrecio80() {
        precio.setValue(80000);
    }

    @FXML
    private void onPrecio120() {
        precio.setValue(120000);
    }

    @FXML
    private void onLimpiarFiltros() {
        busqueda.clear();
        categorias.getSelectionModel().selectFirst();
        precio.setValue(150000);
        precioExacto.setText("150000");
        precioActual.setText("Hasta " + formatoPrecio(150000));
        soloDisponibles.setSelected(false);
        orden.setValue("Recomendados");
        aplicarFiltros();
    }

    private String formatoPrecio(int valor) {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", valor);
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
