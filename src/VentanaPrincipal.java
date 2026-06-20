import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class VentanaPrincipal extends JFrame {
	private JPasswordField pw;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField nomCliente;
	private JTextField correoCliente;
    public static ReproductorMusica reproductor = new ReproductorMusica();

    public VentanaPrincipal() {
        setTitle("Restaurante Mexico-Japones");
        setSize(748, 496);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setLayout(null);
        
        if (!reproductor.isReproduciendo()) {
        	reproductor.reproducir("musica/SedaYagavebymarlon.mp3");
        }
        
        JRadioButton botonCliente = new JRadioButton("Cliente");
        botonCliente.setSelected(true);
        buttonGroup.add(botonCliente);
        botonCliente.setBounds(38, 308, 109, 23);
        botonCliente.setOpaque(false);
        getContentPane().add(botonCliente);
        
        JRadioButton botonEmpleado = new JRadioButton("Empleado");
        buttonGroup.add(botonEmpleado);
        botonEmpleado.setBounds(563, 348, 93, 23);
        botonEmpleado.setOpaque(false);
        getContentPane().add(botonEmpleado);
        
        JRadioButton botonAdmin = new JRadioButton("Administrador");
        buttonGroup.add(botonAdmin);
        botonAdmin.setBounds(563, 322, 96, 23);
        botonAdmin.setOpaque(false);
        getContentPane().add(botonAdmin);
        
        JButton inicioAceptar = new JButton("Aceptar");
        inicioAceptar.setBounds(323, 396, 89, 23);
        getContentPane().add(inicioAceptar);
        
        pw = new JPasswordField();
        pw.setBounds(566, 397, 93, 20);
        getContentPane().add(pw);
        
        JLabel lblNewLabel_2_1 = new JLabel("Contraseña");
        lblNewLabel_2_1.setBounds(566, 380, 82, 14);
        getContentPane().add(lblNewLabel_2_1);
        
        JLabel lblNewLabel_2_1_1 = new JLabel("Ingresa tu nombre");
		lblNewLabel_2_1_1.setBounds(38, 338, 109, 14);
		getContentPane().add(lblNewLabel_2_1_1);
        
        JLabel mal = new JLabel("INCORRECTO");
        mal.setForeground(new Color(255, 0, 0));
        mal.setBounds(566, 420, 75, 14);
        mal.setVisible(false);
        getContentPane().add(mal);
        
        JLabel lblNewLabel_2_1_1_1 = new JLabel("Correo electrónico");
		lblNewLabel_2_1_1_1.setBounds(38, 380, 109, 14);
		getContentPane().add(lblNewLabel_2_1_1_1);
		
		nomCliente = new JTextField();
		nomCliente.setBounds(38, 356, 172, 20);
		getContentPane().add(nomCliente);
		nomCliente.setColumns(10);
		
		correoCliente = new JTextField();
		correoCliente.setColumns(10);
		correoCliente.setBounds(38, 397, 172, 20);
		getContentPane().add(correoCliente);
		
        
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/ventanamain.png")));
		lblFondo.setBounds(0, 0, 732, 457); 
		getContentPane().add(lblFondo);
		
		
		
        
        inicioAceptar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		mal.setVisible(false);
        		if (botonEmpleado.isSelected()) {
        			String contra = new String(pw.getPassword());
        			if (contra.equals("skibidi")) {
        				VentanaEmpleado ventanaE = new VentanaEmpleado();
        				ventanaE.setVisible(true);
        				dispose();
        			}
        			else {
        				mal.setVisible(true);
        			}
        		}
        		else if (botonAdmin.isSelected()) {
        			String contra = new String(pw.getPassword());
        			if (contra.equals("toilet")) {
        				VentanaAdmin ventanaA = new VentanaAdmin();
        				ventanaA.setVisible(true);
        				dispose();
        			} else {
        				mal.setVisible(true);
        			}
        		}
        		else if (botonCliente.isSelected()) {
        			String nombre = nomCliente.getText().trim();
        		    String correo = correoCliente.getText().trim();
        		    if (nombre.isEmpty() || correo.isEmpty()) {
        		        javax.swing.JOptionPane.showMessageDialog(
        		            VentanaPrincipal.this, 
        		            "Ingresa tu nombre y correo electronico antes de continuar.", 
        		            "Campos obligatorios", 
        		            javax.swing.JOptionPane.WARNING_MESSAGE
        		        );
        		        return;
        		    }
        			VentanaCliente ventanaC = new VentanaCliente(nombre, correo);
        		    ventanaC.setVisible(true);
        		    dispose();
        		}
        		repaint();
        	}
        });
        
    }
}