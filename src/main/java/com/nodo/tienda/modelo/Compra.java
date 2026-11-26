package com.nodo.tienda.modelo;

import java.util.Locale;


public class Compra {

    private final String fecha;
    private final int cantidad;
    private final int total;
    private final Producto[] productos;
    private final String numero;

    public Compra(String fecha, int cantidad, int total) {
        this(fecha, cantidad, total, new Producto[0]);
    }

    public Compra(String fecha, int cantidad, int total, Producto[] productos) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.total = total;
        this.productos = productos;
        this.numero = "N-" + fecha.replace("/", "")
                + "-" + String.format("%04d", Math.abs((int) (System.currentTimeMillis() % 10000)));
    }

    public String getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTotal() {
        return total;
    }

    public String getNumero() {
        return numero;
    }

    public String getTotalTexto() {
        return formato(total);
    }

    public Producto[] getProductos() {
        return productos;
    }

    public String getFactura(String cliente) {
        StringBuilder texto = new StringBuilder();
        texto.append("==============================\n");
        texto.append("         nodo · FACTURA\n");
        texto.append("==============================\n");
        texto.append("No: ").append(numero).append("\n");
        texto.append("Fecha: ").append(fecha).append("\n");
        texto.append("Cliente: ").append(cliente).append("\n");
        texto.append("Vendedor: nodo · Tienda Digital\n");
        texto.append("--------------------------------\n");
        texto.append("PRODUCTO              CANT   VALOR\n");
        texto.append("--------------------------------\n");

        for (int i = 0; i < productos.length; i++) {
            String nombre = productos[i].getNombre();
            boolean repetido = false;
            for (int j = 0; j < i; j++) {
                if (productos[j].getNombre().equals(nombre)) {
                    repetido = true;
                    break;
                }
            }
            if (repetido) {
                continue;
            }
            int unidades = 0;
            for (int j = 0; j < productos.length; j++) {
                if (productos[j].getNombre().equals(nombre)) {
                    unidades++;
                }
            }
            texto.append(nombre).append("  x").append(unidades)
                    .append("  ").append(formato(productos[i].getPrecio() * unidades)).append("\n");
        }

        long base = Math.round(total / 1.19);
        long iva = total - base;
        texto.append("--------------------------------\n");
        texto.append("Subtotal sin IVA: ").append(formato(base)).append("\n");
        texto.append("IVA 19%: ").append(formato(iva)).append("\n");
        texto.append("TOTAL: ").append(getTotalTexto()).append("\n");
        texto.append("--------------------------------\n");
        texto.append("Forma de pago: Tarjeta / contraentrega\n");
        texto.append("Estado: PAGADA\n");
        texto.append("Gracias por comprar en nodo.\n");
        return texto.toString();
    }

    private String formato(long valor) {
        return String.format(Locale.forLanguageTag("es-CO"), "$%,d", valor);
    }

    @Override
    public String toString() {
        return fecha + " · " + cantidad
                + (cantidad == 1 ? " producto" : " productos")
                + " · " + getTotalTexto();
    }
}
