import java.util.ArrayList;

public class RestauranteFacade {
    private BaseDatos baseDatos;
    private Pedido pedidoActual;
    private ServicioInteligente servicioInteligente;

    public RestauranteFacade() {
    	//Comentario:
    	//Clase fachada que centraliza el acceso a los distintos subsistemas
    	//del restaurante (base de datos, pedidos y servicio inteligente),
    	//simplificando las operaciones utilizadas por la interfaz grafica.
        baseDatos = new BaseDatos();
        pedidoActual = new Pedido();
        servicioInteligente = new ServicioInteligente();
    }

    public ArrayList<Producto> obtenerMenu() {
        return baseDatos.obtenerProductos();
    }

    public ArrayList<Producto> obtenerMenuFiltrado(String tipo, String pais) {
    	//Obtiene los productos filtrados por tipo y pais
        return baseDatos.obtenerProductosPorTipoYPais(tipo, pais);
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }

    public String agregarProductoAlPedido(int idProducto) {
    	//Busca un producto por ID y lo agrega al pedido actual
        Producto producto = baseDatos.buscarProductoPorId(idProducto);

        if (producto == null) {
            return "No se encontró el producto.";
        }

        pedidoActual.agregarProducto(producto);
        return "Producto agregado: " + producto.getNombre();
    }

    public String verCarrito() {
        return pedidoActual.obtenerDetallesPedido();
    }

    public String obtenerSugerencia() {
    	//Genera sugerencias inteligentes segun los productos agregados
    	//Utiliza el servicio inteligente
    	//No siempre regresa combinaciones
        return servicioInteligente.generarCombinacionesInteligentes(pedidoActual);
    }

    public String verMejorCombo() {
    	//Obtiene el mejor combo de fusion disponible para el pedido.
    	//En conjunto con el servicio inteligente
        return servicioInteligente.obtenerMejorCombo(pedidoActual);
    }

    public String aplicarMejorCombo() {
    	//Comentario:
    	//Verifica si el pedido cumple las condiciones para un combo de fusion.
    	//Si existe una combinacion valida, calcula y aplica el descuento al total
        if (pedidoActual.getProductos().size() == 0) {
        	
            return "No hay productos en el carrito.";
        }

        double descuento = servicioInteligente.calcularDescuentoMejorCombo(pedidoActual);

        if (descuento == 0) {
            return "No se pudo aplicar combo. Agrega comida mexicana y japonesa.";
        }

        pedidoActual.aplicarDescuento(descuento);

        return "Combo aplicado correctamente.\n"
                + "Descuento: $" + descuento + "\n"
                + "Nuevo total: $" + pedidoActual.getTotal();
    }

    public String confirmarPedido() {
    	//Guarda el pedido actual en la base de datos y limpia el carrito
    	//Si no hay productos no puede confirmar el pedido
        if (pedidoActual.getProductos().size() == 0) {
            return "No puedes confirmar un pedido vacío.";
        }

        baseDatos.guardarPedido(pedidoActual);
        pedidoActual = new Pedido();

        return "Pedido confirmado correctamente.";
    }

    public String verHistorial() {
    	//Muestra el historial completo de pedidos registrados
    	//Util para base de datos
        return baseDatos.obtenerPedidosTexto();
    }

    public void cambiarEstadoPedido(int idPedido, String nuevoEstado) {
    	//Cambia el estado del pedido a En preparacion, Terminado, etc
        baseDatos.cambiarEstadoPedido(idPedido, nuevoEstado);
        baseDatos.guardarAccionAdmin("Admin cambió el estado del pedido ID " + idPedido + " a " + nuevoEstado);
    }

    public Reporte generarReporteVentas() {
    	//Comentario:
    	//Genera un reporte general del restaurante combinando estadisticas
    	//de ventas, pedidos, productos mas vendidos y categorías.
        String contenido = "";

        contenido = contenido + "Total de ventas: $" + baseDatos.obtenerTotalVentas() + "\n";
        contenido = contenido + "Cantidad de pedidos: " + baseDatos.obtenerCantidadPedidos() + "\n";
        contenido = contenido + "Ticket promedio real: $" + baseDatos.obtenerPromedioPedidos() + "\n\n";
        contenido = contenido + "Productos más vendidos:\n";
        contenido = contenido + baseDatos.obtenerProductosMasVendidos() + "\n";
        contenido = contenido + "Día con más ventas:\n";
        contenido = contenido + baseDatos.obtenerDiaConMasVentas() + "\n\n";
        contenido = contenido + "Total vendido por categoría:\n";
        contenido = contenido + baseDatos.obtenerTotalPorCategoria();

        return new Reporte("Reporte general del restaurante", contenido);
    }

    public String generarDatosGraficaSimple() {
        return baseDatos.obtenerVentasPorDia();
    }
    
    public String quitarProductoDelPedido(int posicion) {
    	//Elimina un producto del carrito usando su posicion.
        return pedidoActual.quitarProducto(posicion);
    }
    
    public ArrayList<Producto> buscarProductosPorNombre(String nombre) {
    	//Busca productos cuyo nombre coincida con el texto indicado
    	//No necesariamente tiene que ser exacto, con que este incluido
        return baseDatos.buscarProductosPorNombre(nombre);
    }
    
    public String actualizarPrecioMenu(int id, double precio) {
    	//Actualiza el precio de un producto del menu, recibe el
    	//id del producto y la cantidad
        boolean exito = baseDatos.actualizarPrecio(id, precio);

        if (exito) {
            baseDatos.guardarAccionAdmin("Admin cambió el precio del producto ID " + id + " a $" + precio);
            return "Precio actualizado con éxito para el producto ID: " + id;
        } else {
            return "Error: No se encontró ningún producto con el ID: " + id;
        }
    }
    
    public String confirmarPedido(String cliente, String metodoPago) {
    	//Confirma un pedido registrando cliente y metodo de pago
        if (pedidoActual.getProductos().size() == 0) {
            return "No puedes confirmar un pedido vacío.";
        }

        if (cliente == null || cliente.trim().equals("")) {
            return "El nombre del cliente no puede estar vacío.";
        }

        pedidoActual.setCliente(cliente);
        pedidoActual.setMetodoPago(metodoPago);

        String ticket = pedidoActual.generarTicket();

        baseDatos.guardarPedido(pedidoActual);
        pedidoActual = new Pedido();

        return ticket;
    }

    public String limpiarCarrito() {
    	//Elimina todos los productos del carrito actual
        pedidoActual.limpiarPedido();
        return "Carrito limpiado correctamente.";
    }

    public String generarTicketActual(String cliente, String metodoPago) {
    	//Genera una vista previa del ticket sin guardar el pedido
    	//regresa unicamente el texto
        if (pedidoActual.getProductos().size() == 0) {
            return "No hay productos en el carrito.";
        }

        pedidoActual.setCliente(cliente);
        pedidoActual.setMetodoPago(metodoPago);

        return pedidoActual.generarTicket();
    }
    
    public ArrayList<Producto> buscarMenuAvanzado(String texto, String tipo, String pais, String precioFiltro) {
    	//Realiza una busqueda avanzada del menu aplicando filtros
    	//por nombre, tipo de producto, origen y rango de precios.
        return baseDatos.buscarProductosAvanzado(texto, tipo, pais, precioFiltro);
    }

    public String agregarFavorito(String cliente, int idProducto) {
    	//Guarda un producto en la lista de favoritos de un cliente
        if (cliente == null || cliente.trim().equals("")) {
            return "Escribe el nombre del cliente para guardar favoritos.";
        }

        Producto producto = baseDatos.buscarProductoPorId(idProducto);

        if (producto == null) {
            return "No se encontró el producto.";
        }

        return baseDatos.agregarFavorito(cliente, idProducto);
    }

    public String verFavoritos(String cliente) {

    	//Muestra los productos favoritos de un cliente
        if (cliente == null || cliente.trim().equals("")) {
            return "Escribe el nombre del cliente para ver sus favoritos.";
        }

        return baseDatos.obtenerFavoritosTexto(cliente);
    }

    public String verHistorialCliente(String cliente) {
    	//Muestra el historial de pedidos de un cliente
    	//Se puede ver ingresando su nombre
        if (cliente == null || cliente.trim().equals("")) {
            return "Escribe el nombre del cliente para buscar su historial.";
        }

        return baseDatos.obtenerPedidosPorCliente(cliente);
    }

    public String guardarResena(String cliente, int calificacion, String comentario) {
    	//Guarda una resena realizada por un cliente
    	//Incluye nombre, calificacion y comentarios
        if (cliente == null || cliente.trim().equals("")) {
            return "Escribe el nombre del cliente.";
        }

        if (calificacion < 1 || calificacion > 5) {
            return "La calificación debe estar entre 1 y 5.";
        }

        return baseDatos.guardarResena(cliente, calificacion, comentario);
    }

    public String verResenas() {
    	//Muestra todas las resenas registradas
        return baseDatos.obtenerResenasTexto();
    }

    public String confirmarPedido(String cliente, String correo, String metodoPago) {
    	//Confirma un pedido registrando cliente, correo y metodo de pago
        if (pedidoActual.getProductos().size() == 0) {
            return "No puedes confirmar un pedido vacío.";
        }

        if (cliente == null || cliente.trim().equals("")) {
            return "El nombre del cliente no puede estar vacío.";
        }

        if (correo == null || correo.trim().equals("")) {
            return "El correo no puede estar vacío.";
        }

        pedidoActual.setCliente(cliente);
        pedidoActual.setCorreo(correo);
        pedidoActual.setMetodoPago(metodoPago);

        String ticket = pedidoActual.generarTicket();

        baseDatos.guardarPedido(pedidoActual);
        pedidoActual = new Pedido();

        return ticket;
    }
    
    public String agregarProductoMenu(String nombre, double precio, String tipo, String pais) {
    	//Agrega un nuevo producto al menu del restaurante
    	//Valida que los campos esten llenos
        if (nombre == null || nombre.trim().equals("")) {
            return "El nombre del producto no puede estar vacío.";
        }

        if (precio <= 0) {
            return "El precio debe ser mayor a 0.";
        }

        baseDatos.agregarProducto(nombre, precio, tipo, pais);
        baseDatos.guardarAccionAdmin("Admin agregó producto: " + nombre);

        return "Producto agregado correctamente.";
    }

    public String eliminarProductoMenu(int idProducto) {
    	//Elimina un producto del menu con el ID
        Producto producto = baseDatos.buscarProductoPorId(idProducto);

        if (producto == null) {
            return "No existe un producto con ese ID.";
        }

        boolean exito = baseDatos.eliminarProducto(idProducto);

        if (exito) {
            baseDatos.guardarAccionAdmin("Admin eliminó producto: " + producto.getNombre());
            return "Producto eliminado correctamente: " + producto.getNombre();
        }

        return "No se pudo eliminar el producto.";
    }

    public String verDineroCaja() {
    	//Muestra el resumen del dinero acumulado en caja
    	//En conjunto con base de datos, recibe un String
        return baseDatos.obtenerDineroCajaTexto();
    }
    
    public void registrarAccionAdmin(String accion) {
    	//Registra una accion realizada por el administrador
    	//Visible unicamente para Admin
        baseDatos.guardarAccionAdmin(accion);
    }

    public String verActividadAdmin() {
    	//Muestra el historial de acciones del administrador
    	//Unicamente visible para Admin
        return baseDatos.obtenerAccionesAdminTexto();
    }
    
    
}