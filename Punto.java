// Clase punto que implementa un punto con coordenadas x, y
import java.awt.*;
import java.awt.geom.*;

public class Punto {
	private int x;
	private int y;

	public Punto() {
		this( 0, 0 );
	}
	public Punto( int px, int py ) {
		setX( px );
		setY( py );
	}
	public Punto( Punto p ) {
		this( p.x, p.y );
	}
	public void setX( int px ) {
		x = px;
	}
	public void setY( int py ) {
		y = py;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public int distancia(Punto p) {
		int dx = p.getX()-x;
		int dy = p.getY()-y;
		return (int)Math.sqrt((dx*dx)+(dy*dy));
	}

	@Override
	public String toString() {
		return "( " + x + ", " + y + " )";
	}


}