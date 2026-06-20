import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseDatos {

    private Connection conexion;

    public BaseDatos() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:restaurante.db");
            crearTablas();
            verificarColumnas();
            verificarColumnasPedidos();
            verificarColumnasPedidosExtra();
            crearTablasExtra();
            crearTablaAccionesAdmin();
            insertarMenuInicialSiEstaVacio();
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
        }
    }

    private void crearTablas() {
    	//Crea las tablas principales del sistema
        try {
            String tablaProductos = "CREATE TABLE IF NOT EXISTS productos ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nombre TEXT NOT NULL,"
                    + "precio REAL NOT NULL,"
                    + "tipo TEXT,"
                    + "pais TEXT"
                    + ")";

            String tablaPedidos = "CREATE TABLE IF NOT EXISTS pedidos ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "productos TEXT NOT NULL,"
                    + "total REAL NOT NULL,"
                    + "estado TEXT NOT NULL,"
                    + "fecha TEXT NOT NULL"
                    + ")";

            PreparedStatement consulta1 = conexion.prepareStatement(tablaProductos);
            PreparedStatement consulta2 = conexion.prepareStatement(tablaPedidos);

            consulta1.executeUpdate();
            consulta2.executeUpdate();

            consulta1.close();
            consulta2.close();

        } catch (SQLException e) {
            System.out.println("Error al crear tablas");
            e.printStackTrace();
        }
    }

    private void verificarColumnas() {
    	//Comentario:
    	//Verifica si las columnas "tipo" y "pais" existen en la tabla productos.
    	//Si la base de datos fue creada con una version anterior del sistema
    	//las agrega automaticamente y asigna valores por defecto
        try {
            String sql = "PRAGMA table_info(productos)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            boolean existeTipo = false;
            boolean existePais = false;

            while (resultado.next()) {
                String columna = resultado.getString("name");

                if (columna.equals("tipo")) {
                    existeTipo = true;
                }

                if (columna.equals("pais")) {
                    existePais = true;
                }
            }

            resultado.close();
            consulta.close();

            if (!existeTipo) {
                String sqlTipo = "ALTER TABLE productos ADD COLUMN tipo TEXT";
                PreparedStatement consultaTipo = conexion.prepareStatement(sqlTipo);
                consultaTipo.executeUpdate();
                consultaTipo.close();
            }

            if (!existePais) {
                String sqlPais = "ALTER TABLE productos ADD COLUMN pais TEXT";
                PreparedStatement consultaPais = conexion.prepareStatement(sqlPais);
                consultaPais.executeUpdate();
                consultaPais.close();
            }

            String actualizarTipo = "UPDATE productos SET tipo = 'Comida' WHERE tipo IS NULL";
            PreparedStatement consultaActualizarTipo = conexion.prepareStatement(actualizarTipo);
            consultaActualizarTipo.executeUpdate();
            consultaActualizarTipo.close();

            String actualizarPais = "UPDATE productos SET pais = 'Mexicana' WHERE pais IS NULL";
            PreparedStatement consultaActualizarPais = conexion.prepareStatement(actualizarPais);
            consultaActualizarPais.executeUpdate();
            consultaActualizarPais.close();

        } catch (SQLException e) {
            System.out.println("Error al verificar columnas");
            e.printStackTrace();
        }
    }

    public boolean agregarProducto(String nombre, double precio, String tipo, String pais) {
    	//Agrega un producto nuevo al menu
        try {
            String sql = "INSERT INTO productos(nombre, precio, tipo, pais) VALUES (?, ?, ?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, nombre);
            consulta.setDouble(2, precio);
            consulta.setString(3, tipo);
            consulta.setString(4, pais);

            consulta.executeUpdate();
            consulta.close();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar producto");
            e.printStackTrace();
        }

        return false;
    }

    

    public ArrayList<Producto> obtenerProductos() {
    	//Obtiene todos los productos registrados en la base de datos
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM productos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );

                lista.add(producto);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener productos");
            e.printStackTrace();
        }

        return lista;
    }

    public ArrayList<Producto> obtenerProductosPorTipoYPais(String tipo, String pais) {
        ArrayList<Producto> lista = new ArrayList<Producto>();

        try {
            String sql = "SELECT * FROM productos WHERE tipo = ? AND pais = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, tipo);
            consulta.setString(2, pais);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );

                lista.add(producto);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al filtrar productos");
            e.printStackTrace();
        }

        return lista;
    }

    public Producto buscarProductoPorId(int idBuscado) {
    	//Busca un producto usando su id y regresa el producto
        Producto producto = null;

        try {
            String sql = "SELECT * FROM productos WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, idBuscado);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar producto");
            e.printStackTrace();
        }

        return producto;
    }

    public void guardarPedido(Pedido pedido) {
    	//Guarda un pedido en la base de datos
        try {
            String sql = "INSERT INTO pedidos(productos, total, estado, fecha, cliente, metodo_pago, correo, descuento) "
                    + "VALUES (?, ?, ?, datetime('now'), ?, ?, ?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, pedido.obtenerProductosTexto());
            consulta.setDouble(2, pedido.getTotal());
            consulta.setString(3, "Pendiente");
            consulta.setString(4, pedido.getCliente());
            consulta.setString(5, pedido.getMetodoPago());
            consulta.setString(6, pedido.getCorreo());
            consulta.setDouble(7, pedido.getDescuento());

            consulta.executeUpdate();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar pedido");
            e.printStackTrace();
        }
    }

    public String obtenerPedidosTexto() {
    	//Obtiene todos los pedidos en formato texto
        String texto = "";

        try {
            String sql = "SELECT * FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + "ID: " + resultado.getInt("id") + "\n";
                texto = texto + "Cliente: " + resultado.getString("cliente") + "\n";
                texto = texto + "Productos: " + resultado.getString("productos") + "\n";
                texto = texto + "Total: $" + resultado.getDouble("total") + "\n";
                texto = texto + "Método de pago: " + resultado.getString("metodo_pago") + "\n";
                texto = texto + "Estado: " + resultado.getString("estado") + "\n";
                texto = texto + "Fecha: " + resultado.getString("fecha") + "\n";
                texto = texto + "-------------------------\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al leer pedidos");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "No hay pedidos registrados.";
        }

        return texto;
    }

    public void cambiarEstadoPedido(int idPedido, String nuevoEstado) {
    	//Cambia el estado de un pedido, para empleado
        try {
            String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, nuevoEstado);
            consulta.setInt(2, idPedido);

            consulta.executeUpdate();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado");
            e.printStackTrace();
        }
    }

    public double obtenerTotalVentas() {
    	//Calcula el total de ventas registradas desde siempre
        double total = 0;

        try {
            String sql = "SELECT SUM(total) FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                total = resultado.getDouble(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error en total ventas");
            e.printStackTrace();
        }

        return total;
    }

    public double obtenerPromedioPedidos() {
    	//Calcula el promedio de dinero por pedido
        double promedio = 0;

        try {
            String sql = "SELECT AVG(total) FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                promedio = resultado.getDouble(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error en promedio");
            e.printStackTrace();
        }

        return promedio;
    }

    public String obtenerProductosMasVendidos() {
    	//Comentario:
    	//Recorre todos los pedidos almacenados y cuenta cuantas veces aparece
    	//cada producto con un HashMap para generar un reporte de ventas
        String texto = "";
        HashMap<String, Integer> contador = new HashMap<>();

        try {
            String sql = "SELECT productos FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String listaProductos = resultado.getString("productos");
                String[] productos = listaProductos.split(", ");

                for (int i = 0; i < productos.length; i++) {
                    String nombre = productos[i];

                    if (contador.containsKey(nombre)) {
                        contador.put(nombre, contador.get(nombre) + 1);
                    } else {
                        contador.put(nombre, 1);
                    }
                }
            }

            for (String nombre : contador.keySet()) {
                texto = texto + nombre + " -> " + contador.get(nombre) + "\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error en más vendidos");
            e.printStackTrace();
        }

        return texto;
    }

    public String obtenerVentasPorDia() {
        String texto = "";

        try {
            String sql = "SELECT date(fecha), SUM(total) FROM pedidos GROUP BY date(fecha)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + resultado.getString(1) + " -> $" + resultado.getDouble(2) + "\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error en ventas por día");
            e.printStackTrace();
        }

        return texto;
    }
    
    public String obtenerDiaConMasVentas() {
        String texto = "No hay ventas registradas.";

        try {
            String sql = "SELECT date(fecha), SUM(total) AS totalDia "
                    + "FROM pedidos "
                    + "GROUP BY date(fecha) "
                    + "ORDER BY totalDia DESC "
                    + "LIMIT 1";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                texto = "Día con más ventas: " + resultado.getString(1)
                        + " con $" + resultado.getDouble(2);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener día con más ventas");
            e.printStackTrace();
        }

        return texto;
    }

    public int obtenerCantidadPedidos() {
    	//Cuenta cuantos pedidos existen
        int cantidad = 0;

        try {
            String sql = "SELECT COUNT(*) FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                cantidad = resultado.getInt(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al contar pedidos");
            e.printStackTrace();
        }

        return cantidad;
    }

    public String obtenerTotalPorCategoria() {
    	//Calcula las ventas agrupadas por categoria y regresa un String
        String texto = "";
        HashMap<String, Double> totales = new HashMap<>();

        try {
            String sql = "SELECT productos FROM pedidos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String listaProductos = resultado.getString("productos");
                String[] nombres = listaProductos.split(", ");

                for (int i = 0; i < nombres.length; i++) {
                    Producto producto = buscarProductoPorNombre(nombres[i]);

                    if (producto != null) {
                        String tipo = producto.getTipo();
                        double precio = producto.getPrecio();

                        if (totales.containsKey(tipo)) {
                            totales.put(tipo, totales.get(tipo) + precio);
                        } else {
                            totales.put(tipo, precio);
                        }
                    }
                }
            }

            for (String tipo : totales.keySet()) {
                texto = texto + tipo + ": $" + totales.get(tipo) + "\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener total por categoría");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "No hay ventas por categoría.";
        }

        return texto;
    }

    private Producto buscarProductoPorNombre(String nombreBuscado) {
        Producto producto = null;

        try {
            String sql = "SELECT * FROM productos WHERE nombre = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, nombreBuscado);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar producto por nombre");
            e.printStackTrace();
        }

        return producto;
    }
    
    public ArrayList<Producto> buscarProductosPorNombre(String nombreBuscado) {
    	//Busca productos que contengan un texto en su nombre
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM productos WHERE nombre LIKE ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, "%" + nombreBuscado + "%");

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );

                lista.add(producto);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre");
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarPrecio(int id, double nuevoPrecio) {
    	//Actualiza el precio de un producto
        try {
            String sql = "UPDATE productos SET precio = ? WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setDouble(1, nuevoPrecio);
            consulta.setInt(2, id);
            int totalCambios = consulta.executeUpdate();
            consulta.close();
            return totalCambios>0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar precio");
            e.printStackTrace();
            return false;
        }
    }
    
    private void insertarProductosIniciales() {
    	//Agrega productos iniciales al sistema. Este metodo es
    	//temporal, unicamente util en caso de que se pierda la
    	//base de datos. 
        try {
            String sqlContar = "SELECT COUNT(*) FROM productos";
            PreparedStatement consultaContar = conexion.prepareStatement(sqlContar);
            ResultSet resultado = consultaContar.executeQuery();

            int cantidad = 0;

            if (resultado.next()) {
                cantidad = resultado.getInt(1);
            }

            resultado.close();
            consultaContar.close();

            if (cantidad == 0) {
                agregarProducto("Tacos al pastor", 85, "Comida", "Mexicana");
                agregarProducto("Tacos de suadero", 90, "Comida", "Mexicana");
                agregarProducto("Ramen tradicional", 130, "Comida", "Japonesa");
                agregarProducto("Sushi roll", 120, "Comida", "Japonesa");
                agregarProducto("Ramen de birria", 145, "Comida", "Fusion");

                agregarProducto("Agua de horchata", 45, "Bebida", "Mexicana");
                agregarProducto("Agua de jamaica", 40, "Bebida", "Mexicana");
                agregarProducto("Te verde japones", 55, "Bebida", "Japonesa");
                agregarProducto("Ramune", 65, "Bebida", "Japonesa");
                agregarProducto("Matcha con horchata", 70, "Bebida", "Fusion");

                agregarProducto("Mochi de cajeta", 65, "Postre", "Fusion");
                agregarProducto("Flan napolitano", 60, "Postre", "Mexicana");
                agregarProducto("Dorayaki", 75, "Postre", "Japonesa");
                agregarProducto("Helado de matcha", 70, "Postre", "Japonesa");
                agregarProducto("Churros con matcha", 80, "Postre", "Fusion");

                System.out.println("Productos iniciales agregados");
            } else {
                System.out.println("La base ya tiene productos: " + cantidad);
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar productos iniciales");
            e.printStackTrace();
        }
    }
    
    private void verificarColumnasPedidos() {
    	//Verifica que existan las columnas cliente y metodo_pago
        try {
            String sql = "PRAGMA table_info(pedidos)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            boolean existeCliente = false;
            boolean existeMetodoPago = false;

            while (resultado.next()) {
                String columna = resultado.getString("name");

                if (columna.equals("cliente")) {
                    existeCliente = true;
                }

                if (columna.equals("metodo_pago")) {
                    existeMetodoPago = true;
                }
            }

            resultado.close();
            consulta.close();

            if (!existeCliente) {
                String sqlCliente = "ALTER TABLE pedidos ADD COLUMN cliente TEXT";
                PreparedStatement consultaCliente = conexion.prepareStatement(sqlCliente);
                consultaCliente.executeUpdate();
                consultaCliente.close();
            }

            if (!existeMetodoPago) {
                String sqlPago = "ALTER TABLE pedidos ADD COLUMN metodo_pago TEXT";
                PreparedStatement consultaPago = conexion.prepareStatement(sqlPago);
                consultaPago.executeUpdate();
                consultaPago.close();
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar columnas de pedidos");
            e.printStackTrace();
        }
    }
    
    private void insertarMenuInicialSiEstaVacio() {
    	//Inserta el menu inicial si la tabla esta vacia. Solo es
    	//util si se pierde la base de datos.
        int cantidad = contarProductos();

        if (cantidad > 0) {
            return;
        }

        agregarProducto("Tacos al pastor", 85, "Comida", "Mexicana");
        agregarProducto("Tacos de suadero", 90, "Comida", "Mexicana");
        agregarProducto("Enchiladas verdes", 95, "Comida", "Mexicana");
        agregarProducto("Mole con pollo", 125, "Comida", "Mexicana");
        agregarProducto("Pozole rojo", 115, "Comida", "Mexicana");

        agregarProducto("Ramen tradicional", 130, "Comida", "Japonesa");
        agregarProducto("Sushi roll", 120, "Comida", "Japonesa");
        agregarProducto("Onigiri", 65, "Comida", "Japonesa");
        agregarProducto("Gyozas", 90, "Comida", "Japonesa");
        agregarProducto("Yakimeshi", 100, "Comida", "Japonesa");

        agregarProducto("Ramen de birria", 145, "Comida", "Fusión");
        agregarProducto("Sushi de pastor", 135, "Comida", "Fusión");
        agregarProducto("Onigiri de mole", 80, "Comida", "Fusión");
        agregarProducto("Gyozas de cochinita", 110, "Comida", "Fusión");

        agregarProducto("Agua de horchata", 45, "Bebida", "Mexicana");
        agregarProducto("Agua de jamaica", 40, "Bebida", "Mexicana");
        agregarProducto("Matcha", 55, "Bebida", "Japonesa");
        agregarProducto("Ramune", 65, "Bebida", "Japonesa");
        agregarProducto("Horchata matcha", 70, "Bebida", "Fusión");

        agregarProducto("Flan", 55, "Postre", "Mexicana");
        agregarProducto("Churros", 60, "Postre", "Mexicana");
        agregarProducto("Mochi", 65, "Postre", "Japonesa");
        agregarProducto("Dorayaki", 70, "Postre", "Japonesa");
        agregarProducto("Mochi de cajeta", 75, "Postre", "Fusión");

        System.out.println("Menú inicial agregado correctamente.");
    }

    private int contarProductos() {
    	//Cuenta cuantos productos existen en la base de datos
        int cantidad = 0;

        try {
            String sql = "SELECT COUNT(*) FROM productos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                cantidad = resultado.getInt(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al contar productos");
            e.printStackTrace();
        }

        return cantidad;
    }
    
    private void crearTablasExtra() {
    	//Crea las tablas de favoritos y resenas
        try {
            String tablaFavoritos = "CREATE TABLE IF NOT EXISTS favoritos ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "cliente TEXT NOT NULL,"
                    + "id_producto INTEGER NOT NULL"
                    + ")";

            String tablaResenas = "CREATE TABLE IF NOT EXISTS resenas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "cliente TEXT NOT NULL,"
                    + "calificacion INTEGER NOT NULL,"
                    + "comentario TEXT,"
                    + "fecha TEXT NOT NULL"
                    + ")";

            PreparedStatement consulta1 = conexion.prepareStatement(tablaFavoritos);
            PreparedStatement consulta2 = conexion.prepareStatement(tablaResenas);

            consulta1.executeUpdate();
            consulta2.executeUpdate();

            consulta1.close();
            consulta2.close();

        } catch (SQLException e) {
            System.out.println("Error al crear tablas extra");
            e.printStackTrace();
        }
    }

    private void verificarColumnasPedidosExtra() {
    	//Verifica columnas extra en la tabla pedidos
        try {
            String sql = "PRAGMA table_info(pedidos)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            boolean existeCorreo = false;
            boolean existeDescuento = false;

            while (resultado.next()) {
                String columna = resultado.getString("name");

                if (columna.equals("correo")) {
                    existeCorreo = true;
                }

                if (columna.equals("descuento")) {
                    existeDescuento = true;
                }
            }

            resultado.close();
            consulta.close();

            if (!existeCorreo) {
                String sqlCorreo = "ALTER TABLE pedidos ADD COLUMN correo TEXT";
                PreparedStatement consultaCorreo = conexion.prepareStatement(sqlCorreo);
                consultaCorreo.executeUpdate();
                consultaCorreo.close();
            }

            if (!existeDescuento) {
                String sqlDescuento = "ALTER TABLE pedidos ADD COLUMN descuento REAL DEFAULT 0";
                PreparedStatement consultaDescuento = conexion.prepareStatement(sqlDescuento);
                consultaDescuento.executeUpdate();
                consultaDescuento.close();
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar columnas extra de pedidos");
            e.printStackTrace();
        }
    }
    
    public ArrayList<Producto> buscarProductosAvanzado(String textoBuscado, String tipo, String pais, String precioFiltro) {
    	//Comentario:
    	//Construye la consulta SQL de forma dinamica agregando unicamente
    	//los filtros seleccionados por el usuario (tipo, origen y rango de precio)
        ArrayList<Producto> lista = new ArrayList<Producto>();

        try {
            String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

            if (!tipo.equals("Todos")) {
                sql = sql + " AND tipo = ?";
            }

            if (!pais.equals("Todos")) {
                sql = sql + " AND pais = ?";
            }

            if (precioFiltro.equals("Menos de $80")) {
                sql = sql + " AND precio < 80";
            } else if (precioFiltro.equals("$80 a $120")) {
                sql = sql + " AND precio >= 80 AND precio <= 120";
            } else if (precioFiltro.equals("Más de $120")) {
                sql = sql + " AND precio > 120";
            }

            PreparedStatement consulta = conexion.prepareStatement(sql);

            int posicion = 1;
            consulta.setString(posicion, "%" + textoBuscado + "%");
            posicion++;

            if (!tipo.equals("Todos")) {
                consulta.setString(posicion, tipo);
                posicion++;
            }

            if (!pais.equals("Todos")) {
                consulta.setString(posicion, pais);
                posicion++;
            }

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getString("tipo"),
                        resultado.getString("pais")
                );

                lista.add(producto);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar productos avanzado");
            e.printStackTrace();
        }

        return lista;
    }

    public String agregarFavorito(String cliente, int idProducto) {
    	//Agrega un producto a favoritos
        try {
            String sqlBuscar = "SELECT * FROM favoritos WHERE cliente = ? AND id_producto = ?";
            PreparedStatement consultaBuscar = conexion.prepareStatement(sqlBuscar);
            consultaBuscar.setString(1, cliente);
            consultaBuscar.setInt(2, idProducto);

            ResultSet resultado = consultaBuscar.executeQuery();

            if (resultado.next()) {
                resultado.close();
                consultaBuscar.close();
                return "Ese producto ya está en favoritos.";
            }

            resultado.close();
            consultaBuscar.close();

            String sql = "INSERT INTO favoritos(cliente, id_producto) VALUES (?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, cliente);
            consulta.setInt(2, idProducto);

            consulta.executeUpdate();
            consulta.close();

            return "Producto agregado a favoritos.";

        } catch (SQLException e) {
            System.out.println("Error al agregar favorito");
            e.printStackTrace();
        }

        return "No se pudo agregar favorito.";
    }

    public String obtenerFavoritosTexto(String cliente) {
    	//Obtiene los favoritos de un cliente y regresa el texto con ese
        String texto = "";

        try {
            String sql = "SELECT productos.id, productos.nombre, productos.precio, productos.tipo, productos.pais "
                    + "FROM favoritos "
                    + "INNER JOIN productos ON favoritos.id_producto = productos.id "
                    + "WHERE favoritos.cliente = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, cliente);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + "ID: " + resultado.getInt("id") + "\n";
                texto = texto + "Platillo: " + resultado.getString("nombre") + "\n";
                texto = texto + "Precio: $" + resultado.getDouble("precio") + "\n";
                texto = texto + "Tipo: " + resultado.getString("tipo") + "\n";
                texto = texto + "Origen: " + resultado.getString("pais") + "\n";
                texto = texto + "-------------------------\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener favoritos");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "No hay favoritos guardados para ese cliente.";
        }

        return texto;
    }

    public String obtenerPedidosPorCliente(String cliente) {
    	//Busca pedidos usando nombre o correo del cliente. Ambos
    	//funcionan y busca las coincidencias
        String texto = "";

        try {
        	String sql = "SELECT * FROM pedidos WHERE cliente LIKE ? OR correo LIKE ? ORDER BY id DESC";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, "%" + cliente + "%");
            consulta.setString(2, "%" + cliente + "%");

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + "Pedido #" + resultado.getInt("id") + "\n";
                texto = texto + "Cliente: " + resultado.getString("cliente") + "\n";
                texto = texto + "Correo: " + resultado.getString("correo") + "\n";
                texto = texto + "Productos: " + resultado.getString("productos") + "\n";
                texto = texto + "Descuento: $" + resultado.getDouble("descuento") + "\n";
                texto = texto + "Total: $" + resultado.getDouble("total") + "\n";
                texto = texto + "Pago: " + resultado.getString("metodo_pago") + "\n";
                texto = texto + "Estado: " + resultado.getString("estado") + "\n";
                texto = texto + "Fecha: " + resultado.getString("fecha") + "\n";
                texto = texto + "-------------------------\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener pedidos por cliente");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "No se encontraron pedidos para ese cliente.";
        }

        return texto;
    }

    public String guardarResena(String cliente, int calificacion, String comentario) {
    	//Guarda una resena realizada por un cliente
        try {
            String sql = "INSERT INTO resenas(cliente, calificacion, comentario, fecha) "
                    + "VALUES (?, ?, ?, datetime('now'))";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, cliente);
            consulta.setInt(2, calificacion);
            consulta.setString(3, comentario);

            consulta.executeUpdate();
            consulta.close();

            return "Reseña guardada correctamente.";

        } catch (SQLException e) {
            System.out.println("Error al guardar reseña");
            e.printStackTrace();
        }

        return "No se pudo guardar la reseña.";
    }

    public String obtenerResenasTexto() {
    	//Obtiene todas las resenas registradas en un texto
    	// para imprimir depsues
        String texto = "";

        try {
            String sql = "SELECT * FROM resenas ORDER BY id DESC";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + "Cliente: " + resultado.getString("cliente") + "\n";
                texto = texto + "Calificación: " + resultado.getInt("calificacion") + " estrellas\n";
                texto = texto + "Comentario: " + resultado.getString("comentario") + "\n";
                texto = texto + "Fecha: " + resultado.getString("fecha") + "\n";
                texto = texto + "-------------------------\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener reseñas");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "No hay reseñas registradas.";
        }

        return texto;
    }
    
    public boolean eliminarProducto(int idProducto) {
    	//Elimina un producto del menu
        try {
            String sql = "DELETE FROM productos WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, idProducto);

            int cambios = consulta.executeUpdate();
            consulta.close();

            if (cambios > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto");
            e.printStackTrace();
        }

        return false;
    }

    public String obtenerDineroCajaTexto() {
    	//Genera un reporte del dinero en caja para el administrador
        String texto = "";
        double totalGeneral = obtenerTotalVentasCaja();
        int cantidadPedidos = obtenerCantidadPedidosCaja();
        double efectivo = obtenerTotalPorMetodoPago("Efectivo");
        double tarjeta = obtenerTotalPorMetodoPago("Tarjeta");
        double transferencia = obtenerTotalPorMetodoPago("Transferencia");

        texto = texto + "DINERO EN CAJA\n";
        texto = texto + "-------------------------\n";
        texto = texto + "Total vendido: $" + totalGeneral + "\n";
        texto = texto + "Pedidos cobrados: " + cantidadPedidos + "\n\n";
        texto = texto + "Por método de pago:\n";
        texto = texto + "Efectivo: $" + efectivo + "\n";
        texto = texto + "Tarjeta: $" + tarjeta + "\n";
        texto = texto + "Transferencia: $" + transferencia + "\n";
        texto = texto + "-------------------------\n";
        texto = texto + "Nota: no se cuentan pedidos cancelados.";

        return texto;
    }

    private double obtenerTotalVentasCaja() {
    	//Calcula el total de ventas validas para caja
        double total = 0;

        try {
            String sql = "SELECT SUM(total) FROM pedidos WHERE estado != 'Cancelado'";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                total = resultado.getDouble(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener dinero en caja");
            e.printStackTrace();
        }

        return total;
    }

    private int obtenerCantidadPedidosCaja() {
    	//Cuenta los pedidos validos para caja
        int cantidad = 0;

        try {
            String sql = "SELECT COUNT(*) FROM pedidos WHERE estado != 'Cancelado'";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                cantidad = resultado.getInt(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al contar pedidos de caja");
            e.printStackTrace();
        }

        return cantidad;
    }

    private double obtenerTotalPorMetodoPago(String metodoPago) {
    	//Obtiene el total vendido por metodo de pago, util para filtrar
        double total = 0;

        try {
            String sql = "SELECT SUM(total) FROM pedidos WHERE metodo_pago = ? AND estado != 'Cancelado'";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, metodoPago);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                total = resultado.getDouble(1);
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener total por método de pago");
            e.printStackTrace();
        }

        return total;
    }
    
    private void crearTablaAccionesAdmin() {
    	//Crea la tabla para guardar acciones del administrador
    	//Este permite ver los cambios anteriores
        try {
            String sql = "CREATE TABLE IF NOT EXISTS acciones_admin ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "accion TEXT NOT NULL,"
                    + "fecha TEXT NOT NULL"
                    + ")";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.executeUpdate();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al crear tabla de acciones admin");
            e.printStackTrace();
        }
    }

    public void guardarAccionAdmin(String accion) {
    	//Guarda una accion realizada por el administrador
        try {
            String sql = "INSERT INTO acciones_admin(accion, fecha) VALUES (?, datetime('now'))";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, accion);

            consulta.executeUpdate();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar acción admin");
            e.printStackTrace();
        }
    }

    public String obtenerAccionesAdminTexto() {
    	//Obtiene el historial de acciones del administrador en formato
    	//de texto
        String texto = "";

        try {
            String sql = "SELECT * FROM acciones_admin ORDER BY id DESC";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                texto = texto + "ID: " + resultado.getInt("id") + "\n";
                texto = texto + "Acción: " + resultado.getString("accion") + "\n";
                texto = texto + "Fecha: " + resultado.getString("fecha") + "\n";
                texto = texto + "-------------------------\n";
            }

            resultado.close();
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener acciones admin");
            e.printStackTrace();
        }

        if (texto.equals("")) {
            texto = "Todavía no hay acciones registradas.";
        }

        return texto;
    }
    
}