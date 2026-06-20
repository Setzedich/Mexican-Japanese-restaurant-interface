public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String tipo;
    private String pais;

    public Producto(int id, String nombre, double precio, String tipo, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPais() {
        return pais;
    }

    public String obtenerTexto() {
        return id + " - " + nombre + " ($" + precio + ") [" + tipo + " / " + pais + "]";
    }
}