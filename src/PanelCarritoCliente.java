import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelCarritoCliente extends JPanel {

    private RestauranteFacade facade;

    private JTextArea areaCarrito;
    private JTextField campoCliente;
    private JTextField campoCorreo;
    private JComboBox<String> comboPago;

    public PanelCarritoCliente(RestauranteFacade facade, String nombre, String correo) {
        this.facade = facade;

        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        crearTitulo();
        crearCarrito();
        crearDatosCompra();
        campoCliente.setText(nombre);
        campoCorreo.setText(correo);

        actualizarCarrito();
    }

    private void crearTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(139, 95, 64));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel titulo = new JLabel("Mi carrito");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Revisa tu pedido, agrega tus datos y confirma tu compra");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 248, 232));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);
    }

    private void crearCarrito() {
        areaCarrito = new JTextArea();
        areaCarrito.setEditable(false);
        areaCarrito.setLineWrap(true);
        areaCarrito.setWrapStyleWord(true);
        areaCarrito.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaCarrito.setBackground(new Color(255, 248, 232));
        areaCarrito.setForeground(new Color(80, 55, 37));
        areaCarrito.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaCarrito);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(139, 95, 64)),
                "Resumen del pedido"
        ));

        add(scroll, BorderLayout.CENTER);
    }

    private void crearDatosCompra() {
        JPanel panelAbajo = new JPanel(new BorderLayout(6, 6));
        panelAbajo.setBackground(new Color(253, 234, 210));

        JPanel panelDatos = new JPanel(new GridLayout(2, 4, 5, 5));
        panelDatos.setBackground(new Color(253, 234, 210));
        panelDatos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(139, 95, 64)),
                "Datos para finalizar"
        ));

        campoCliente = new JTextField();
        campoCorreo = new JTextField();

        comboPago = new JComboBox<String>();
        comboPago.addItem("Efectivo");
        comboPago.addItem("Tarjeta");
        comboPago.addItem("Transferencia");

        panelDatos.add(crearEtiqueta("Cliente:"));
        panelDatos.add(campoCliente);
        panelDatos.add(crearEtiqueta("Correo:"));
        panelDatos.add(campoCorreo);
        panelDatos.add(crearEtiqueta("Pago:"));
        panelDatos.add(comboPago);
        panelDatos.add(new JLabel(""));
        panelDatos.add(new JLabel(""));

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 6, 5));
        panelBotones.setBackground(new Color(253, 234, 210));

        JButton botonActualizar = crearBoton("Actualizar");
        JButton botonQuitar = crearBoton("Quitar");
        JButton botonCombo = crearBoton("Mejor combo");
        JButton botonLimpiar = crearBoton("Limpiar");
        JButton botonComprar = crearBotonComprar("Comprar");

        panelBotones.add(botonActualizar);
        panelBotones.add(botonQuitar);
        panelBotones.add(botonCombo);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonComprar);

        panelAbajo.add(panelDatos, BorderLayout.CENTER);
        panelAbajo.add(panelBotones, BorderLayout.SOUTH);

        add(panelAbajo, BorderLayout.SOUTH);

        botonActualizar.addActionListener(e -> actualizarCarrito());
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

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        return boton;
    }

    private JButton crearBotonComprar(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.BOLD, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64), 2));

        return boton;
    }

    private void actualizarCarrito() {
        areaCarrito.setText("");
        areaCarrito.append(facade.verCarrito());
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

        mostrarTicket(ticket);

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

        comboPago.setSelectedIndex(0);
        actualizarCarrito();
    }

    private void mostrarTicket(String ticket) {
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
}