import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

public class VentanaCliente extends JFrame {

    private RestauranteFacade facade;
    private JPanel panelContenido;
    private String nombreCliente;
    private String correoCliente;

    public VentanaCliente(String nombre, String correo) {
    	this.nombreCliente = nombre;
        this.correoCliente = correo;
        facade = new RestauranteFacade();

        getContentPane().setBackground(new Color(255, 248, 232));
        setTitle("Clientes - Restaurante México-Japonés");
        setSize(748, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JPanel seleccion = new JPanel();
        seleccion.setBackground(new Color(253, 234, 210));
        seleccion.setBounds(10, 99, 190, 370);
        getContentPane().add(seleccion);
        seleccion.setLayout(null);

        panelContenido = new JPanel();
        panelContenido.setBackground(new Color(253, 234, 210));
        panelContenido.setBounds(209, 99, 518, 370);
        panelContenido.setLayout(new BorderLayout());
        getContentPane().add(panelContenido);

        JLabel lblNewLabel = new JLabel("Selecciona una opción");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNewLabel.setForeground(new Color(80, 55, 37));
        lblNewLabel.setBounds(10, 11, 164, 14);
        seleccion.add(lblNewLabel);

        JButton opc1 = crearBoton("Menú y ordenar");
        opc1.setBounds(13, 40, 164, 50);
        opc1.addActionListener(e -> mostrarMenuOrdenar());
        seleccion.add(opc1);

        JButton opc2 = crearBoton("Mi carrito");
        opc2.setBounds(13, 100, 164, 50);
        opc2.addActionListener(e -> mostrarCarritoCliente());
        seleccion.add(opc2);

        JButton opc3 = crearBoton("Mis pedidos");
        opc3.setBounds(13, 160, 164, 50);
        opc3.addActionListener(e -> mostrarMisPedidos());
        seleccion.add(opc3);

        JButton opc4 = crearBoton("Más opciones");
        opc4.setBounds(13, 220, 164, 50);
        opc4.addActionListener(e -> mostrarMasOpciones());
        seleccion.add(opc4);

        JButton opc5 = crearBoton("Salir");
        opc5.setBounds(13, 280, 164, 50);
        opc5.addActionListener(e -> {
            VentanaPrincipal ventanaP = new VentanaPrincipal();
            ventanaP.setVisible(true);
            dispose();
        });
        seleccion.add(opc5);

        JLabel titulo = new JLabel(new ImageIcon(VentanaCliente.class.getResource("/titulo.png")));
        titulo.setBounds(10, 11, 712, 77);
        getContentPane().add(titulo);

        mostrarMenuOrdenar();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setIcon(new ImageIcon(VentanaCliente.class.getResource("/boton.png")));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(245, 100, 118));
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        return boton;
    }

    private JButton crearBotonOpcion(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.BOLD, 14));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        return boton;
    }

    private void mostrarMenuOrdenar() {
        PanelVerMenuCompleto panel = new PanelVerMenuCompleto(facade);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarCarritoCliente() {
        PanelCarritoCliente panel = new PanelCarritoCliente(facade, nombreCliente, correoCliente);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarMisPedidos() {
        PanelMisPedidos panel = new PanelMisPedidos(facade);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarMasOpciones() {
        JPanel panelMas = new JPanel(new BorderLayout(10, 10));
        panelMas.setBackground(new Color(253, 234, 210));
        panelMas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(139, 95, 64));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel titulo = new JLabel("Más opciones");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Herramientas extra del restaurante");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 248, 232));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelOpciones.setBackground(new Color(253, 234, 210));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));
        
        
        JButton botonPromociones = crearBotonOpcion("Promociones");
        JButton botonRecomendaciones = crearBotonOpcion("Recomendaciones");
        JButton botonSoporte = crearBotonOpcion("Aiko Soporte");

        botonPromociones.addActionListener(e -> mostrarPromociones());
        botonRecomendaciones.addActionListener(e -> mostrarRecomendaciones());
        botonSoporte.addActionListener(e -> mostrarSoporteTecnico());

        panelOpciones.add(botonPromociones);
        panelOpciones.add(botonRecomendaciones);
        panelOpciones.add(botonSoporte);

        JTextArea descripcion = new JTextArea();
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setFont(new Font("Tahoma", Font.PLAIN, 13));
        descripcion.setBackground(new Color(255, 248, 232));
        descripcion.setForeground(new Color(80, 55, 37));
        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        descripcion.setText(
                "En esta sección puedes consultar promociones, recibir recomendaciones "
                        + "del chef o hablar con Aiko, el soporte inteligente del restaurante."
        );

        panelMas.add(panelTitulo, BorderLayout.NORTH);
        panelMas.add(panelOpciones, BorderLayout.CENTER);
        panelMas.add(descripcion, BorderLayout.SOUTH);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panelMas, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarPromociones() {
        JPanel panelPromociones = new JPanel(new BorderLayout(10, 10));
        panelPromociones.setBackground(new Color(253, 234, 210));
        panelPromociones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Promociones");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(139, 95, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel listaPromos = new JPanel(new GridLayout(4, 1, 10, 10));
        listaPromos.setBackground(new Color(253, 234, 210));

        JLabel promo1 = crearTextoPromo("Combo México-Japonés: comida + bebida + postre.");
        JLabel promo2 = crearTextoPromo("Menú del día: platillos seleccionados con mejor precio.");
        JLabel promo3 = crearTextoPromo("Opciones veganas disponibles dentro del menú.");
        JLabel promo4 = crearTextoPromo("Promoción especial: aplica el mejor combo al carrito.");

        listaPromos.add(promo1);
        listaPromos.add(promo2);
        listaPromos.add(promo3);
        listaPromos.add(promo4);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(253, 234, 210));

        JButton botonMenuDia = crearBotonOpcion("Ver menú del día");
        JButton botonVegano = crearBotonOpcion("Ver opciones veganas");
        JButton botonCombo = crearBotonOpcion("Aplicar mejor combo");

        botonMenuDia.addActionListener(e -> mostrarMenuOrdenar());
        botonVegano.addActionListener(e -> mostrarMenuOrdenar());

        botonCombo.addActionListener(e -> {
            String mensaje = facade.aplicarMejorCombo();
            JOptionPane.showMessageDialog(this, mensaje);
            mostrarCarritoCliente();
        });

        panelBotones.add(botonMenuDia);
        panelBotones.add(botonVegano);
        panelBotones.add(botonCombo);

        panelPromociones.add(titulo, BorderLayout.NORTH);
        panelPromociones.add(listaPromos, BorderLayout.CENTER);
        panelPromociones.add(panelBotones, BorderLayout.SOUTH);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panelPromociones, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private JLabel crearTextoPromo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setForeground(new Color(80, 55, 37));
        label.setOpaque(true);
        label.setBackground(new Color(255, 248, 232));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        return label;
    }

    private void mostrarRecomendaciones() {
        PanelRecomendaciones panel = new PanelRecomendaciones(facade);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarSoporteTecnico() {
        PanelSoporteTecnico panel = new PanelSoporteTecnico();

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}