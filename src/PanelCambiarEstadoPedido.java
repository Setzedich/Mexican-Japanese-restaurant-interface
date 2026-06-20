import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelCambiarEstadoPedido extends JPanel {

    private RestauranteFacade facade;
    private JTextField campoIdPedido;
    private JComboBox<String> comboEstados;

    public PanelCambiarEstadoPedido(RestauranteFacade facade) {
        this.facade = facade;
        
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(null);
        
        JLabel lblTitulo = new JLabel(new ImageIcon(VentanaEmpleado.class.getResource("/tituloCambiar.png")));
        lblTitulo.setBounds(0, 0, 518, 76);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        JLabel lblIdPedido = new JLabel("ID del Pedido:");
        lblIdPedido.setBounds(110, 120, 120, 25);
        lblIdPedido.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblIdPedido.setForeground(new Color(80, 55, 37));
        add(lblIdPedido);

        campoIdPedido = new JTextField();
        campoIdPedido.setBounds(240, 120, 160, 25);
        campoIdPedido.setFont(new Font("Tahoma", Font.PLAIN, 13));
        campoIdPedido.setBackground(new Color(255, 248, 232));
        campoIdPedido.setForeground(new Color(80, 55, 37));
        campoIdPedido.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(campoIdPedido);

        JLabel lblNuevoEstado = new JLabel("Nuevo Estado:");
        lblNuevoEstado.setBounds(110, 165, 120, 25);
        lblNuevoEstado.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNuevoEstado.setForeground(new Color(80, 55, 37));
        add(lblNuevoEstado);

        comboEstados = new JComboBox<String>();
        comboEstados.addItem("Recibido");
        comboEstados.addItem("En preparación");
        comboEstados.addItem("Enviado");
        comboEstados.addItem("Terminado");
        comboEstados.setBounds(240, 165, 160, 25);
        comboEstados.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboEstados.setBackground(new Color(255, 248, 232));
        comboEstados.setForeground(new Color(80, 55, 37));
        comboEstados.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(comboEstados);

        JLabel lblAviso = new JLabel("Consulta la lista de pedidos para verificar el ID correspondiente.");
        lblAviso.setBounds(0, 210, 518, 20);
        lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
        lblAviso.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblAviso.setForeground(new Color(120, 90, 70));
        add(lblAviso);

        JButton botonActualizar = new JButton("Actualizar Estado");
        botonActualizar.setBounds(110, 250, 290, 40);
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 14));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setBackground(new Color(139, 95, 64));
        botonActualizar.setOpaque(true);
        botonActualizar.setContentAreaFilled(true);
        botonActualizar.setBorderPainted(false);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        botonActualizar.addActionListener(e -> ejecutarCambioEstado());
        add(botonActualizar);
    }

    private void ejecutarCambioEstado() {
        String idTexto = campoIdPedido.getText().trim();
        String nuevoEstado = comboEstados.getSelectedItem().toString();

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese el ID del pedido.", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPedidoNum = Integer.parseInt(idTexto);

            facade.cambiarEstadoPedido(idPedidoNum, nuevoEstado);

            JOptionPane.showMessageDialog(this, 
                "Estado del pedido actualizado a '" + nuevoEstado + "' con éxito.", 
                "Estado Actualizado", 
                JOptionPane.INFORMATION_MESSAGE);

            campoIdPedido.setText("");
            comboEstados.setSelectedIndex(0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Asegúrese de ingresar un ID de pedido numérico y entero válido.", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}