import java.util.ArrayList;

public class ServicioInteligente {

    public ServicioInteligente() {
    }

    public String generarCombinacionesInteligentes(Pedido pedido) {
    	//Genera sugerencias para el pedido actual
    	//Funciona en conjunto con los otros metodos en esta clase
        if (pedido.getProductos().size() == 0) {
            return "Agrega productos al carrito para recibir sugerencias inteligentes.";
        }

        String texto = "Sugerencias inteligentes:\n\n";

        texto = texto + revisarPedidoCompleto(pedido) + "\n";
        texto = texto + sugerirBebida(pedido) + "\n";
        texto = texto + sugerirPostre(pedido) + "\n";
        texto = texto + sugerirFusion(pedido);

        return texto;
    }

    private String revisarPedidoCompleto(Pedido pedido) {
    	//Revisa si el pedido tiene comida, bebida y postre
        boolean tieneComida = false;
        boolean tieneBebida = false;
        boolean tienePostre = false;

        for (int i = 0; i < pedido.getProductos().size(); i++) {
            Producto producto = pedido.getProductos().get(i);

            if (producto.getTipo().equalsIgnoreCase("Comida")) {
                tieneComida = true;
            }

            if (producto.getTipo().equalsIgnoreCase("Bebida")) {
                tieneBebida = true;
            }

            if (producto.getTipo().equalsIgnoreCase("Postre")) {
                tienePostre = true;
            }
        }

        if (tieneComida && tieneBebida && tienePostre) {
            return "Tu pedido ya tiene comida, bebida y postre.";
        }

        String texto = "Para completar mejor tu pedido te falta: ";

        if (!tieneComida) {
            texto = texto + "comida ";
        }

        if (!tieneBebida) {
            texto = texto + "bebida ";
        }

        if (!tienePostre) {
            texto = texto + "postre ";
        }

        return texto;
    }

    private String sugerirBebida(Pedido pedido) {
        boolean tieneBebida = existeTipo(pedido, "Bebida");

        if (tieneBebida) {
            return "Ya agregaste una bebida.";
        }

        String paisPrincipal = obtenerPaisPrincipal(pedido);

        if (paisPrincipal.equalsIgnoreCase("Mexicana")) {
            return "Bebida recomendada: Agua de horchata o Agua de jamaica.";
        }

        if (paisPrincipal.equalsIgnoreCase("Japonesa")) {
            return "Bebida recomendada: Té matcha o Ramune.";
        }

        if (paisPrincipal.equalsIgnoreCase("Fusión")) {
            return "Bebida recomendada: Horchata matcha.";
        }

        return "Bebida recomendada: Agua de horchata.";
    }

    private String sugerirPostre(Pedido pedido) {
        boolean tienePostre = existeTipo(pedido, "Postre");

        if (tienePostre) {
            return "Ya agregaste un postre.";
        }

        String paisPrincipal = obtenerPaisPrincipal(pedido);

        if (paisPrincipal.equalsIgnoreCase("Mexicana")) {
            return "Postre recomendado: Flan o Churros.";
        }

        if (paisPrincipal.equalsIgnoreCase("Japonesa")) {
            return "Postre recomendado: Mochi o Dorayaki.";
        }

        if (paisPrincipal.equalsIgnoreCase("Fusión")) {
            return "Postre recomendado: Mochi de cajeta.";
        }

        return "Postre recomendado: Flan.";
    }

    private String sugerirFusion(Pedido pedido) {
        ArrayList<Producto> mexicanos = obtenerProductosPorPais(pedido, "Mexicana");
        ArrayList<Producto> japoneses = obtenerProductosPorPais(pedido, "Japonesa");

        if (mexicanos.size() == 0 || japoneses.size() == 0) {
            return "\nPara generar una fusión personalizada agrega comida mexicana y japonesa.";
        }

        String texto = "\nFusiones recomendadas:\n\n";

        for (int i = 0; i < mexicanos.size(); i++) {
            for (int j = 0; j < japoneses.size(); j++) {
                Producto mexicano = mexicanos.get(i);
                Producto japones = japoneses.get(j);

                if (mexicano.getTipo().equalsIgnoreCase("Comida")
                        && japones.getTipo().equalsIgnoreCase("Comida")) {

                    double precioNormal = mexicano.getPrecio() + japones.getPrecio();
                    double descuento = precioNormal * 0.10;
                    double precioFinal = precioNormal - descuento;

                    texto = texto + "Fusion sugerida: " + crearNombreFusion(mexicano, japones) + "\n";
                    texto = texto + mexicano.getNombre() + " + " + japones.getNombre() + "\n";
                    texto = texto + "Precio normal: $" + precioNormal + "\n";
                    texto = texto + "Precio con combo: $" + precioFinal + "\n";
                    texto = texto + "Ahorro: $" + descuento + "\n";
                    texto = texto + "-------------------------\n";
                }
            }
        }

        return texto;
    }

    private boolean existeTipo(Pedido pedido, String tipo) {
    	//Verifica si existe un tipo de producto en el pedido
        for (int i = 0; i < pedido.getProductos().size(); i++) {
            Producto producto = pedido.getProductos().get(i);

            if (producto.getTipo().equalsIgnoreCase(tipo)) {
                return true;
            }
        }

        return false;
    }

    private String obtenerPaisPrincipal(Pedido pedido) {
    	//Determina el origen principal del pedido, o sea pais
        int mexicanos = 0;
        int japoneses = 0;
        int fusiones = 0;

        for (int i = 0; i < pedido.getProductos().size(); i++) {
            Producto producto = pedido.getProductos().get(i);

            if (producto.getPais().equalsIgnoreCase("Mexicana")) {
                mexicanos++;
            }

            if (producto.getPais().equalsIgnoreCase("Japonesa")) {
                japoneses++;
            }

            if (producto.getPais().equalsIgnoreCase("Fusión")) {
                fusiones++;
            }
        }

        if (fusiones >= mexicanos && fusiones >= japoneses) {
            return "Fusión";
        }

        if (mexicanos >= japoneses) {
            return "Mexicana";
        }

        return "Japonesa";
    }

    private ArrayList<Producto> obtenerProductosPorPais(Pedido pedido, String pais) {
    	//Obtiene productos de un origen especifico, o sea pais
        ArrayList<Producto> lista = new ArrayList<>();

        for (int i = 0; i < pedido.getProductos().size(); i++) {
            Producto producto = pedido.getProductos().get(i);

            if (producto.getPais().equalsIgnoreCase(pais)) {
                lista.add(producto);
            }
        }

        return lista;
    }

    private String crearNombreFusion(Producto mexicano, Producto japones) {
    	//Genera un nombre para una combinacion fusion
        String nombreMexicano = mexicano.getNombre().toLowerCase();
        String nombreJapones = japones.getNombre().toLowerCase();

        if ((nombreMexicano.contains("barbacoa") || nombreMexicano.contains("birria"))
                && nombreJapones.contains("ramen")) {
            return "Ramen de birria estilo japones";
        }

        if (nombreMexicano.contains("pastor")
                && (nombreJapones.contains("sushi") || nombreJapones.contains("roll"))) {
            return "Sushi de pastor";
        }

        if (nombreMexicano.contains("mole")
                && nombreJapones.contains("onigiri")) {
            return "Onigiri de mole";
        }

        if (nombreMexicano.contains("cochinita")
                && nombreJapones.contains("gyoza")) {
            return "Gyozas de cochinita";
        }

        return mexicano.getNombre() + " estilo " + japones.getNombre();
    }

    public String obtenerMejorCombo(Pedido pedido) {
    	//Busca el combo con mayor descuento y regresa si se encontro
    	//o no
        ArrayList<Producto> mexicanos = obtenerProductosPorPais(pedido, "Mexicana");
        ArrayList<Producto> japoneses = obtenerProductosPorPais(pedido, "Japonesa");

        Producto mejorMexicano = null;
        Producto mejorJapones = null;

        double mayorDescuento = 0;
        double mejorPrecioFinal = 0;

        for (int i = 0; i < mexicanos.size(); i++) {
            for (int j = 0; j < japoneses.size(); j++) {
                Producto mexicano = mexicanos.get(i);
                Producto japones = japoneses.get(j);

                if (mexicano.getTipo().equalsIgnoreCase("Comida")
                        && japones.getTipo().equalsIgnoreCase("Comida")) {

                    double precioNormal = mexicano.getPrecio() + japones.getPrecio();
                    double descuento = precioNormal * 0.10;
                    double precioFinal = precioNormal - descuento;

                    if (descuento > mayorDescuento) {
                        mayorDescuento = descuento;
                        mejorMexicano = mexicano;
                        mejorJapones = japones;
                        mejorPrecioFinal = precioFinal;
                    }
                }
            }
        }

        if (mejorMexicano == null || mejorJapones == null) {
            return "No hay combo recomendado todavía.";
        }

        return "Mejor combo recomendado:\n"
                + crearNombreFusion(mejorMexicano, mejorJapones) + "\n"
                + mejorMexicano.getNombre() + " + " + mejorJapones.getNombre() + "\n"
                + "Precio final: $" + mejorPrecioFinal + "\n"
                + "Descuento aplicado: $" + mayorDescuento;
    }

    public double calcularDescuentoMejorCombo(Pedido pedido) {
    	//Calcula el descuento del mejor combo y lo regresa
    	//en caso de no haber, regresa 0
        ArrayList<Producto> mexicanos = obtenerProductosPorPais(pedido, "Mexicana");
        ArrayList<Producto> japoneses = obtenerProductosPorPais(pedido, "Japonesa");

        double mayorDescuento = 0;

        for (int i = 0; i < mexicanos.size(); i++) {
            for (int j = 0; j < japoneses.size(); j++) {
                Producto mexicano = mexicanos.get(i);
                Producto japones = japoneses.get(j);

                if (mexicano.getTipo().equalsIgnoreCase("Comida")
                        && japones.getTipo().equalsIgnoreCase("Comida")) {

                    double precioNormal = mexicano.getPrecio() + japones.getPrecio();
                    double descuento = precioNormal * 0.10;

                    if (descuento > mayorDescuento) {
                        mayorDescuento = descuento;
                    }
                }
            }
        }

        return mayorDescuento;
    }
}