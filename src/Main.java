import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
      
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
        
        RestauranteFacade facade = new RestauranteFacade();
        int rol = 0;
        int opcion = 0;

        System.out.println("=== RESTAURANTE MEXICO-JAPONES ===");
        System.out.println("Selecciona tu rol:");
        System.out.println("1. Cliente");
        System.out.println("2. Empleado");
        System.out.println("3. Administrador");
        System.out.print("Opcion: ");
        rol = sc.nextInt();

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver menu completo");
            System.out.println("2. Ver menu por categoria");

            if (rol == 1 || rol == 3) {
                System.out.println("3. Hacer pedido");
            }

            if (rol == 2 || rol == 3) {
                System.out.println("4. Ver pedidos");
                System.out.println("5. Cambiar estado de pedido");
            }

            if (rol == 3) {
                System.out.println("6. Ver reportes");
                System.out.println("7. Cambiar precio de producto");
            }

            System.out.println("8. Salir");
            System.out.print("Opcion: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    mostrarMenuCompleto(facade);
                    break;

                case 2:
                    mostrarMenuPorCategoria(sc, facade);
                    break;

                case 3:
                    if (rol == 2) {
                        System.out.println("No tienes permiso.");
                        break;
                    }

                    hacerPedido(sc, facade);
                    break;

                case 4:
                    if (rol == 1) {
                        System.out.println("No tienes permiso.");
                        break;
                    }

                    System.out.println("\n--- HISTORIAL DE PEDIDOS ---");
                    System.out.println(facade.verHistorial());
                    break;

                case 5:
                    if (rol == 1) {
                        System.out.println("No tienes permiso.");
                        break;
                    }

                    System.out.print("ID del pedido: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nuevo estado: ");
                    String estado = sc.nextLine();

                    facade.cambiarEstadoPedido(id, estado);
                    System.out.println("Estado actualizado.");
                    break;

                case 6:
                    if (rol != 3) {
                        System.out.println("No tienes permiso.");
                        break;
                    }

                    Reporte reporte = facade.generarReporteVentas();

                    System.out.println("\n--- REPORTES ---");
                    System.out.println(reporte.obtenerTextoReporte());

                    System.out.println("\n--- VENTAS POR DIA ---");
                    System.out.println(facade.generarDatosGraficaSimple());
                    break;

                case 7:
                    if (rol != 3) {
                        System.out.println("No tienes permiso.");
                        break;
                    }

                    System.out.print("ID del producto: ");
                    int idProducto = sc.nextInt();

                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = sc.nextDouble();

                    facade.actualizarPrecioMenu(idProducto, nuevoPrecio);
                    System.out.println("Precio actualizado.");
                    break;

                case 8:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 8);

        sc.close();
    }

    public static void mostrarMenuCompleto(RestauranteFacade facade) {
        ArrayList<Producto> productos = facade.obtenerMenu();

        System.out.println("\n========== MENU COMPLETO ==========");

        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).obtenerTexto());
        }
    }

    public static void mostrarMenuPorCategoria(Scanner sc, RestauranteFacade facade) {
        int opcionTipo = 0;
        int opcionPais = 0;

        String tipo = "";
        String pais = "";

        System.out.println("\n--- SELECCIONA CATEGORIA ---");
        System.out.println("1. Comida");
        System.out.println("2. Bebida");
        System.out.println("3. Postre");
        System.out.print("Opcion: ");
        opcionTipo = sc.nextInt();

        switch (opcionTipo) {
            case 1:
                tipo = "Comida";
                break;
            case 2:
                tipo = "Bebida";
                break;
            case 3:
                tipo = "Postre";
                break;
            default:
                System.out.println("Categoria invalida.");
                return;
        }

        System.out.println("\n--- SELECCIONA PAIS / ESTILO ---");
        System.out.println("1. Mexicana");
        System.out.println("2. Japonesa");
        System.out.println("3. Fusión");
        System.out.print("Opcion: ");
        opcionPais = sc.nextInt();

        switch (opcionPais) {
            case 1:
                pais = "Mexicana";
                break;
            case 2:
                pais = "Japonesa";
                break;
            case 3:
                pais = "Fusión";
                break;
            default:
                System.out.println("Pais invalido.");
                return;
        }

        ArrayList<Producto> productos = facade.obtenerMenuFiltrado(tipo, pais);

        System.out.println("\n========== " + tipo.toUpperCase() + " / " + pais.toUpperCase() + " ==========");

        if (productos.size() == 0) {
            System.out.println("No hay productos en esta categoria.");
        } else {
            for (int i = 0; i < productos.size(); i++) {
                System.out.println(productos.get(i).obtenerTexto());
            }
        }
    }

    public static void hacerPedido(Scanner sc, RestauranteFacade facade) {
        int eleccion = 0;

        do {
        	System.out.println("1. Ver menu completo");
        	System.out.println("2. Ver menu por categoria");
        	System.out.println("3. Ver carrito");
        	System.out.println("4. Ver sugerencias inteligentes");
        	System.out.println("5. Agregar producto por ID");
        	System.out.println("6. Quitar producto del carrito");
        	System.out.println("7. Buscar producto por nombre");
        	System.out.println("8. Confirmar pedido");
        	System.out.println("0. Cancelar / regresar");
            System.out.print("Opcion: ");

            eleccion = sc.nextInt();

            switch (eleccion) {

                case 1:
                	mostrarMenuAutomatico(facade);
                    break;

                case 2:
                    mostrarMenuPorCategoria(sc, facade);
                    break;

                case 3:
                    System.out.println("\n--- CARRITO ---");
                    System.out.println(facade.verCarrito());
                    break;

                case 4:
                    System.out.println("\n--- SUGERENCIAS ---");
                    System.out.println(facade.obtenerSugerencia());
                    System.out.println(facade.verMejorCombo());
                    break;

                case 5:
                    System.out.print("Ingresa ID del producto: ");
                    int idProducto = sc.nextInt();

                    String respuesta = facade.agregarProductoAlPedido(idProducto);
                    System.out.println(respuesta);
                    break;

                case 6:
                    System.out.println("\n--- CARRITO ---");
                    System.out.println(facade.verCarrito());

                    System.out.print("Número del producto a quitar: ");
                    int posicion = sc.nextInt();

                    System.out.println(facade.quitarProductoDelPedido(posicion));
                    break;

                case 7:
                    sc.nextLine();

                    System.out.print("Nombre a buscar: ");
                    String nombre = sc.nextLine();

                    ArrayList<Producto> encontrados = facade.buscarProductosPorNombre(nombre);

                    if (encontrados.size() == 0) {
                        System.out.println("No se encontraron productos.");
                    } else {
                        System.out.println("\n--- RESULTADOS ---");

                        for (int i = 0; i < encontrados.size(); i++) {
                            System.out.println(encontrados.get(i).obtenerTexto());
                        }
                    }
                    break;

                case 8:
                    confirmarPedido(sc, facade);
                    return;

                case 0:
                    System.out.println("Regresando al menu principal...");
                    break;            }

        } while (eleccion != 0);
    }
    
    public static void mostrarMenuAutomatico(RestauranteFacade facade) {
        System.out.println("\n========== MENU AUTOMATICO POR SECCIONES ==========");

        mostrarSeccion(facade, "Comida", "Mexicana");
        mostrarSeccion(facade, "Comida", "Japonesa");
        mostrarSeccion(facade, "Comida", "Fusión");

        mostrarSeccion(facade, "Bebida", "Mexicana");
        mostrarSeccion(facade, "Bebida", "Japonesa");
        mostrarSeccion(facade, "Bebida", "Fusión");

        mostrarSeccion(facade, "Postre", "Mexicana");
        mostrarSeccion(facade, "Postre", "Japonesa");
        mostrarSeccion(facade, "Postre", "Fusión");
    }

    public static void mostrarSeccion(RestauranteFacade facade, String tipo, String pais) {
        ArrayList<Producto> productos = facade.obtenerMenuFiltrado(tipo, pais);

        if (productos.size() > 0) {
            System.out.println("\n--- " + tipo.toUpperCase() + " " + pais.toUpperCase() + " ---");

            for (int i = 0; i < productos.size(); i++) {
                System.out.println(productos.get(i).obtenerTexto());
            }
        }
    }

    public static void confirmarPedido(Scanner sc, RestauranteFacade facade) {
        if (facade.getPedidoActual().getProductos().size() == 0) {
            System.out.println("No puedes confirmar un pedido vacio.");
            return;
        }

        System.out.println("\n--- CARRITO ACTUAL ---");
        System.out.println(facade.verCarrito());

        System.out.println("\n--- MEJOR COMBO ---");
        System.out.println(facade.verMejorCombo());

        System.out.println("\nDeseas aplicar el mejor combo?");
        System.out.println("1. Si");
        System.out.println("2. No");
        System.out.print("Opcion: ");
        int aplicar = sc.nextInt();

        if (aplicar == 1) {
            System.out.println(facade.aplicarMejorCombo());
        }

        System.out.println("\n--- RESUMEN FINAL ---");
        System.out.println(facade.verCarrito());

        String respuesta = facade.confirmarPedido();
        System.out.println(respuesta);
    }
}