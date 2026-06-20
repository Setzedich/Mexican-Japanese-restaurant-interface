import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PanelVerMenuCompleto extends JPanel {

    private RestauranteFacade facade;
    private JTable tablaProductos;
    private DefaultTableModel modelo;

    private JTextField campoBuscar;
    private JComboBox<String> comboVista;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboPrecio;

    public PanelVerMenuCompleto(RestauranteFacade facade) {
        this.facade = facade;

        setLayout(new BorderLayout(6, 6));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        crearFiltros();
        crearTabla();
        crearBotones();

        cargarProductos();
    }

    private void crearFiltros() {
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

        JButton botonBuscar = crearBoton("Buscar");
        JButton botonLimpiar = crearBoton("Limpiar");

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
        campoBuscar.addActionListener(e -> cargarProductos());
        botonBuscar.addActionListener(e -> cargarProductos());

        botonLimpiar.addActionListener(e -> limpiarFiltros());
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
        tablaProductos.setRowHeight(23);

        JTableHeader cabecera = tablaProductos.getTableHeader();
        cabecera.setBackground(new Color(253, 234, 210));
        cabecera.setForeground(new Color(80, 55, 37));
        cabecera.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        add(scroll, BorderLayout.CENTER);
    }

    private void crearBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 8, 5));
        panelBotones.setBackground(new Color(253, 234, 210));

        JButton botonAgregar = crearBotonImportante("Agregar al carrito");

        panelBotones.add(botonAgregar);

        add(panelBotones, BorderLayout.SOUTH);

        botonAgregar.addActionListener(e -> agregarAlCarrito());
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

    private JButton crearBotonImportante(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.BOLD, 13));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64), 2));

        return boton;
    }

    private void cargarProductos() {
        String texto = campoBuscar.getText();
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

        ArrayList<Producto> productos = facade.buscarMenuAvanzado(texto, tipo, origen, precio);

        if (vista.equals("Opciones veganas")) {
            productos = filtrarVeganos(productos);
        }

        if (vista.equals("Menú del día")) {
            productos = filtrarMenuDelDia(productos);
        }

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

    private void agregarAlCarrito() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto del menú.");
            return;
        }

        int idProducto = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
        String nombre = tablaProductos.getValueAt(fila, 1).toString();

        String mensaje = facade.agregarProductoAlPedido(idProducto);

        JOptionPane.showMessageDialog(this, nombre + "\n\n" + mensaje);
    }


    private void limpiarFiltros() {
        campoBuscar.setText("");
        comboVista.setSelectedItem("Completo");
        comboTipo.setSelectedItem("Todos");
        comboOrigen.setSelectedItem("Todos");
        comboPrecio.setSelectedItem("Todos");

        cargarProductos();
    }
}