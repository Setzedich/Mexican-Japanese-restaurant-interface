import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class ReproductorMusica {
    private Player player;
    private Thread hiloMusica;
    private boolean reproduciendo;

    public ReproductorMusica() {
        reproduciendo = false;
    }

    public void reproducir(String ruta) {
        if (reproduciendo) {
            return;
        }

        reproduciendo = true;

        hiloMusica = new Thread(new Runnable() {
            public void run() {
                try {
                    while (reproduciendo) {
                        FileInputStream archivo = new FileInputStream(ruta);
                        BufferedInputStream buffer = new BufferedInputStream(archivo);

                        player = new Player(buffer);
                        player.play();

                        buffer.close();
                        archivo.close();
                    }

                } catch (Exception e) {
                    System.out.println("Error al reproducir la música");
                    e.printStackTrace();
                }
            }
        });

        hiloMusica.start();
    }

    public void detener() {
        reproduciendo = false;

        if (player != null) {
            player.close();
        }
    }
    
    public boolean isReproduciendo() {
        return this.reproduciendo;
    }
}