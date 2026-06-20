import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

public class PanelSoporteTecnico extends JPanel {

    private JTextArea areaChat;
    private JTextField campoPregunta;
    private ServicioGemini servicioGemini;

    public PanelSoporteTecnico() {
        servicioGemini = new ServicioGemini();

        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        crearTitulo();
        crearChat();
        crearPartePregunta();

        mostrarBienvenida();
    }

    private void crearTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(139, 95, 64));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel titulo = new JLabel("Aiko Soporte");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Asistente inteligente del Restaurante México-Japonés");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 248, 232));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);
    }

    private void crearChat() {
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
        areaChat.setFont(new Font("Tahoma", Font.PLAIN, 13));
        areaChat.setBackground(new Color(255, 248, 232));
        areaChat.setForeground(new Color(80, 55, 37));
        areaChat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaChat);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        add(scroll, BorderLayout.CENTER);
    }

    private void crearPartePregunta() {
        JPanel panelAbajo = new JPanel(new BorderLayout(5, 5));
        panelAbajo.setBackground(new Color(253, 234, 210));

        JLabel etiqueta = new JLabel("Escribe tu duda:");
        etiqueta.setForeground(new Color(80, 55, 37));
        etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));

        campoPregunta = new JTextField();
        campoPregunta.setFont(new Font("Tahoma", Font.PLAIN, 13));

        JButton botonEnviar = new JButton("Enviar");
        JButton botonLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelBotones.setBackground(new Color(253, 234, 210));
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonEnviar);

        panelAbajo.add(etiqueta, BorderLayout.NORTH);
        panelAbajo.add(campoPregunta, BorderLayout.CENTER);
        panelAbajo.add(panelBotones, BorderLayout.EAST);

        add(panelAbajo, BorderLayout.SOUTH);

        botonEnviar.addActionListener(e -> enviarPregunta());
        botonLimpiar.addActionListener(e -> limpiarChat());

        campoPregunta.addActionListener(e -> enviarPregunta());
    }

    private void mostrarBienvenida() {
        areaChat.setText("");
        areaChat.append("Aiko:\n");
        areaChat.append("¡Konnichiwa! Soy Aiko, tu asistente del Restaurante México-Japonés.\n");
        areaChat.append("Puedo ayudarte con dudas sobre el menú, carrito, promociones, pedidos,\n");
        areaChat.append("opciones veganas y menú del día.\n\n");
        areaChat.append("Ejemplos de preguntas:\n");
        areaChat.append("- ¿Qué platillo me recomiendas?\n");
        areaChat.append("- ¿Tienen opciones veganas?\n");
        areaChat.append("- ¿Cómo puedo hacer un pedido?\n\n");
    }

    private void enviarPregunta() {
        String pregunta = campoPregunta.getText();

        if (pregunta.trim().equals("")) {
            return;
        }

        areaChat.append("Cliente:\n");
        areaChat.append(pregunta + "\n\n");

        campoPregunta.setText("");
        areaChat.append("Aiko:\n");
        areaChat.append("Estoy revisando tu duda...\n\n");

        String respuesta = servicioGemini.preguntar(pregunta);

        areaChat.append("Aiko:\n");
        areaChat.append(respuesta + "\n\n");

        areaChat.setCaretPosition(areaChat.getDocument().getLength());
    }

    private void limpiarChat() {
        mostrarBienvenida();
    }
}