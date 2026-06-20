import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PanelVerPedidos extends JPanel {

    private RestauranteFacade facade;
    private JTextArea areaPedidos;
    private JComboBox<String> comboFiltro;

    public PanelVerPedidos(RestauranteFacade facade) {
        this.facade = facade;
        
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(null);
        
        JLabel lblTitulo = new JLabel(new ImageIcon(getClass().getResource("/tituloPedidos.png")));
        lblTitulo.setBounds(0, 0, 518, 76);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        JLabel lblFiltrar = new JLabel("Mostrar estado:");
        lblFiltrar.setBounds(30, 85, 110, 25);
        lblFiltrar.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblFiltrar.setForeground(new Color(80, 55, 37));
        add(lblFiltrar);

        comboFiltro = new JComboBox<String>();
        comboFiltro.addItem("Todos");
        comboFiltro.addItem("Recibido");
        comboFiltro.addItem("En preparación");
        comboFiltro.addItem("Enviado");
        comboFiltro.addItem("Terminado");
        comboFiltro.setBounds(145, 85, 150, 25);
        comboFiltro.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboFiltro.setBackground(new Color(255, 248, 232));
        comboFiltro.setForeground(new Color(80, 55, 37));
        comboFiltro.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        comboFiltro.addActionListener(e -> cargarPedidos());
        add(comboFiltro);

        areaPedidos = new JTextArea();
        areaPedidos.setEditable(false);
        areaPedidos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaPedidos.setBackground(new Color(255, 248, 232));
        areaPedidos.setForeground(new Color(80, 55, 37));
        
        JScrollPane scrollPane = new JScrollPane(areaPedidos);
        scrollPane.setBounds(30, 120, 458, 165); 
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(scrollPane);

        JButton botonRefrescar = new JButton("Actualizar Lista");
        botonRefrescar.setBounds(159, 295, 200, 35);
        botonRefrescar.setFont(new Font("Tahoma", Font.BOLD, 13));
        botonRefrescar.setForeground(new Color(255, 248, 232));
        botonRefrescar.setBackground(new Color(139, 95, 64));
        botonRefrescar.setOpaque(true);
        botonRefrescar.setContentAreaFilled(true);
        botonRefrescar.setBorderPainted(false);
        botonRefrescar.setFocusPainted(false);
        botonRefrescar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botonRefrescar.addActionListener(e -> cargarPedidos());
        add(botonRefrescar);

        cargarPedidos();
    }

    private void cargarPedidos() {
        String historialCompleto = facade.verHistorial();
        String filtroSeleccionado = comboFiltro.getSelectedItem().toString();
        
        if (filtroSeleccionado.equals("Todos")) {
            areaPedidos.setText(historialCompleto);
            areaPedidos.setCaretPosition(0);
            return;
        }
        
        String[] bloquesPedidos = historialCompleto.split("-------------------------\n");
        StringBuilder pedidosFiltrados = new StringBuilder();
        
        for (String bloque : bloquesPedidos) {
            if (bloque.contains("Estado: " + filtroSeleccionado)) {
                pedidosFiltrados.append(bloque).append("-------------------------\n");
            }
        }
        
        if (pedidosFiltrados.length() == 0) {
            areaPedidos.setText("No hay pedidos con el estado: '" + filtroSeleccionado + "'.");
        } else {
            areaPedidos.setText(pedidosFiltrados.toString());
        }

        areaPedidos.setCaretPosition(0);
    }
}