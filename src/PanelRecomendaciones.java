	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PanelRecomendaciones extends JPanel {

    private RestauranteFacade facade;
    private JTextArea areaRecomendaciones;

    public PanelRecomendaciones(RestauranteFacade facade) {
        this.facade = facade;

        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(253, 234, 210));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        crearTitulo();
        crearBotones();
        crearAreaTexto();

        mostrarBienvenida();
    }

    private void crearTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(139, 95, 64));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel titulo = new JLabel("Recomendaciones del Chef");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 248, 232));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Elige una opción para descubrir combinaciones México-Japonesas");
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 248, 232));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);
    }

    private void crearBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 5, 5));
        panelBotones.setBackground(new Color(253, 234, 210));

        JButton botonMexicano = crearBoton("Mexicano");
        JButton botonJapones = crearBoton("Japonés");
        JButton botonFusion = crearBoton("Fusión");
        JButton botonVegano = crearBoton("Vegano");
        JButton botonCarrito = crearBoton("Mi carrito");

        panelBotones.add(botonMexicano);
        panelBotones.add(botonJapones);
        panelBotones.add(botonFusion);
        panelBotones.add(botonVegano);
        panelBotones.add(botonCarrito);

        add(panelBotones, BorderLayout.SOUTH);

        botonMexicano.addActionListener(e -> mostrarMexicano());
        botonJapones.addActionListener(e -> mostrarJapones());
        botonFusion.addActionListener(e -> mostrarFusion());
        botonVegano.addActionListener(e -> mostrarVegano());
        botonCarrito.addActionListener(e -> mostrarSegunCarrito());
    }

    private void crearAreaTexto() {
        areaRecomendaciones = new JTextArea();
        areaRecomendaciones.setEditable(false);
        areaRecomendaciones.setLineWrap(true);
        areaRecomendaciones.setWrapStyleWord(true);
        areaRecomendaciones.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaRecomendaciones.setBackground(new Color(255, 248, 232));
        areaRecomendaciones.setForeground(new Color(80, 55, 37));
        areaRecomendaciones.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(areaRecomendaciones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        boton.setForeground(new Color(80, 55, 37));
        boton.setBackground(new Color(255, 248, 232));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(139, 95, 64)));

        return boton;
    }

    private void mostrarBienvenida() {
        areaRecomendaciones.setText("");
        areaRecomendaciones.append("Bienvenido a las recomendaciones del chef.\n\n");
        areaRecomendaciones.append("Aquí puedes elegir una categoría y el sistema te mostrará\n");
        areaRecomendaciones.append("combinaciones de comida, bebida y postre.\n\n");
        areaRecomendaciones.append("También puedes usar la opción \"Mi carrito\" para recibir\n");
        areaRecomendaciones.append("una recomendación según los productos que ya agregaste.\n");
    }

    private void mostrarMexicano() {
        areaRecomendaciones.setText("");
        areaRecomendaciones.append("RECOMENDACIÓN MEXICANA\n");
        areaRecomendaciones.append("--------------------------------------\n\n");
        areaRecomendaciones.append("Comida:\n");
        areaRecomendaciones.append("- Tacos al pastor\n");
        areaRecomendaciones.append("- Enchiladas verdes\n");
        areaRecomendaciones.append("- Pozole rojo\n\n");
        areaRecomendaciones.append("Bebida:\n");
        areaRecomendaciones.append("- Agua de horchata\n");
        areaRecomendaciones.append("- Agua de jamaica\n\n");
        areaRecomendaciones.append("Postre:\n");
        areaRecomendaciones.append("- Churros\n");
        areaRecomendaciones.append("- Flan\n\n");
        areaRecomendaciones.append("Combo recomendado:\n");
        areaRecomendaciones.append("Tacos al pastor + Agua de horchata + Churros\n");
    }

    private void mostrarJapones() {
        areaRecomendaciones.setText("");
        areaRecomendaciones.append("RECOMENDACIÓN JAPONESA\n");
        areaRecomendaciones.append("--------------------------------------\n\n");
        areaRecomendaciones.append("Comida:\n");
        areaRecomendaciones.append("- Ramen tradicional\n");
        areaRecomendaciones.append("- Sushi roll\n");
        areaRecomendaciones.append("- Yakimeshi\n\n");
        areaRecomendaciones.append("Bebida:\n");
        areaRecomendaciones.append("- Matcha\n");
        areaRecomendaciones.append("- Ramune\n\n");
        areaRecomendaciones.append("Postre:\n");
        areaRecomendaciones.append("- Mochi\n");
        areaRecomendaciones.append("- Dorayaki\n\n");
        areaRecomendaciones.append("Combo recomendado:\n");
        areaRecomendaciones.append("Ramen tradicional + Matcha + Mochi\n");
    }

    private void mostrarFusion() {
        areaRecomendaciones.setText("");
        areaRecomendaciones.append("RECOMENDACIÓN FUSIÓN\n");
        areaRecomendaciones.append("--------------------------------------\n\n");
        areaRecomendaciones.append("Comida:\n");
        areaRecomendaciones.append("- Ramen de birria\n");
        areaRecomendaciones.append("- Sushi de pastor\n");
        areaRecomendaciones.append("- Onigiri de mole\n\n");
        areaRecomendaciones.append("Bebida:\n");
        areaRecomendaciones.append("- Horchata matcha\n\n");
        areaRecomendaciones.append("Postre:\n");
        areaRecomendaciones.append("- Mochi de cajeta\n\n");
        areaRecomendaciones.append("Combo recomendado:\n");
        areaRecomendaciones.append("Sushi de pastor + Horchata matcha + Mochi de cajeta\n");
    }

    private void mostrarVegano() {
        areaRecomendaciones.setText("");
        areaRecomendaciones.append("RECOMENDACIÓN VEGANA\n");
        areaRecomendaciones.append("--------------------------------------\n\n");
        areaRecomendaciones.append("Comida:\n");
        areaRecomendaciones.append("- Onigiri\n");
        areaRecomendaciones.append("- Yakimeshi\n\n");
        areaRecomendaciones.append("Bebida:\n");
        areaRecomendaciones.append("- Agua de jamaica\n");
        areaRecomendaciones.append("- Matcha\n");
        areaRecomendaciones.append("- Ramune\n\n");
        areaRecomendaciones.append("Postre:\n");
        areaRecomendaciones.append("- Mochi\n");
        areaRecomendaciones.append("- Churros\n\n");
        areaRecomendaciones.append("Combo recomendado:\n");
        areaRecomendaciones.append("Onigiri + Matcha + Mochi\n");
    }

    private void mostrarSegunCarrito() {
        String carrito = facade.verCarrito();

        areaRecomendaciones.setText("");
        areaRecomendaciones.append("RECOMENDACIÓN SEGÚN TU CARRITO\n");
        areaRecomendaciones.append("--------------------------------------\n\n");

        if (carrito.contains("Carrito vacío")) {
            areaRecomendaciones.append("Tu carrito está vacío.\n\n");
            areaRecomendaciones.append("Te recomendamos empezar con alguno de estos combos:\n\n");
            areaRecomendaciones.append("- Tacos al pastor + Agua de horchata + Churros\n");
            areaRecomendaciones.append("- Ramen tradicional + Matcha + Mochi\n");
            areaRecomendaciones.append("- Sushi de pastor + Horchata matcha + Mochi de cajeta\n");
            return;
        }

        areaRecomendaciones.append("Tu carrito actual es:\n\n");
        areaRecomendaciones.append(carrito);
        areaRecomendaciones.append("\n\n");

        if (carrito.contains("Tacos") || carrito.contains("Pozole") || carrito.contains("Enchiladas")) {
            areaRecomendaciones.append("Como elegiste comida mexicana, te recomendamos agregar:\n");
            areaRecomendaciones.append("- Agua de horchata\n");
            areaRecomendaciones.append("- Churros\n");
        } else if (carrito.contains("Ramen") || carrito.contains("Sushi") || carrito.contains("Yakimeshi")) {
            areaRecomendaciones.append("Como elegiste comida japonesa, te recomendamos agregar:\n");
            areaRecomendaciones.append("- Matcha\n");
            areaRecomendaciones.append("- Mochi\n");
        } else if (carrito.contains("birria") || carrito.contains("pastor") || carrito.contains("mole")) {
            areaRecomendaciones.append("Como elegiste comida fusión, te recomendamos agregar:\n");
            areaRecomendaciones.append("- Horchata matcha\n");
            areaRecomendaciones.append("- Mochi de cajeta\n");
        } else {
            areaRecomendaciones.append("Te recomendamos completar tu pedido con:\n");
            areaRecomendaciones.append("- Una bebida\n");
            areaRecomendaciones.append("- Un postre\n");
        }
    }
}