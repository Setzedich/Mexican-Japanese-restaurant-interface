import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelDineroCaja extends JPanel {

    private RestauranteFacade facade;
    private JTextArea areaCaja;

    public PanelDineroCaja(RestauranteFacade facade) {
        this.facade = facade;

        setBackground(new Color(253, 234, 210));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Dinero en caja");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        titulo.setForeground(new Color(80, 55, 37));
        add(titulo, BorderLayout.NORTH);

        areaCaja = new JTextArea();
        areaCaja.setEditable(false);
        areaCaja.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaCaja.setBackground(new Color(255, 248, 232));
        areaCaja.setForeground(new Color(80, 55, 37));

        JScrollPane scroll = new JScrollPane(areaCaja);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(scroll, BorderLayout.CENTER);

        JButton botonActualizar = new JButton("Actualizar caja");
        botonActualizar.setBackground(new Color(139, 95, 64));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
        botonActualizar.setOpaque(true);
        botonActualizar.setContentAreaFilled(true);
        botonActualizar.setBorderPainted(false);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botonActualizar.addActionListener(e -> cargarCaja());
        add(botonActualizar, BorderLayout.SOUTH);

        cargarCaja();
    }

    private void cargarCaja() {
        areaCaja.setText(facade.verDineroCaja());
        areaCaja.setCaretPosition(0);
    }
}