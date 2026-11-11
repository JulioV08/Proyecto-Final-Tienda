package com.nodo.tienda;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada de la Tienda Digital Nodo.
 */
public class App extends Application {

    private static Scene escena;

    @Override
    public void start(Stage stage) throws IOException {
        escena = new Scene(cargar("login"));

        stage.setTitle("nodo · Tienda Digital");
        stage.setScene(escena);
        stage.show();
    }

    /**
     * Cambia la vista que se muestra en la ventana.
     *
     * @param vista nombre del archivo FXML sin la extensión
     */
    public static void irA(String vista) {
        try {
            escena.setRoot(cargar(vista));
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo abrir la vista: " + vista, e);
        }
    }

    private static Parent cargar(String vista) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("vistas/" + vista + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
