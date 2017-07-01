/**
 *  Clase Misil que hereda de figura
 */
public class Misil extends Figura {

    private final int LARGO_VENTANA = 1300;
    private final int VELOCIDAD = 2;

    /**
     * Constructor, asigna la posicion inicial del misil
     */
    public Misil(int x, int y) {
        super(x, y);

        crearMisil();
    }
    /**
     *  Metodo que carga la imagen del misil y obtiene sus medidas
     */
    private void crearMisil() {
        cargarImagen(".\\img\\misil.png");
        getMedidasImg();        
    }
    /** 
     *  Metodo que se encarga de mover el misil
    */
    public void mover() {
        
        x += VELOCIDAD;
        
        if (x > LARGO_VENTANA)
            vis = false;
    }
}