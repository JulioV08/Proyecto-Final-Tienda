package com.nodo.tienda.controladores;

import java.util.Locale;

import com.nodo.tienda.App;
import com.nodo.tienda.datos.RepositorioProductos;
import com.nodo.tienda.datos.RepositorioUsuarios;
import com.nodo.tienda.modelo.Producto;
import com.nodo.tienda.modelo.Usuario;
import com.nodo.tienda.util.Alertas;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AdminControlador {

    @FXML
    private Label saludo;

    @FXML
    private Label usuario;

    @FXML
    private Label productos;

    @FXML
    private Label unidades;

    @FXML
    private Label agotados;

    @FXML
    private Label valor;

    @FXML
    private Label bajoStock;

    @FXML
    private ListView<String> inventario;

    @FXML
    private TextField buscarId;

    @FXML
    private Label idProducto;

    @FXML
    private TextField nombre;

    @FXML
    private ComboBox<String> categoria;

    @FXML
    private TextField precio;

    @FXML
    private TextField stock;

    @FXML
    private TextField agregarUnidades;

    @FXML
    private TextField umbralStock;

    @FXML
    private Label stockActual;

    @FXML
    private TextArea descripcion;

    @FXML
    private Label mensaje;

    private Producto productoEditando;

    @FXML
    public void initialize() {
        Usuario actual = RepositorioUsuarios.getUsuario();
        if (actual == null || !actual.esAdministrador()) {
            App.irA("login");
            return;
        }

        saludo.setText("Hola, " + actual.getNombre() + " " + actual.getApellidos());
        usuario.setText(actual.getNombre() + " " + actual.getApellidos() + " · Administrador");
        categoria.getItems().addAll("Audio", "Escritorio", "Iluminación", "Accesorios",
                "Consolas", "Celulares", "Electrodomésticos");
        actualizarResumen();
        onLimpiar();
        umbralStock.setText(String.valueOf(RepositorioProductos.getUmbralStock()));

        inventario.getSelectionModel().selectedItemProperty().addListener((observable, anterior, nuevo) -> {
            if (nuevo == null || !nuevo.startsWith("#")) {
                return;
            }
            int fin = nuevo.indexOf(" ·");
            if (fin < 0) {
                return;
            }
            buscarId.setText(nuevo.substring(1, fin).trim());
            onBuscar();
        });

        if (!inventario.getItems().isEmpty()) {
            inventario.getSelectionModel().selectFirst();
        }
    }

    private void actualizarResumen() {
        int totalUnidades = 0;
        int totalAgotados = 0;
        int totalBajoStock = 0;
        long valorInventario = 0;
        inventario.getItems().clear();

        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            totalUnidades += producto.getStock();
            if (producto.getStock() == 0) {
                totalAgotados++;
            }
            if (producto.getStock() > 0 && producto.getStock() <= 5) {
                totalBajoStock++;
            }
            valorInventario += (long) producto.getPrecio() * producto.getStock();
            inventario.getItems().add("#" + producto.getId() + " · "
                    + producto.getNombre() + " · "
                    + producto.getCategoria() + " · "
                    + producto.getStock() + " unidades · "
                    + producto.getPrecioTexto());
        }

        productos.setText(String.valueOf(RepositorioProductos.tamanio()));
        unidades.setText(String.valueOf(totalUnidades));
        agotados.setText(String.valueOf(totalAgotados));
        valor.setText(String.format(Locale.forLanguageTag("es-CO"), "$%,d", valorInventario));
        bajoStock.setText("Stock bajo: " + totalBajoStock
                + (totalBajoStock == 1 ? " producto" : " productos"));
    }

    @FXML
    private void onBuscar() {
        String busqueda = texto(buscarId);
        if (busqueda.isEmpty()) {
            mostrar("Escribe un ID o un nombre.");
            return;
        }

        Producto encontrado = null;
        try {
            encontrado = RepositorioProductos.buscarPorId(Integer.parseInt(busqueda));
        } catch (NumberFormatException e) {
            String nombreBuscado = busqueda.toLowerCase();
            for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
                Producto candidato = RepositorioProductos.obtener(i);
                if (candidato.getNombre().toLowerCase().contains(nombreBuscado)) {
                    encontrado = candidato;
                    break;
                }
            }
        }

        if (encontrado == null) {
            productoEditando = null;
            idProducto.setText("Producto sin seleccionar");
            stockActual.setText("Stock actual: -");
            limpiarFormulario();
            mostrar("No existe un producto con ese ID o nombre.");
            return;
        }

        productoEditando = encontrado;
        idProducto.setText("Producto #" + encontrado.getId() + " · " + encontrado.getNombre());
        stockActual.setText("Stock actual: " + encontrado.getStock()
                + " / Tope: " + RepositorioProductos.getUmbralStock());
        nombre.setText(encontrado.getNombre());
        categoria.setValue(encontrado.getCategoria());
        precio.setText(String.valueOf(encontrado.getPrecio()));
        stock.setText(String.valueOf(encontrado.getStock()));
        descripcion.setText(encontrado.getDescripcion());
        mostrar("Producto encontrado. Puedes agregar unidades o editar sus datos.");
    }

    @FXML
    private void onGuardar() {
        if (productoEditando == null) {
            mostrar("Primero busca un producto por su ID.");
            return;
        }

        String nombreNuevo = texto(nombre);
        String categoriaNueva = categoria.getValue() == null ? "" : categoria.getValue().trim();
        String descripcionNueva = texto(descripcion);
        if (nombreNuevo.isEmpty() || categoriaNueva.isEmpty() || descripcionNueva.isEmpty()) {
            mostrar("Completa el nombre, la categoría y la descripción.");
            return;
        }

        int precioNuevo;
        int stockNuevo;
        try {
            precioNuevo = Integer.parseInt(texto(precio));
            stockNuevo = Integer.parseInt(texto(stock));
        } catch (NumberFormatException e) {
            mostrar("El precio y el stock deben ser números.");
            return;
        }

        if (precioNuevo < 0 || stockNuevo < 0) {
            mostrar("El precio y el stock no pueden ser negativos.");
            return;
        }

        int tope = RepositorioProductos.getUmbralStock();
        if (stockNuevo > tope) {
            mostrar("El stock no puede superar el tope de " + tope + " unidades.");
            return;
        }

        productoEditando.setNombre(nombreNuevo);
        productoEditando.setCategoria(categoriaNueva);
        productoEditando.setPrecio(precioNuevo);
        productoEditando.setStock(stockNuevo);
        productoEditando.setDescripcion(descripcionNueva);
        stockActual.setText("Stock actual: " + productoEditando.getStock()
                + " / Tope: " + RepositorioProductos.getUmbralStock());
        RepositorioProductos.guardar();

        actualizarResumen();
        mostrar("Cambios guardados en el producto #" + productoEditando.getId() + ".");
        Alertas.info("Cambios guardados", "El producto #" + productoEditando.getId() + " se actualizó correctamente.");
    }

    @FXML
    private void onAgregarUnidades() {
        if (productoEditando == null && !texto(buscarId).isEmpty()) {
            onBuscar();
        }
        if (productoEditando == null) {
            mostrar("Selecciona un producto de la lista o búscalo por ID o nombre.");
            return;
        }

        int extras;
        try {
            extras = Integer.parseInt(texto(agregarUnidades));
        } catch (NumberFormatException e) {
            mostrar("Escribe cuántas unidades agregar.");
            return;
        }

        if (extras <= 0) {
            mostrar("La cantidad debe ser mayor a 0.");
            return;
        }

        int tope = RepositorioProductos.getUmbralStock();
        int actual = productoEditando.getStock();
        int nuevo = Math.min(actual + extras, tope);
        int agregadas = nuevo - actual;
        if (agregadas <= 0) {
            mostrar("El producto ya está en el tope máximo de " + tope + " unidades.");
            return;
        }
        productoEditando.setStock(nuevo);
        stock.setText(String.valueOf(nuevo));
        stockActual.setText("Stock actual: " + nuevo + " / Tope: " + tope);
        RepositorioProductos.guardar();
        actualizarResumen();
        mostrar("Se agregaron " + agregadas + " unidades. Stock final: " + nuevo + " / " + tope + ".");
        Alertas.info("Stock actualizado", "Ahora hay " + nuevo + " unidades de " + productoEditando.getNombre() + ".");
    }

    @FXML
    private void onQuitarUnidades() {
        if (productoEditando == null && !texto(buscarId).isEmpty()) {
            onBuscar();
        }
        if (productoEditando == null) {
            mostrar("Selecciona un producto de la lista o búscalo por ID o nombre.");
            return;
        }

        int quitar;
        try {
            quitar = Integer.parseInt(texto(agregarUnidades));
        } catch (NumberFormatException e) {
            mostrar("Escribe cuántas unidades quitar.");
            return;
        }
        if (quitar <= 0) {
            mostrar("La cantidad debe ser mayor a 0.");
            return;
        }
        if (productoEditando.getStock() == 0) {
            mostrar("El producto ya está agotado.");
            return;
        }

        int actual = productoEditando.getStock();
        int nuevo = Math.max(0, actual - quitar);
        int quitadas = actual - nuevo;
        productoEditando.setStock(nuevo);
        stock.setText(String.valueOf(nuevo));
        stockActual.setText("Stock actual: " + nuevo + " / Tope: " + RepositorioProductos.getUmbralStock());
        RepositorioProductos.guardar();
        actualizarResumen();
        mostrar("Se quitaron " + quitadas + " unidades. Stock final: " + nuevo + ".");
        Alertas.info("Stock actualizado", "Ahora hay " + nuevo + " unidades de " + productoEditando.getNombre() + ".");
    }

    @FXML
    private void onLimpiar() {
        productoEditando = null;
        buscarId.clear();
        nombre.clear();
        categoria.setValue(null);
        precio.clear();
        stock.clear();
        agregarUnidades.clear();
        agregarUnidades.setText("20");
        descripcion.clear();
        idProducto.setText("Producto sin seleccionar");
        stockActual.setText("Stock actual: -");
        mostrar("Busca un producto por ID o nombre.");
    }

    private String texto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String texto(TextArea campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void limpiarFormulario() {
        nombre.clear();
        categoria.setValue(null);
        precio.clear();
        stock.clear();
        agregarUnidades.clear();
        agregarUnidades.setText("20");
        descripcion.clear();
    }

    private void mostrar(String texto) {
        mensaje.setText(texto);
    }

    @FXML
    private void onInventario() {
        actualizarResumen();
        mostrar("Estás en Inventario.");
    }

    @FXML
    private void onPedidos() {
        App.irA("pedidos");
    }

    @FXML
    private void onUsuarios() {
        App.irA("usuarios");
    }

    @FXML
    private void onReportes() {
        App.irA("reportes");
    }

    @FXML
    private void onConfiguracion() {
        App.irA("configuracion");
    }

    @FXML
    private void onCuenta() {
        App.irA("cuenta");
    }

    @FXML
    private void onRegistrarProducto() {
        App.irA("nuevo-producto");
    }

    @FXML
    private void onEliminar() {
        if (productoEditando == null) {
            String busqueda = texto(buscarId);
            try {
                int id = Integer.parseInt(busqueda);
                productoEditando = RepositorioProductos.buscarPorId(id);
            } catch (NumberFormatException e) {
                productoEditando = null;
            }
            if (productoEditando == null) {
                mostrar("Busca un producto por ID o nombre antes de eliminarlo.");
                return;
            }
        }
        if (!Alertas.confirmar("Eliminar producto", "¿Seguro que quieres eliminar " + productoEditando.getNombre() + "?")) {
            return;
        }
        RepositorioProductos.eliminarPorId(productoEditando.getId());
        productoEditando = null;
        onLimpiar();
        actualizarResumen();
        mostrar("Producto eliminado del inventario.");
        Alertas.info("Producto eliminado", "El producto se quitó del inventario y se guardó el cambio.");
    }

    @FXML
    private void onGuardarUmbral() {
        int valor;
        try {
            valor = Integer.parseInt(texto(umbralStock));
        } catch (NumberFormatException e) {
            mostrar("El tope debe ser un número.");
            return;
        }
        if (valor < 1 || valor > 1000) {
            mostrar("El tope debe estar entre 1 y 1000 unidades.");
            return;
        }
        int ajustados = 0;
        for (int i = 0; i < RepositorioProductos.tamanio(); i++) {
            Producto producto = RepositorioProductos.obtener(i);
            if (producto.getStock() > valor) {
                producto.setStock(valor);
                ajustados++;
            }
        }
        RepositorioProductos.setUmbralStock(valor);
        if (productoEditando != null) {
            stock.setText(String.valueOf(productoEditando.getStock()));
            stockActual.setText("Stock actual: " + productoEditando.getStock() + " / Tope: " + valor);
        }
        actualizarResumen();
        String detalle = ajustados == 0
                ? "Ningún producto superaba el nuevo tope."
                : "Se ajustaron " + ajustados + " producto(s) que superaban el tope.";
        mostrar("Tope máximo guardado en " + valor + " unidades. " + detalle);
        Alertas.info("Tope actualizado", "Ningún producto supera " + valor + " unidades. " + detalle);
    }

    @FXML
    private void onCerrarSesion() {
        RepositorioUsuarios.cerrarSesion();
        App.irA("login");
    }
}
