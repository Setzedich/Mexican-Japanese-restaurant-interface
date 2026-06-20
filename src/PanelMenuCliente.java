import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PanelMenuCliente extends JPanel {

    private RestauranteFacade facade;
    private JTable tablaProductos;
    private DefaultTableModel modelo;

    private JComboBox<String> comboVista;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboPrecio;
    private JComboBox<String> comboPago;

    private JTextArea areaCarrito;
    private JTextField campoCliente;
    private JTextField campoCorreo;
    private JTextField campoBuscar;

    public PanelMenuCliente(RestauranteFacade facade, String vistaInicial) {
        this.facade = facade;

        setLayout(new BorderLayout(6, 6));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        crearFiltros(vistaInicial);
        crearTabla();
        crearPartePedido();

        cargarProductos();
        actualizarCarrito();
    }

    private void crearFiltros(String vistaInicial) {
        JPanel panelFiltros = new JPanel(new GridLayout(2, 1, 5, 5));
        panelFiltros.setBackground(new Color(253, 234, 210));

        JPanel filaUno = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        filaUno.setBackground(new Color(253, 234, 210));

        JLabel lblVista = crearEtiqueta("Menú:");
        comboVista = new JComboBox<String>();
        comboVista.addItem("Completo");
        comboVista.addItem("Por categoría");
        comboVista.addItem("Opciones veganas");
        comboVista.addItem("Menú del día");
        comboVista.setSelectedItem(vistaInicial);

        JLabel lblTipo = crearEtiqueta("Categoría:");
        comboTipo = new JComboBox<String>();
        comboTipo.addItem("Todos");
        comboTipo.addItem("Comida");
        comboTipo.addItem("Bebida");
        comboTipo.addItem("Postre");

        JLabel lblOrigen = crearEtiqueta("Origen:");
        comboOrigen = new JComboBox<String>();
        comboOrigen.addItem("Todos");
        comboOrigen.addItem("Mexicana");
        comboOrigen.addItem("Japonesa");
        comboOrigen.addItem("Fusión");

        filaUno.add(lblVista);
        filaUno.add(comboVista);
        filaUno.add(lblTipo);
        filaUno.add(comboTipo);
        filaUno.add(lblOrigen);
        filaUno.add(comboOrigen);

        JPanel filaDos = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        filaDos.setBackground(new Color(253, 234, 210));

        JLabel lblBuscar = crearEtiqueta("Buscar:");
        campoBuscar = new JTextField(12);

        JLabel lblPrecio = crearEtiqueta("Precio:");
        comboPrecio = new JComboBox<String>();
        comboPrecio.addItem("Todos");
        comboPrecio.addItem("Menos de $80");
        comboPrecio.addItem("$80 a $120");
        comboPrecio.addItem("Más de $120");

        JButton botonBuscar = crearBotonFiltro("Buscar");
        JButton botonLimpiar = crearBotonFiltro("Limpiar");

        filaDos.add(lblBuscar);
        filaDos.add(campoBuscar);
        filaDos.add(lblPrecio);
        filaDos.add(comboPrecio);
        filaDos.add(botonBuscar);
        filaDos.add(botonLimpiar);

        panelFiltros.add(filaUno);
        panelFiltros.add(filaDos);

        add(panelFiltros, BorderLayout.NORTH);

        comboVista.addActionListener(e -> cargarProductos());
        comboTipo.addActionListener(e -> cargarProductos());
        comboOrigen.addActionListener(e -> cargarProductos());
        comboPrecio.addActionListener(e -> cargarProductos());
        botonBuscar.addActionListener(e -> cargarProductos());
        campoBuscar.addActionListener(e -> cargarProductos());

        botonLimpiar.addActionListener(e -> {
            campoBuscar.setText("");
            comboTipo.setSelectedItem("Todos");
            comboOrigen.setSelectedItem("Todos");
            comboPrecio.setSelectedItem("Todos");
            comboVista.setSelectedItem("Completo");
            cargarProductos();
        });
    }

    private void crearTabla() {
        String[] columnas = {"ID", "Nombre", "Precio", "Tipo", "Origen"};

        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaProductos = new JTable(modelo);
        tablaProductos.setBackground(new Color(255, 248, 232));
        tablaProductos.setForeground(new Color(80, 55, 37));
        tablaProductos.setGridColor(new Color(139, 95, 64));
        tablaProductos.setRowHeight(22);

        JTableHeader cabecera = tablaProductos.getTableHeader();
        cabecera.setBackground(new Color(253, 234, 210));
        cabecera.setForeground(new Color(80, 55, 37));
        cabecera.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        add(scroll, BorderLayout.CENTER);
    }

    private void crearPartePedido() {
        JPanel panelPedido = new JPanel(new BorderLayout(6, 6));
        panelPedido.setBackground(new Color(253, 234, 210));

        JPanel panelDatos = new JPanel(new BorderLayout(5, 5));
        panelDatos.setBackground(new Color(253, 234, 210));
        panelDatos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(139, 95, 64)),
                "Datos del pedido"
        ));

        JPanel filaDatosUno = new JPanel(new GridLayout(1, 4, 5, 5));
        filaDatosUno.setBackground(new Color(253, 234, 210));

        campoCliente = new JTextField();
        campoCorreo = new JTextField();

        filaDatosUno.add(new JLabel("Cliente:"));
        filaDatosUno.add(campoCliente);
        filaDatosUno.add(new JLabel("Correo:"));
        filaDatosUno.add(campoCorreo);

        JPanel filaDatosDos = new JPanel(new GridLayout(1, 4, 5, 5));
        filaDatosDos.setBackground(new Color(253, 234, 210));

        comboPago = new JComboBox<String>();
        comboPago.addItem("Efectivo");
        comboPago.addItem("Tarjeta");
        comboPago.addItem("Transferencia");

        filaDatosDos.add(new JLabel("Pago:"));
        filaDatosDos.add(comboPago);
        filaDatosDos.add(new JLabel(""));
        filaDatosDos.add(new JLabel(""));

        panelDatos.add(filaDatosUno, BorderLayout.NORTH);
        panelDatos.add(filaDatosDos, BorderLayout.SOUTH);

        areaCarrito = new JTextArea(4, 20);
        areaCarrito.setEditable(false);
        areaCarrito.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaCarrito.setBackground(new Color(255, 248, 232));
        areaCarrito.setForeground(new Color(80, 55, 37));
        areaCarrito.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollCarrito = new JScrollPane(areaCarrito);
        scrollCarrito.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(139, 95, 64)),
                "Carrito"
        ));

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 6, 5));
        panelBotones.setBackground(new Color(253, 234, 210));

        JButton botonAgregar = crearBotonAccion("Agregar");
        JButton botonQuitar = crearBotonAccion("Quitar");
        JButton botonCombo = crearBotonAccion("Mejor combo");
        JButton botonLimpiar = crearBotonAccion("Limpiar");
        JButton botonComprar = crearBotonComprar("Comprar");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonQuitar);
        panelBotones.add(botonCombo);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonComprar);

        panelPedido.add(panelDatos, BorderLayout.NORTH);
        panelPedido.add(scrollCarrito, BorderLayout.CENTER);
        panelPedido.add(panelBotones, BorderLayout.SOUTH);

        add(panelPedido, BorderLayout.SOUTH);

        botonAgregar.addActionListener(e -> agregarProducto());
        botonQuitar.addActionListener(e -> quitarProducto());
        botonCombo.addActionListener(e -> aplicarCombo());
        botonLimpiar.addActionListener(e -> limpiarCarrito());
        botonComprar.addActionListener(e -> comprar());
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
        etiqueta.setForeground(new Color(80, 55, 37));
        return etiqueta;
    }

    private JButton crearBotonFiltro(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        return boton;
    }

    private JButton crearBotonAccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        return boton;
    }

    private JButton crearBotonComprar(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Tahoma", Font.BOLD, 12));
        boton.setForeground(new Color(255, 248, 232));
        boton.setBackground(new Color(139, 95, 64));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(80, 55, 37)));
        return boton;
    }

    private void cargarProductos() {
        ArrayList<Producto> productos = obtenerProductosSegunVista();

        modelo.setRowCount(0);

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);

            Object[] fila = {
                    producto.getId(),
                    producto.getNombre(),
                    "$" + producto.getPrecio(),
                    producto.getTipo(),
                    producto.getPais()
            };

            modelo.addRow(fila);
        }
    }

    private ArrayList<Producto> obtenerProductosSegunVista() {
        String textoBuscado = campoBuscar.getText();
        String vista = comboVista.getSelectedItem().toString();
        String tipo = comboTipo.getSelectedItem().toString();
        String origen = comboOrigen.getSelectedItem().toString();
        String precio = comboPrecio.getSelectedItem().toString();

        if (vista.equals("Por categoría")) {
            if (tipo.equals("Todos")) {
                tipo = "Comida";
                comboTipo.setSelectedItem("Comida");
            }
        }

        ArrayList<Producto> productos = facade.buscarMenuAvanzado(textoBuscado, tipo, origen, precio);

        if (vista.equals("Opciones veganas")) {
            productos = filtrarVeganos(productos);
        }

        if (vista.equals("Menú del día")) {
            productos = filtrarMenuDelDia(productos);
        }

        return productos;
    }

    private ArrayList<Producto> filtrarVeganos(ArrayList<Producto> productos) {
        ArrayList<Producto> lista = new ArrayList<Producto>();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            String nombre = producto.getNombre().toLowerCase();

            if (nombre.contains("agua")
                    || nombre.contains("matcha")
                    || nombre.contains("ramune")
                    || nombre.contains("onigiri")
                    || nombre.contains("yakimeshi")
                    || nombre.contains("mochi")
                    || nombre.contains("dorayaki")
                    || nombre.contains("churros")) {
                lista.add(producto);
            }
        }

        return lista;
    }

    private ArrayList<Producto> filtrarMenuDelDia(ArrayList<Producto> productos) {
        ArrayList<Producto> lista = new ArrayList<Producto>();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            String nombre = producto.getNombre().toLowerCase();

            if (nombre.contains("tacos al pastor")
                    || nombre.contains("sushi de pastor")
                    || nombre.contains("horchata matcha")
                    || nombre.contains("mochi")) {
                lista.add(producto);
            }
        }

        return lista;
    }

    private void agregarProducto() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.");
            return;
        }

        int idProducto = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
        String mensaje = facade.agregarProductoAlPedido(idProducto);

        JOptionPane.showMessageDialog(this, mensaje);
        actualizarCarrito();
    }

    private void quitarProducto() {
        String texto = JOptionPane.showInputDialog(this, "Número del producto en el carrito:");

        if (texto == null) {
            return;
        }

        try {
            int posicion = Integer.parseInt(texto);
            String mensaje = facade.quitarProductoDelPedido(posicion);

            JOptionPane.showMessageDialog(this, mensaje);
            actualizarCarrito();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debes escribir un número válido.");
        }
    }

    private void aplicarCombo() {
        String mensaje = facade.aplicarMejorCombo();

        JOptionPane.showMessageDialog(this, mensaje);
        actualizarCarrito();
    }

    private void limpiarCarrito() {
        String mensaje = facade.limpiarCarrito();

        JOptionPane.showMessageDialog(this, mensaje);
        actualizarCarrito();
    }

    private void comprar() {
        String cliente = campoCliente.getText();
        String correo = campoCorreo.getText();
        String metodoPago = comboPago.getSelectedItem().toString();

        if (cliente.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre del cliente.");
            return;
        }

        if (correo.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Escribe el correo del cliente.");
            return;
        }

        String ticket = facade.confirmarPedido(cliente, correo, metodoPago);

        if (ticket.equals("No puedes confirmar un pedido vacío.")
                || ticket.equals("El nombre del cliente no puede estar vacío.")
                || ticket.equals("El correo no puede estar vacío.")) {
            JOptionPane.showMessageDialog(this, ticket);
            return;
        }

        mostrarTicketBonito(ticket);

        boolean enviado = EnviadorCorreo.enviarCorreo(
                correo,
                "Ticket Restaurante México-Japonés",
                ticket
        );

        if (enviado) {
            JOptionPane.showMessageDialog(this, "Pedido realizado.\nEl ticket fue enviado a: " + correo);
        } else {
            JOptionPane.showMessageDialog(this, "Pedido realizado.\nNo se pudo enviar el correo.");
        }

        preguntarResena(cliente);

        campoCliente.setText("");
        campoCorreo.setText("");
        campoBuscar.setText("");
        comboPago.setSelectedIndex(0);
        comboVista.setSelectedItem("Completo");
        comboTipo.setSelectedItem("Todos");
        comboOrigen.setSelectedItem("Todos");
        comboPrecio.setSelectedItem("Todos");

        cargarProductos();
        actualizarCarrito();
    }

    private void mostrarTicketBonito(String ticket) {
        JTextArea areaTicket = new JTextArea();
        areaTicket.setEditable(false);
        areaTicket.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaTicket.setBackground(new Color(255, 248, 232));
        areaTicket.setForeground(new Color(80, 55, 37));
        areaTicket.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaTicket.setText(ticket);

        JScrollPane scroll = new JScrollPane(areaTicket);
        scroll.setPreferredSize(new java.awt.Dimension(430, 300));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        JOptionPane.showMessageDialog(this, scroll, "Ticket de compra", JOptionPane.INFORMATION_MESSAGE);
    }

    private void preguntarResena(String cliente) {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Quieres calificar tu pedido?",
                "Reseña",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        String textoCalificacion = JOptionPane.showInputDialog(this, "Calificación de 1 a 5:");

        if (textoCalificacion == null) {
            return;
        }

        try {
            int calificacion = Integer.parseInt(textoCalificacion);

            String comentario = JOptionPane.showInputDialog(this, "Comentario:");

            if (comentario == null) {
                comentario = "";
            }

            String mensaje = facade.guardarResena(cliente, calificacion, comentario);
            JOptionPane.showMessageDialog(this, mensaje);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La calificación debe ser un número.");
        }
    }

    private void actualizarCarrito() {
        areaCarrito.setText("");
        areaCarrito.append(facade.verCarrito());
    }
}