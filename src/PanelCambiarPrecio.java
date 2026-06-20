import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelCambiarPrecio extends JPanel {

    private RestauranteFacade facade;
    private JTextField idProducto;
    private JTextField nuevoPrecio;

    public PanelCambiarPrecio(RestauranteFacade facade) {
        this.facade = facade;
        
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(null);
        
        JLabel imagenCambiar = new JLabel(new ImageIcon(VentanaAdmin.class.getResource("/tituloModificar.png")));
        imagenCambiar.setBounds(0, 0, 518, 100);
        imagenCambiar.setHorizontalAlignment(SwingConstants.CENTER); 
        add(imagenCambiar);
        
        JLabel idprod = new JLabel("ID del Producto:");
        idprod.setBounds(110, 130, 120, 25); 
        idprod.setFont(new Font("Tahoma", Font.BOLD, 13));
        idprod.setForeground(new Color(80, 55, 37));
        add(idprod);

        idProducto = new JTextField(12);
        idProducto.setBounds(240, 130, 160, 25);
        idProducto.setFont(new Font("Tahoma", Font.PLAIN, 13));
        idProducto.setBackground(new Color(255, 248, 232));
        idProducto.setForeground(new Color(80, 55, 37));
        idProducto.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(idProducto);

        JLabel nuevoPreciotxt = new JLabel("Nuevo Precio ($):");
        nuevoPreciotxt.setBounds(110, 170, 120, 25);
        nuevoPreciotxt.setFont(new Font("Tahoma", Font.BOLD, 13));
        nuevoPreciotxt.setForeground(new Color(80, 55, 37));
        add(nuevoPreciotxt);

        nuevoPrecio = new JTextField(12);
        nuevoPrecio.setBounds(240, 170, 160, 25);
        nuevoPrecio.setFont(new Font("Tahoma", Font.PLAIN, 13));
        nuevoPrecio.setBackground(new Color(255, 248, 232));
        nuevoPrecio.setForeground(new Color(80, 55, 37));
        nuevoPrecio.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));
        add(nuevoPrecio);

        JLabel lblNewLabel = new JLabel("Revisar menú completo para consultar ID de los productos.");
        lblNewLabel.setBounds(0, 100, 518, 20);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 11)); 
        lblNewLabel.setForeground(new Color(120, 90, 70));
        add(lblNewLabel);

        JButton botonActualizar = new JButton("");
        botonActualizar.setIcon(new ImageIcon(VentanaAdmin.class.getResource("/botonPrecio.png")));
        botonActualizar.setHorizontalTextPosition(SwingConstants.CENTER);
        botonActualizar.setBounds(110, 250, 290, 40);
        botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 14));
        botonActualizar.setForeground(new Color(255, 248, 232));
        botonActualizar.setBackground(new Color(139, 95, 64)); 
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botonActualizar.addActionListener(e -> ejecutarCambioPrecio());
        add(botonActualizar);
    }

    private void ejecutarCambioPrecio() {
        String idTexto = idProducto.getText().trim();
        String precioTexto = nuevoPrecio.getText().trim();
        if (idTexto.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, llene todos los campos.", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idProductonum = Integer.parseInt(idTexto);
            double nuevoPrecionum = Double.parseDouble(precioTexto);
            if (nuevoPrecionum <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "El precio debe ser un número mayor a 0.", 
                    "Precio inválido", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String resultado = facade.actualizarPrecioMenu(idProductonum, nuevoPrecionum);
            
            JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);

            idProducto.setText("");
            nuevoPrecio.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Asegúrese de ingresar un ID entero y un Precio numérico válido.", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}