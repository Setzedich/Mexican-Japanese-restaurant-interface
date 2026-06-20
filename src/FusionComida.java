public class FusionComida {

    public FusionComida() {
    }

    public String sugerirFusion(Producto producto1, Producto producto2) {
        String nombre1 = producto1.getNombre();
        String nombre2 = producto2.getNombre();

        if (producto1.getPais().equals("Mexicana") && producto2.getPais().equals("Japonesa")) {
            return "Sugerencia de fusión: " + nombre1 + " con " + nombre2;
        }

        if (producto1.getPais().equals("Japonesa") && producto2.getPais().equals("Mexicana")) {
            return "Sugerencia de fusión: " + nombre2 + " con " + nombre1;
        }

        return "Para generar una fusión se recomienda elegir un producto mexicano y uno japonés.";
    }
}