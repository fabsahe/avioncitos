/**
 * Clase Nave que hereda de Figura
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Nave extends Figura {

    private int dx;
    private int dy;
    private ArrayList<Misil> misiles;

    /** 
     * Constructor, asigna una posicion inicial a la nave y crea su dibujo
     */
    public Nave(int x, int y) {
        super(x, y);
        crearNave();
    }
    /**
     *  Metodo que carga la imagen de la nave y obtiene sus dimensiones
     */
    public void crearNave() {
        misiles = new ArrayList<>(); // se crea un arreglo de misiles
        cargarImagen(".\\img\\nave.png");
        getMedidasImg();        
    }
    /**
     * Metodo que se encarga de mover la nave
     */ 
    public void mover() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }
    /**
     * @return el arreglo de misiles
     */
    public ArrayList<Misil> getMisiles() {
        return misiles;
    }
    /**
     *  Metodo que detecta los eventos del teclado
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }
    /** 
     *  Metodo que dispara y crea un nuevo misil que se guarda en el arreglo de misiles
     */
    public void fire() {
        misiles.add(new Misil(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
