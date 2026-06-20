import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PanelActividadAdmin extends JPanel {

    private RestauranteFacade facade;
    private JTextArea areaActividad;

    public PanelActividadAdmin(RestauranteFacade facade) {
        this.facade = facade;

        setBackground(new Color(253, 234, 210));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Actividad del administrador");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(new Color(80, 55, 37));
        add(titulo, BorderLayout.NORTH);

        areaActividad = new JTextArea();
        areaActividad.setEditable(false);
        areaActividad.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaActividad.setBackground(new Color(255, 248, 232));
        areaActividad.setForeground(new Color(80, 55, 37));

        JScrollPane scroll = new JScrollPane(areaActividad);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(scroll, BorderLayout.CENTER);

        JButton botonActualizar = new JButton("Actualizar actividad");
        botonActualizar.setBackground(new Color(139, 95, 64));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
        botonActualizar.setOpaque(true);
        botonActualizar.setContentAreaFilled(true);
        botonActualizar.setBorderPainted(false);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarActividad();
            }
        });

        add(botonActualizar, BorderLayout.SOUTH);

        cargarActividad();
    }

    private void cargarActividad() {
        areaActividad.setText(facade.verActividadAdmin());
        areaActividad.setCaretPosition(0);
    }
}