import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelReportes extends JPanel {

    private RestauranteFacade facade;
    private JTextArea txtReporteGeneral;
    private JTextArea txtVentasPorDia;

    public PanelReportes(RestauranteFacade facade) {
        this.facade = facade;
        
        setLayout(new BorderLayout(0, 10));
        setBackground(new Color(253, 234, 210)); 

        Font fuenteTitulo = new Font("Tahoma", Font.BOLD, 13);
        Font fuenteTexto = new Font("Monospaced", Font.PLAIN, 12); 
        Color colorMarron = new Color(80, 55, 37);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperior.setBackground(new Color(253, 234, 210));
        
        JButton botonActualizar = new JButton("Actualizar Reporte");
        botonActualizar.setBackground(new Color(139, 95, 64));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
        botonActualizar.setOpaque(true);
        botonActualizar.setContentAreaFilled(true);
        botonActualizar.setBorderPainted(false);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelSuperior.add(botonActualizar);
        
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setBackground(new Color(253, 234, 210));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 5));
        panelIzquierdo.setBackground(new Color(253, 234, 210));

        JLabel lblReporte = new JLabel("Reporte General de Ventas:");
        lblReporte.setFont(fuenteTitulo);
        lblReporte.setForeground(colorMarron);
        panelIzquierdo.add(lblReporte, BorderLayout.NORTH);
        
        txtReporteGeneral = new JTextArea();
        txtReporteGeneral.setBackground(new Color(255, 248, 232));
        txtReporteGeneral.setEditable(false);
        txtReporteGeneral.setFont(fuenteTexto);
        txtReporteGeneral.setForeground(new Color(51, 51, 51));
        panelIzquierdo.add(new JScrollPane(txtReporteGeneral), BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(0, 5));
        panelDerecho.setBackground(new Color(253, 234, 210));

        JLabel lblHistorial = new JLabel("Historial de Ventas por Día:");
        lblHistorial.setFont(fuenteTitulo);
        lblHistorial.setForeground(colorMarron);
        panelDerecho.add(lblHistorial, BorderLayout.NORTH);
        
        txtVentasPorDia = new JTextArea();
        txtVentasPorDia.setBackground(new Color(255, 248, 232));
        txtVentasPorDia.setEditable(false);
        txtVentasPorDia.setFont(fuenteTexto);
        txtVentasPorDia.setForeground(new Color(51, 51, 51));
        panelDerecho.add(new JScrollPane(txtVentasPorDia), BorderLayout.CENTER);

        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelDerecho);
        
        add(panelCentral, BorderLayout.CENTER);

        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosReporte();
            }
        });

        cargarDatosReporte();
    }

    private void cargarDatosReporte() {
        Reporte reporte = facade.generarReporteVentas();

        if (reporte != null) {
            txtReporteGeneral.setText(reporte.obtenerTextoReporte());
        } else {
            txtReporteGeneral.setText("No hay datos de reportes disponibles.");
        }

        String datosDias = facade.generarDatosGraficaSimple();

        if (datosDias != null && !datosDias.trim().isEmpty()) {
            txtVentasPorDia.setText(datosDias);
        } else {
            txtVentasPorDia.setText("No se registran ventas por día aún.");
        }
    }
}