import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaEmpleado extends JFrame{
	public VentanaEmpleado() {
		RestauranteFacade facade = new RestauranteFacade();
		getContentPane().setBackground(new Color(255, 248, 232));
		setTitle("Empleados - Restaurante México-Japonés");
        setSize(748, 496); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setLayout(null);
        
        
        
        JPanel seleccion = new JPanel();
        seleccion.setBackground(new Color(253, 234, 210));
        seleccion.setBounds(10, 99, 184, 347);
        getContentPane().add(seleccion);
        seleccion.setLayout(null);
        
        JPanel menu_1 = new JPanel();
        menu_1.setBackground(new Color(253, 234, 210));
        menu_1.setBounds(204, 99, 518, 347);
        getContentPane().add(menu_1);
        menu_1.setLayout(null);
        
        JLabel fondo = new JLabel(new ImageIcon(VentanaEmpleado.class.getResource("/fondobase.png")));
        fondo.setBounds(0, 0, 518, 347);
        menu_1.add(fondo);
        
        
        JButton opc1 = new JButton("Ver menú completo");
        opc1.setIcon(new ImageIcon(VentanaCliente.class.getResource("/boton.png")));
        opc1.setHorizontalTextPosition(SwingConstants.CENTER);
        opc1.setBackground(new Color(228, 63, 111));
        opc1.setForeground(new Color(80, 55, 37));
        opc1.setContentAreaFilled(false); 
        opc1.setOpaque(true);
        opc1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        opc1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ArrayList<Producto> lista = facade.obtenerMenu(); 
        		PanelMenuStaff menuCompleto = new PanelMenuStaff(facade,"Completo");
                menuCompleto.setSize(518, 347);
                menu_1.removeAll();
                menu_1.setLayout(new BorderLayout());
                menu_1.add(menuCompleto);
                menu_1.revalidate();
                menu_1.repaint();
        	}
        });
        opc1.setBounds(10, 34, 164, 56);
        seleccion.add(opc1);
        
        JButton opc3 = new JButton("Ver pedidos");
        opc3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PanelVerPedidos panelPedidos = new PanelVerPedidos(facade);
                panelPedidos.setSize(518, 347); 
                
                menu_1.removeAll();
                menu_1.setLayout(new java.awt.BorderLayout());
                menu_1.add(panelPedidos, java.awt.BorderLayout.CENTER);
                
                menu_1.revalidate();
                menu_1.repaint();
        	}
        });
        opc3.setIcon(new ImageIcon(VentanaCliente.class.getResource("/boton.png")));
        opc3.setHorizontalTextPosition(SwingConstants.CENTER);
        opc3.setForeground(new Color(80, 55, 37));
        opc3.setBackground(new Color(245, 100, 118));
        opc3.setContentAreaFilled(false); 
        opc3.setOpaque(true);
        opc3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        opc3.setBounds(10, 101, 164, 56);
        seleccion.add(opc3);
        
        JButton opc4 = new JButton("Cambiar estado de pedido");
        opc4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PanelCambiarEstadoPedido panelEstado = new PanelCambiarEstadoPedido(facade);
                panelEstado.setSize(518, 347); 
                menu_1.removeAll();
                menu_1.setLayout(new java.awt.BorderLayout());
                menu_1.add(panelEstado, java.awt.BorderLayout.CENTER);
                
                menu_1.revalidate();
                menu_1.repaint();
        	}
        });
        opc4.setIcon(new ImageIcon(VentanaCliente.class.getResource("/boton.png")));
        opc4.setHorizontalTextPosition(SwingConstants.CENTER);
        opc4.setForeground(new Color(80, 55, 37));
        opc4.setBackground(new Color(245, 100, 118));
        opc4.setContentAreaFilled(false); 
        opc4.setOpaque(true);
        opc4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        opc4.setBounds(10, 168, 164, 56);
        seleccion.add(opc4);
        
        JLabel lblNewLabel = new JLabel("Selecciona una opción");
        lblNewLabel.setBackground(new Color(242, 137, 75));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNewLabel.setForeground(new Color(80, 55, 37));
        lblNewLabel.setBounds(10, 11, 164, 14);
        seleccion.add(lblNewLabel);
        
        JButton opc6 = new JButton("Salir");
        opc6.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		VentanaPrincipal ventanaP = new VentanaPrincipal();
        		ventanaP.setVisible(true);
        		dispose();
        	}
        });
        opc6.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/boton.png")));
        opc6.setHorizontalTextPosition(SwingConstants.CENTER);
        opc6.setForeground(new Color(80, 55, 37));
        opc6.setBackground(new Color(245, 100, 118));
        opc6.setContentAreaFilled(false); 
        opc6.setOpaque(true);
        opc6.setBounds(10, 306, 164, 27);
        seleccion.add(opc6);
        
        JButton opc5 = new JButton("Ver reseñas");
        opc5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PanelResenas panelResenas = new PanelResenas(facade);
                panelResenas.setSize(518, 347); 
                menu_1.removeAll();
                menu_1.setLayout(new java.awt.BorderLayout());
                menu_1.add(panelResenas, java.awt.BorderLayout.CENTER);
                menu_1.revalidate();
                menu_1.repaint();
        	}
        });
        opc5.setOpaque(true);
        opc5.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/boton.png")));
        opc5.setHorizontalTextPosition(SwingConstants.CENTER);
        opc5.setForeground(new Color(80, 55, 37));
        opc5.setFont(new Font("Tahoma", Font.PLAIN, 13));
        opc5.setContentAreaFilled(false);
        opc5.setBackground(new Color(245, 100, 118));
        opc5.setBounds(10, 235, 164, 56);
        seleccion.add(opc5);
        
        
        
        JLabel titulo = new JLabel(new ImageIcon(VentanaEmpleado.class.getResource("/titulo.png")));
        titulo.setBounds(10, 11, 712, 77);
        getContentPane().add(titulo);
        
	}
}
