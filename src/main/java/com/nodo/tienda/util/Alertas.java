package com.nodo.tienda.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public final class Alertas {

    private Alertas() {
    }

    public static void info(String titulo, String mensaje) {
        if (silenciar()) {
            return;
        }
        mostrar(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public static void error(String titulo, String mensaje) {
        if (silenciar()) {
            return;
        }
        mostrar(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public static void factura(String titulo, String mensaje) {
        if (silenciar()) {
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("nodo · Factura");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.getDialogPane().setPrefWidth(560);
        if (capturar()) {
            alerta.show();
            return;
        }
        alerta.showAndWait();
    }

    public static boolean confirmar(String titulo, String mensaje) {
        if (silenciar()) {
            return true;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("nodo");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        if (capturar()) {
            alerta.show();
            return true;
        }
        return alerta.showAndWait().filter(ButtonType.OK::equals).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("nodo");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        if (capturar()) {
            alerta.show();
            return;
        }
        alerta.showAndWait();
    }

    private static boolean silenciar() {
        return Boolean.getBoolean("nodo.test.alertas");
    }

    private static boolean capturar() {
        return Boolean.getBoolean("nodo.test.alertas.capturar");
    }
}
