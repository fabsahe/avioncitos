/**  
 *  Clase que implementa una figura del juego
 */
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Figura {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image imagen;

    /**
     * Constructor que asigna una posicion a la figura
     */
    public Figura(int x, int y) {
        this.x = x;
        this.y = y;
        vis = true;
    }

    /** 
     *  Metodo que obtiene las medidas de la imagen
     */
    protected void getMedidasImg() {
        width = imagen.getWidth(null);
        height = imagen.getHeight(null);
    }

    /** 
     * Metodo que asigna una imagen a la figura en particular
     */
    protected void cargarImagen(String imageName) {
        Image img = new ImageIcon(getClass().getResource(imageName)).getImage();
        imagen = img;
    }
    /**
     *  @return la imagen asignada
     */
    public Image getImagen() {
        return imagen;
    }

    /**
     *  @return la posicion en x
     */
    public int getX() {
        return x;
    }
    /**
     *  @return la posicion en y
     */
    public int getY() {
        return y;
    }
    /**
     *  @return la visibilidad de la imagen
     */
    public boolean isVisible() {
        return vis;
    }
    /**
     *  Metodo que asigna la visibilidad de la imagen
     */
    public void setVisible(Boolean visible) {
        vis = visible;
    }
    /**
     *  @return un rectangulo que representa el perimetro de la figura
     */
    public Rectangle getPerimetro() {
        return new Rectangle(x, y, width-1, height-1);
    }
}
