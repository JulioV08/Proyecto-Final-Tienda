package com.nodo.tienda.controladores;

import java.io.IOException;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.Carrito;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.Sesion;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * Controlador del catálogo de productos.
 */
public class CatalogoController {

    @FXML
    private Label saludo;

    @FXML
    private Label contador;

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
    private Button carrito;

    @FXML
    public void initialize() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario != null) {
            saludo.setText(usuario.getNombre() + " " + usuario.getApellidos());
        } else {
            saludo.setText("Invitado");
        }

        prepararFiltros();
        actualizarCarrito();
        aplicarFiltros();
    }

    private void prepararFiltros() {
        orden.getItems().addAll("Recomendados", "Precio: menor a mayor", "Precio: mayor a menor");
        orden.setValue("Recomendados");

        categorias.getSelectionModel().selectFirst();

        busqueda.textProperty().addListener((observable, anterior, nuevo) -> aplicarFiltros());
        precio.valueProperty().addListener((observable, anterior, nuevo) -> aplicarFiltros());
        soloDisponibles.setOnAction(evento -> aplicarFiltros());
        orden.setOnAction(evento -> aplicarFiltros());
        categorias.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, nuevo) -> aplicarFiltros());
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
    }

    private boolean cumpleFiltros(Producto producto, String categoria, String texto) {
        if (!categoria.equals("Todas") && !producto.getCategoria().equals(categoria)) {
            return false;
        }
        if (producto.getPrecio() > precio.getValue()) {
            return false;
        }
        if (soloDisponibles.isSelected() && producto.getStock() <= 0) {
            return false;
        }
        return texto.isEmpty() || producto.getNombre().toLowerCase().contains(texto);
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
            ProductoCardController controlador = loader.getController();
            controlador.setProducto(producto, this);
            contenedorProductos.getChildren().add(tarjeta);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la tarjeta del producto", e);
        }
    }

    public void agregarAlCarrito(Producto producto) {
        if (producto.getStock() > 0) {
            Carrito.agregar(producto);
            actualizarCarrito();
        }
    }

    private void actualizarCarrito() {
        carrito.setText("🛒 " + Carrito.tamanio());
    }

    @FXML
    private void onCarrito() {
        App.irA("carrito");
    }

    @FXML
    private void onHistorial() {
        App.irA("historial");
    }

    @FXML
    private void onVolver() {
        App.irA("principal");
    }
}
