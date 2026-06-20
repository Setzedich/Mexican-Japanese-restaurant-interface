import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaAdmin extends JFrame {

    private RestauranteFacade facade;
    private JPanel menu_1;

    public VentanaAdmin() {
        facade = new RestauranteFacade();

        getContentPane().setBackground(new Color(255, 248, 232));
        setTitle("Admin - Restaurante México-Japonés");
        setSize(748, 496);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel seleccion = new JPanel();
        seleccion.setBackground(new Color(253, 234, 210));
        seleccion.setBounds(10, 99, 184, 347);
        getContentPane().add(seleccion);
        seleccion.setLayout(null);

        menu_1 = new JPanel();
        menu_1.setBackground(new Color(253, 234, 210));
        menu_1.setBounds(204, 99, 518, 347);
        getContentPane().add(menu_1);
        menu_1.setLayout(null);
        
        JLabel fondo = new JLabel(new ImageIcon(VentanaAdmin.class.getResource("/fondobase.png")));
        fondo.setBounds(0, 0, 518, 347);
        menu_1.add(fondo);

        JLabel lblNewLabel = new JLabel("Selecciona una opción");
        lblNewLabel.setBackground(new Color(242, 137, 75));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNewLabel.setForeground(new Color(80, 55, 37));
        lblNewLabel.setBounds(10, 11, 164, 14);
        seleccion.add(lblNewLabel);

        JButton opc1 = crearBoton("Ver menú completo");
        opc1.setBounds(10, 34, 164, 27);
        opc1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelMenuStaff menuCompleto = new PanelMenuStaff(facade, "Completo");
                mostrarPanel(menuCompleto);
            }
        });
        seleccion.add(opc1);

        JButton opc2 = crearBoton("Ver pedidos");
        opc2.setBounds(10, 72, 164, 27);
        opc2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelVerPedidos panelPedidos = new PanelVerPedidos(facade);
                mostrarPanel(panelPedidos);
            }
        });
        seleccion.add(opc2);

        JButton opc3 = crearBoton("Cambiar estado");
        opc3.setBounds(10, 110, 164, 27);
        opc3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelCambiarEstadoPedido panelEstado = new PanelCambiarEstadoPedido(facade);
                mostrarPanel(panelEstado);
            }
        });
        seleccion.add(opc3);

        JButton opc4 = crearBoton("Ver reportes");
        opc4.setBounds(10, 148, 164, 27);
        opc4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelReportes panelReporte = new PanelReportes(facade);
                mostrarPanel(panelReporte);
            }
        });
        seleccion.add(opc4);

        JButton opc5 = crearBoton("Cambiar precio");
        opc5.setBounds(10, 186, 164, 27);
        opc5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelCambiarPrecio panelPrecio = new PanelCambiarPrecio(facade);
                mostrarPanel(panelPrecio);
            }
        });
        seleccion.add(opc5);

        JButton opc6 = crearBoton("Agregar/Quitar");
        opc6.setBounds(10, 224, 164, 27);
        opc6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelAgregarQuitarProductos panelProductos = new PanelAgregarQuitarProductos(facade);
                mostrarPanel(panelProductos);
            }
        });
        seleccion.add(opc6);

        JButton opc7 = crearBoton("Dinero en caja");
        opc7.setBounds(10, 262, 164, 27);
        opc7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                facade.registrarAccionAdmin("Admin revisó el dinero en caja");

                PanelDineroCaja panelCaja = new PanelDineroCaja(facade);
                mostrarPanel(panelCaja);
            }
        });
        seleccion.add(opc7);

        JButton opc9 = crearBoton("Actividad admin");
        opc9.setBounds(10, 300, 164, 27);
        opc9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelActividadAdmin panelActividad = new PanelActividadAdmin(facade);
                mostrarPanel(panelActividad);
            }
        });
        seleccion.add(opc9);

        JButton opc8 = crearBoton("Salir");
        opc8.setBounds(10, 331, 164, 13);
        opc8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal ventanaP = new VentanaPrincipal();
                ventanaP.setVisible(true);
                dispose();
            }
        });
        seleccion.add(opc8);

        JLabel titulo = new JLabel(new ImageIcon(VentanaAdmin.class.getResource("/titulo.png")));
        titulo.setBounds(10, 11, 712, 77);
        getContentPane().add(titulo);

        PanelMenuStaff menuInicial = new PanelMenuStaff(facade, "Completo");
        mostrarPanel(menuInicial);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setOpaque(true);
        boton.setIcon(new ImageIcon(VentanaAdmin.class.getResource("/boton.png")));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setForeground(new Color(80, 55, 37));
        boton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        boton.setContentAreaFilled(false);
        boton.setBackground(new Color(245, 100, 118));

        return boton;
    }

    private void mostrarPanel(JPanel panel) {
        panel.setSize(518, 347);

        menu_1.removeAll();
        menu_1.setLayout(new BorderLayout());
        menu_1.add(panel, BorderLayout.CENTER);
        menu_1.revalidate();
        menu_1.repaint();
    }
}