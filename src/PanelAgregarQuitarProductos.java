import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelAgregarQuitarProductos extends JPanel {

    private RestauranteFacade facade;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboPais;
    private JTextField txtIdEliminar;
    private JTextArea areaMenu;

    public PanelAgregarQuitarProductos(RestauranteFacade facade) {
        this.facade = facade;

        setBackground(new Color(253, 234, 210));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Agregar o quitar productos");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        titulo.setForeground(new Color(80, 55, 37));
        add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 8, 8));
        panelFormulario.setBackground(new Color(253, 234, 210));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        comboTipo = new JComboBox<String>();
        comboPais = new JComboBox<String>();
        txtIdEliminar = new JTextField();

        comboTipo.addItem("Comida");
        comboTipo.addItem("Bebida");
        comboTipo.addItem("Postre");

        comboPais.addItem("Mexicana");
        comboPais.addItem("Japonesa");
        comboPais.addItem("Fusión");

        JButton botonAgregar = new JButton("Agregar producto");
        JButton botonEliminar = new JButton("Quitar producto");
        JButton botonActualizar = new JButton("Actualizar lista");

        panelFormulario.add(crearEtiqueta("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(crearEtiqueta("Precio:"));
        panelFormulario.add(txtPrecio);

        panelFormulario.add(crearEtiqueta("Tipo:"));
        panelFormulario.add(comboTipo);

        panelFormulario.add(crearEtiqueta("País / estilo:"));
        panelFormulario.add(comboPais);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(botonAgregar);

        panelFormulario.add(crearEtiqueta("ID a quitar:"));
        panelFormulario.add(txtIdEliminar);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(botonEliminar);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(botonActualizar);

        add(panelFormulario, BorderLayout.WEST);

        areaMenu = new JTextArea();
        areaMenu.setEditable(false);
        areaMenu.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaMenu.setBackground(new Color(255, 248, 232));
        areaMenu.setForeground(new Color(80, 55, 37));

        JScrollPane scroll = new JScrollPane(areaMenu);
        add(scroll, BorderLayout.CENTER);

        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarMenu();
            }
        });

        cargarMenu();
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
        etiqueta.setForeground(new Color(80, 55, 37));
        return etiqueta;
    }

    private void agregarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String tipo = comboTipo.getSelectedItem().toString();
        String pais = comboPais.getSelectedItem().toString();

        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(this, "No puedes dejar el nombre vacío.");
            return;
        }

        if (precioTexto.equals("")) {
            JOptionPane.showMessageDialog(this, "No puedes dejar el precio vacío.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.");
                return;
            }

            String resultado = facade.agregarProductoMenu(nombre, precio, tipo, pais);
            JOptionPane.showMessageDialog(this, resultado);

            txtNombre.setText("");
            txtPrecio.setText("");

            cargarMenu();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser numérico. No escribas letras.");
        }
    }

    private void eliminarProducto() {
        String idTexto = txtIdEliminar.getText().trim();

        if (idTexto.equals("")) {
            JOptionPane.showMessageDialog(this, "Escribe el ID del producto que quieres quitar.");
            return;
        }

        try {
            int idProducto = Integer.parseInt(idTexto);

            if (idProducto <= 0) {
                JOptionPane.showMessageDialog(this, "El ID debe ser mayor a 0.");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que quieres eliminar el producto con ID " + idProducto + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            String resultado = facade.eliminarProductoMenu(idProducto);
            JOptionPane.showMessageDialog(this, resultado);

            txtIdEliminar.setText("");

            cargarMenu();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero. No escribas letras.");
        }
    }

    private void cargarMenu() {
        ArrayList<Producto> lista = facade.obtenerMenu();

        String texto = "";
        texto = texto + "ID   Producto                     Precio      Tipo       País\n";
        texto = texto + "---------------------------------------------------------------\n";

        for (int i = 0; i < lista.size(); i++) {
            Producto producto = lista.get(i);

            texto = texto + producto.getId() + "   ";
            texto = texto + producto.getNombre() + "   $";
            texto = texto + producto.getPrecio() + "   ";
            texto = texto + producto.getTipo() + "   ";
            texto = texto + producto.getPais() + "\n";
        }

        areaMenu.setText(texto);
    }
}