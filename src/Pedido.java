import java.util.ArrayList;

public class Pedido {
    private ArrayList<Producto> productos;
    private double subtotal;
    private double descuento;
    private double total;
    private String estado;
    private String cliente;
    private String metodoPago;
    private String correo;

    public Pedido() {
        productos = new ArrayList<>();
        subtotal = 0;
        descuento = 0;
        total = 0;
        estado = "En preparación";
        cliente = "Cliente general";
        metodoPago = "No especificado";
        correo = "No especificado";
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        subtotal = subtotal + producto.getPrecio();
        calcularTotal();
    }

    public String quitarProducto(int posicion) {
        int indice = posicion - 1;

        if (indice >= 0 && indice < productos.size()) {
            Producto eliminado = productos.remove(indice);
            subtotal = subtotal - eliminado.getPrecio();
            calcularTotal();

            return "Se quitó: " + eliminado.getNombre();
        }

        return "Posición inválida";
    }

    public void limpiarPedido() {
        productos.clear();
        subtotal = 0;
        descuento = 0;
        total = 0;
        estado = "En preparación";
        cliente = "Cliente general";
        metodoPago = "No especificado";
        correo = "No especificado";
    }

    public void aplicarDescuento(double descuento) {
        this.descuento = descuento;
        calcularTotal();
    }

    private void calcularTotal() {
        total = subtotal - descuento;

        if (total < 0) {
            total = 0;
        }
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public String getCliente() {
        return cliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String obtenerProductosTexto() {
        String texto = "";

        for (int i = 0; i < productos.size(); i++) {
            texto = texto + productos.get(i).getNombre();

            if (i < productos.size() - 1) {
                texto = texto + ", ";
            }
        }

        return texto;
    }

    public String obtenerDetallesPedido() {
        if (productos.size() == 0) {
            return "Carrito vacío.";
        }

        String texto = "";

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);

            texto = texto + (i + 1) + ". ";
            texto = texto + producto.getNombre();
            texto = texto + " - $" + producto.getPrecio();
            texto = texto + " - " + producto.getTipo();
            texto = texto + " - " + producto.getPais() + "\n";
        }

        texto = texto + "\nSubtotal: $" + subtotal + "\n";
        texto = texto + "Descuento: $" + descuento + "\n";
        texto = texto + "Total: $" + total + "\n";
        texto = texto + "Estado: " + estado;

        return texto;
    }

    public String generarTicket() {
        String texto = "";

        texto = texto + "RESTAURANTE MÉXICO-JAPONÉS\n";
        texto = texto + "-----------------------------\n";
        texto = texto + "Cliente: " + cliente + "\n";
        texto = texto + "Correo: " + correo + "\n";
        texto = texto + "Método de pago: " + metodoPago + "\n";

        texto = texto + "Productos:\n";

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);

            texto = texto + (i + 1) + ". ";
            texto = texto + producto.getNombre();
            texto = texto + " - $" + producto.getPrecio() + "\n";
        }

        texto = texto + "\nSubtotal: $" + subtotal + "\n";
        texto = texto + "Descuento: $" + descuento + "\n";
        texto = texto + "Total: $" + total + "\n";
        texto = texto + "Estado: " + estado + "\n";
        texto = texto + "-----------------------------\n";
        texto = texto + "Gracias por su compra.";

        return texto;
    }
    
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}