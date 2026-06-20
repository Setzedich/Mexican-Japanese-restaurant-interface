import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PanelHacerPedido extends JPanel {
    private RestauranteFacade facade;
    private JTable tablaProductos;
    private JTextArea areaCarrito;
    private DefaultTableModel modelo;
    private JTextField campoCliente;
    private JComboBox<String> comboPago;

    public PanelHacerPedido(RestauranteFacade facade) {
        this.facade = facade;

        setBackground(new Color(253, 234, 210));
        setLayout(null);

        JLabel titulo = new JLabel("Hacer pedido");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        titulo.setForeground(new Color(80, 55, 37));
        titulo.setBounds(10, 5, 200, 25);
        add(titulo);

        crearDatosCliente();
        crearTablaProductos();
        crearAreaCarrito();
        crearBotones();

        cargarProductos();
        actualizarCarrito();
    }

    private void crearDatosCliente() {
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(10, 35, 60, 25);
        add(lblCliente);

        campoCliente = new JTextField();
        campoCliente.setBounds(70, 35, 180, 25);
        add(campoCliente);

        JLabel lblPago = new JLabel("Pago:");
        lblPago.setBounds(270, 35, 45, 25);
        add(lblPago);

        comboPago = new JComboBox<String>();
        comboPago.addItem("Efectivo");
        comboPago.addItem("Tarjeta");
        comboPago.addItem("Transferencia");
        comboPago.setBounds(315, 35, 150, 25);
        add(comboPago);
    }

    private void crearTablaProductos() {
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
        tablaProductos.setRowHeight(23);

        JTableHeader cabecera = tablaProductos.getTableHeader();
        cabecera.setBackground(new Color(253, 234, 210));
        cabecera.setForeground(new Color(80, 55, 37));
        cabecera.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBounds(10, 70, 498, 115);
        add(scroll);
    }

    private void crearAreaCarrito() {
        areaCarrito = new JTextArea();
        areaCarrito.setEditable(false);
        areaCarrito.setBackground(new Color(255, 248, 232));
        areaCarrito.setForeground(new Color(80, 55, 37));
        areaCarrito.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JScrollPane scrollCarrito = new JScrollPane(areaCarrito);
        scrollCarrito.setBounds(10, 195, 498, 90);
        add(scrollCarrito);
    }

    private void crearBotones() {
        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBounds(10, 300, 90, 30);
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                agregarProductoSeleccionado();
            }
        });
        add(botonAgregar);

        JButton botonQuitar = new JButton("Quitar");
        botonQuitar.setBounds(105, 300, 80, 30);
        botonQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                quitarProducto();
            }
        });
        add(botonQuitar);

        JButton botonCombo = new JButton("Mejor combo");
        botonCombo.setBounds(190, 300, 110, 30);
        botonCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                aplicarCombo();
            }
        });
        add(botonCombo);

        JButton botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBounds(305, 300, 90, 30);
        botonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limpiarCarrito();
            }
        });
        add(botonLimpiar);

        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setBounds(400, 300, 105, 30);
        botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                confirmarPedido();
            }
        });
        add(botonConfirmar);
    }

    private void cargarProductos() {
        ArrayList<Producto> productos = facade.obtenerMenu();

        modelo.setRowCount(0);

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);

            Object[] fila = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getTipo(),
                    producto.getPais()
            };

            modelo.addRow(fila);
        }
    }

    private void agregarProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto del menú.");
            return;
        }

        int idProducto = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());

        String mensaje = facade.agregarProductoAlPedido(idProducto);

        JOptionPane.showMessageDialog(this, mensaje);

        actualizarCarrito();
    }

    private void quitarProducto() {
        String posicionTexto = JOptionPane.showInputDialog(this, "Escribe el número del producto en el carrito:");

        if (posicionTexto == null) {
            return;
        }

        try {
            int posicion = Integer.parseInt(posicionTexto);

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

    private void confirmarPedido() {
        String cliente = campoCliente.getText();
        String metodoPago = comboPago.getSelectedItem().toString();

        if (cliente.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre del cliente.");
            return;
        }

        String ticket = facade.confirmarPedido(cliente, metodoPago);

        JOptionPane.showMessageDialog(this, ticket);

        campoCliente.setText("");
        actualizarCarrito();
    }

    private void actualizarCarrito() {
        areaCarrito.setText("");

        areaCarrito.append("CARRITO:\n\n");
        areaCarrito.append(facade.verCarrito());
    }
}