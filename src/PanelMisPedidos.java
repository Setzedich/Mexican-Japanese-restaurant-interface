import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class PanelMisPedidos extends JPanel {

    private RestauranteFacade facade;
    private JTextField campoBusqueda;
    private JTextArea areaPedidos;

    public PanelMisPedidos(RestauranteFacade facade) {
        this.facade = facade;

        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        crearTitulo();
        crearBusqueda();
        crearAreaPedidos();

        mostrarMensajeInicial();
    }

    private void crearTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(139, 95, 64));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel titulo = new JLabel("Mis pedidos");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Consulta el historial y estado de tus pedidos");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 248, 232));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);
    }

    private void crearBusqueda() {
        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBackground(new Color(253, 234, 210));

        JLabel etiqueta = new JLabel("Nombre o correo:");
        etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
        etiqueta.setForeground(new Color(80, 55, 37));

        campoBusqueda = new JTextField();

        JButton botonBuscar = crearBoton("Buscar");
        JButton botonLimpiar = crearBoton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelBotones.setBackground(new Color(253, 234, 210));
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonBuscar);

        panelBusqueda.add(etiqueta, BorderLayout.WEST);
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(panelBotones, BorderLayout.EAST);

        add(panelBusqueda, BorderLayout.SOUTH);

        botonBuscar.addActionListener(e -> buscarPedidos());
        botonLimpiar.addActionListener(e -> limpiar());
        campoBusqueda.addActionListener(e -> buscarPedidos());
    }

    private void crearAreaPedidos() {
        areaPedidos = new JTextArea();
        areaPedidos.setEditable(false);
        areaPedidos.setLineWrap(true);
        areaPedidos.setWrapStyleWord(true);
        areaPedidos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaPedidos.setBackground(new Color(255, 248, 232));
        areaPedidos.setForeground(new Color(80, 55, 37));
        areaPedidos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaPedidos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        add(scroll, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        return boton;
    }

    private void mostrarMensajeInicial() {
        areaPedidos.setText("");
        areaPedidos.append("Aquí puedes consultar tus pedidos anteriores.\n\n");
        areaPedidos.append("Escribe tu nombre o correo y presiona Buscar.\n\n");
        areaPedidos.append("Ejemplo:\n");
        areaPedidos.append("- Marlon\n");
        areaPedidos.append("- cliente@gmail.com\n");
    }

    private void buscarPedidos() {
        String busqueda = campoBusqueda.getText();

        if (busqueda.trim().equals("")) {
            areaPedidos.setText("Escribe un nombre o correo para buscar pedidos.");
            return;
        }

        String texto = facade.verHistorialCliente(busqueda);

        areaPedidos.setText("");
        areaPedidos.append(texto);
    }

    private void limpiar() {
        campoBusqueda.setText("");
        mostrarMensajeInicial();
    }
}