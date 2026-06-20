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

public class PanelResenas extends JPanel {

    private RestauranteFacade facade;
    private JTextArea areaResenas;
    private JComboBox<String> comboEstrellas;

    public PanelResenas(RestauranteFacade facade) {
        this.facade = facade;
        
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(null);
        
        JLabel lblTitulo = new JLabel(new ImageIcon(getClass().getResource("/tituloResenas.png")));
        lblTitulo.setBounds(0, -13, 518, 87);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        JLabel lblFiltrar = new JLabel("Filtrar por:");
        lblFiltrar.setBounds(30, 85, 90, 25);
        lblFiltrar.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblFiltrar.setForeground(new Color(80, 55, 37));
        add(lblFiltrar);

        comboEstrellas = new JComboBox<String>();
        comboEstrellas.addItem("Todas");
        comboEstrellas.addItem("5 estrellas");
        comboEstrellas.addItem("4 estrellas");
        comboEstrellas.addItem("3 estrellas");
        comboEstrellas.addItem("2 estrellas");
        comboEstrellas.addItem("1 estrella");
        comboEstrellas.setBounds(120, 85, 140, 25);
        comboEstrellas.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboEstrellas.setBackground(new Color(255, 248, 232));
        comboEstrellas.setForeground(new Color(80, 55, 37));
        comboEstrellas.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        comboEstrellas.addActionListener(e -> cargarResenas());
        add(comboEstrellas);

        areaResenas = new JTextArea();
        areaResenas.setEditable(false);
        areaResenas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResenas.setBackground(new Color(255, 248, 232));
        areaResenas.setForeground(new Color(80, 55, 37));
        
        JScrollPane scrollPane = new JScrollPane(areaResenas);
        scrollPane.setBounds(30, 120, 458, 165);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(scrollPane);

        JButton botonActualizar = new JButton("Actualizar Reseñas");
        botonActualizar.setBounds(159, 295, 200, 35);
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setBackground(new Color(139, 95, 64));
        botonActualizar.setOpaque(true);
        botonActualizar.setContentAreaFilled(true);
        botonActualizar.setBorderPainted(false);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botonActualizar.addActionListener(e -> cargarResenas());
        add(botonActualizar);

        cargarResenas();
    }

    private void cargarResenas() {
        String historialCompleto = facade.verResenas();
        String filtro = comboEstrellas.getSelectedItem().toString();
        
        if (filtro.equals("Todas")) {
            areaResenas.setText(historialCompleto);
            areaResenas.setCaretPosition(0);
            return;
        }
        
        String[] bloquesResenas = historialCompleto.split("-------------------------\n");
        StringBuilder resenasFiltradas = new StringBuilder();
        
        for (String bloque : bloquesResenas) {
            if (bloque.contains("Calificación: " + filtro)) {
                resenasFiltradas.append(bloque).append("-------------------------\n");
            }
        }
        
        if (resenasFiltradas.length() == 0) {
            areaResenas.setText("No hay comentarios registrados con: " + filtro);
        } else {
            areaResenas.setText(resenasFiltradas.toString());
        }
        
        areaResenas.setCaretPosition(0);
    }
}