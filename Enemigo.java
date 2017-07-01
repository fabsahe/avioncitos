/**
 * Clase Enemigo que hereda de Figura
 */
public class Enemigo extends Figura {

    private final String[] imagenes = { "alien.png", "asteroide.png", "ufo.png", "invader1.png", "invader2.png", "robot.png" }; // tipos de enemigo
    private int xInicial;
    private int velocidad;
    private int tipo;
    private int dirX=-1, dirY=-1;
    
    /**
     * Constructor que define la posicion del enemigo, su tipo y la posicion en donde comienza a dibujarse
     */
    public Enemigo(int x, int y, int k, int xini) {
        super(x, y);
        cargarImagen(".\\img\\"+imagenes[k]);
        getMedidasImg();
        tipo=k;
        velocidad=2;
        xInicial=xini;
    }

    /**
     *  Metodo que ajusta la velocidad del enemigo
     */
    public void setVelocidad(int vel) {
        velocidad = vel;
    }
    /**
     *  @return el tipo de enemigo
     */
    public int getTipo() {
        return tipo;
    }
    /**
     *  Metodo que se encarga de mover a los enemigos comunes
     */
    public void mover() {
        if (x < -32) {
            x = xInicial;
        }
        x -= velocidad;
    }
    /**
     *  Metodo que hace rebotar al jefe final
     */
    public void rebotar() {
        
        x = x + (dirX*2);
        y = y + (dirY*2);
        if ((x-0) <= 0) {
            dirX*= -1;
        }
        else if ((x+85) >= xInicial) {
            dirX*= -1;
        }

        if ((y-0) <= 0) {
            dirY*= -1;
        }
        else if ((y+128) >= 680) {
            dirY*= -1;
        }

    }

}