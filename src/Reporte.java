public class Reporte {
    private String titulo;
    private String contenido;

    public Reporte() {
    }

    public Reporte(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String obtenerTextoReporte() {
        return titulo + "\n" + contenido;
    }
}