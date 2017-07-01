/**
 * Clase principal que ejecuta el juego
 */

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    /**
     * Constructor que crea el panel de animacion dentro de una ventana
     */
    public Main() {
        super("Aviones vs Aliens"); 
        add(new Dibujar());
        this.getContentPane().setBackground(new Color(0,0,0));
        setResizable(false);
        pack();
        
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *  Metodo main, ejecuta la aplicacion en un nuevo hilo
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main ex = new Main();
            }
        });
    }
}
