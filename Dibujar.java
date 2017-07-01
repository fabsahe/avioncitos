/**
 * Clase Dibujar, hereda de JPanel y se encarga de dibujar toda la animacion
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class Dibujar extends JPanel implements ActionListener {

    private Timer timer; // El hilo que permite la animacion
    private Nave nave;
    private ArrayList<Enemigo> obstaculos;
    private boolean enJuego, crear=false;
    // posicion inicial de la nave
    private final int NAVE_X = 40;
    private final int NAVE_Y = 60;
    // medidas de la ventana
    private final int V_WIDTH = 1200;
    private final int V_HEIGHT = 680;
    // el retraso con el que trabaja el hilo y realiza las acciones
    private final int DELAY = 15;
    // variables utiles
    private ArrayList<Punto> posiciones;
    private int puntaje;
    private int golpesJefe;
    private Font neoletters;

    /**
     *  Constructor, agrega el controlador de los eventos del teclado, inicializa la nave y los primeros enemigos
     */
    public Dibujar() {

        addKeyListener(new TAdapter()); // Agregamos el listener del teclado
        setFocusable(true);
        setBackground(new Color(20,20,20));
        enJuego = true; // que comience el juego
        golpesJefe = 0; 
        setPreferredSize(new Dimension(V_WIDTH, V_HEIGHT));
        puntaje=0; // inicualizamos el puntaje
        nave = new Nave(NAVE_X, NAVE_Y); // se crea la nave
        InputStream fontStream = null;
        try {
            // se crea la font personalizada
            fontStream = new BufferedInputStream(new FileInputStream(".\\fonts\\neoletters.ttf")); 
            neoletters = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            neoletters = neoletters.deriveFont(Font.PLAIN, 18);
        } catch (FontFormatException ex) {
            Logger.getLogger(Dibujar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dibujar.class.getName()).log(Level.SEVERE, null, ex);
        }

        generarPosiciones();  // se crean las posiciones de los primeros enemigos
        crearEnemigos(); // se crean los enemigos

        timer = new Timer(DELAY, this); // se crea el hilo que permite la animacion
        timer.start(); // el hilo se pone en estado de listo
    }

    /**
     *  Metodo que crea las posiciones de los primeros enemigos
     */
    public void generarPosiciones() {
        posiciones = new ArrayList<>();
        Random rnd = new Random();
        Punto actual;
        for(int i=0;i<25;i++) {
            do {
                actual = new Punto( rnd.nextInt(800)+1200, rnd.nextInt(500)+100 ); // se genera un punto con posicion aleatoria
            }while( !separados(actual) ); // se crean nuevas posiciones si los enemigos estan muy cerca
            posiciones.add(actual);
        }
    }
    /**
     *  Metodo que indica si los enemigos estan lo suficiente separados entre ellos
     */
    public boolean separados(Punto p) {
        for(Punto p2:posiciones) {  // se recorren todos los puntos existentes
            int dx = Math.abs( p2.getX()-p.getX() );
            int dy = Math.abs( p2.getY()-p.getY() );
            if( dx < 120 && dy<50  ) { 
                return false;
            }
        }
        return true;
    }

    /** 
     *  Metodo que crea los enemigos a partir de las posiciones creadas
     */
    public void crearEnemigos() {
        obstaculos = new ArrayList<>();
        Random rnd = new Random();
        for (Punto p : posiciones) {
            // al principio solo aparecen 3 tipos de enemigos
            obstaculos.add( new Enemigo(p.getX(), p.getY(), rnd.nextInt(3), V_WIDTH) ); 
        }
    }

    /**
     * Metodo que agrega nuevos enemigos cuando se ha subido de nivel
    */
    public void agregarEnemigos(int num) {
        Random rnd = new Random();
        Punto actual;
        while(obstaculos.size()<30) {
            do {
                actual = new Punto( rnd.nextInt(1000)+1200, rnd.nextInt(500)+100 );
            }while( !separados(actual) );
            posiciones.add(actual);
            obstaculos.add( new Enemigo( actual.getX(), actual.getY(), 3, V_WIDTH ) );

        }        
    }
    /**
     *  Metodo que agrega el jefe final al arreglo de enemigos
     */
    public void agregarJefe() {
        //System.out.println(obstaculos.size());
        if(obstaculos.size()==0)
            obstaculos.add( new Enemigo(1100, 300, 5, V_WIDTH) );
    }

    /**
     *  Redifinicion del metodo paint para asignar un fondo si fuera necesario
     */
    @Override
    public void paint(Graphics g) {
        //Image imagen = new ImageIcon(getClass().getResource(".\\img\\fondo.png") ).getImage();
        Image imagen=null;
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, 960, 574, this);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
 
        super.paint(g);
    }    

    /**
     * Metodo que repinta la ventana cada vez que sea necesario
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enJuego) {
            dibujarObjetos(g);  // si el juego continua se dibujan los objetos del juego
        } else {
            if( puntaje==500 )
                dibujarGanaste(g); // si se alcanz el puntaje maximo dibuja un mensaje ganador
            else
                dibujarGameOver(g); // si no, dibuja juego terminado :(
        }
        Toolkit.getDefaultToolkit().sync(); // sincroniza el repintado de estos elementos con los eventos del teclado
    }

    /**
     * Metodo que dibuja todos los elementos del juego
     */
    private void dibujarObjetos(Graphics g) {

        if (nave.isVisible()) {
            g.drawImage(nave.getImagen(), nave.getX(), nave.getY(), this); // dibuja la nave
        }

        ArrayList<Misil> ms = nave.getMisiles();

        for (Misil m : ms) {
            if (m.isVisible()) {
                g.drawImage(m.getImagen(), m.getX(), m.getY(), this); // dibuja los misiles
            }
        }
        if( puntaje==100 ) {
            agregarEnemigos(5); // se crean nuevos enemigos cuando el puntaje llega a 100
        }
        if( puntaje==400 )
            agregarJefe(); // se crea el jefe cuando el puntaje llega a 400
        Iterator<Enemigo> iter = obstaculos.iterator();
        while ( iter.hasNext() ) {
                Enemigo a = iter.next();
            if( puntaje>=100 ) {

                a.setVelocidad(4); // los enemigos se mueven mas rapido cuando el puntaje llega a 100
            }
            if (a.isVisible()) {
                g.drawImage(a.getImagen(), a.getX(), a.getY(), this); // si el enemigo no ha sido impactado se sigue dibujando
            }
        }

        g.setColor(Color.WHITE);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(neoletters);
        g2.drawString("PUNTAJE: "+puntaje,V_WIDTH/2,20); // dibujamos el puntaje

    }

    /**
     *  Metodo que dibuja el mensaje ganador
     */
    private void dibujarGanaste(Graphics g) {

        String msg = "GANASTE!!";
        Font small = neoletters;
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(neoletters);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString(msg, (V_WIDTH - fm.stringWidth(msg)) / 2, V_HEIGHT / 2);
    }

    /**
     *  Metodo que dibuja el mensaje perdedor
     */
    private void dibujarGameOver(Graphics g) {

        String msg = "Juego terminado :'v";
        Font small = neoletters;
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(neoletters);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString(msg, (V_WIDTH - fm.stringWidth(msg)) / 2, V_HEIGHT / 2);
    }

    /** 
     *  Metodo que actualiza la interfaz al recibir un evento del teclado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        actualizarNave();
        actualizarMisiles();
        actualizarEnemigos();
        revisarColisiones();
        repaint();
    }
    /**
     *  Metodo que detiene el hilo de la animacion cuando se termina el juego
     */
    private void inGame() {
        if (!enJuego) {
            timer.stop();
        }
    }
    /**
     *  Metodo que repinta la nave en una nueva posicion
     */
    private void actualizarNave() {

        if (nave.isVisible()) {
            nave.mover();
        }
    }

    /**
     *  Metodo que anima los misiles
     */
    private void actualizarMisiles() {

        ArrayList<Misil> ms = nave.getMisiles();

        for (int i = 0; i < ms.size(); i++) {

            Misil m = ms.get(i);

            if (m.isVisible()) {
                m.mover();
            } else {
                ms.remove(i);
            }
        }
    }

    /**
     * Metodo que repinta los enemigos
     */
    private void actualizarEnemigos() {

        if (puntaje==500) {
            enJuego = false; // se termina el juego al alcanzar el puntaje maximo
            return;
        }

        for (int i = 0; i < obstaculos.size(); i++) {

            Enemigo a = obstaculos.get(i);

            if (a.isVisible()) {
                if( a.getTipo()==5 )
                    a.rebotar(); // si es el jefe, rebota
                else
                    a.mover(); // si no, se mueve normalmente
            } else {
                obstaculos.remove(i); // si ya no esta visible se elimina
            }
        }
    }

    /**
     *  Metodo que revisa las intersecciones entre los distintos elementos
     */
    public void revisarColisiones() {

        Rectangle r3 = nave.getPerimetro();

        for (Enemigo alien : obstaculos) {
            Rectangle r2 = alien.getPerimetro();
            if (r3.intersects(r2)) {
                nave.setVisible(false);
                alien.setVisible(false);
                enJuego = false;
            }
        }

        ArrayList<Misil> ms = nave.getMisiles();

        for (Misil m : ms) {
            Rectangle r1 = m.getPerimetro();
            for (Enemigo alien : obstaculos) {
                Rectangle r2 = alien.getPerimetro();
                if( alien.getTipo()==5 ) {
                    if (r1.intersects(r2)) {
                        puntaje+=10;
                        golpesJefe++;
                        m.setVisible(false);
                        if( golpesJefe==10 ) {
                            alien.setVisible(false);                        
                        }
                    }
                }
                else {
                    if (r1.intersects(r2)) {
                        puntaje += 10;
                        m.setVisible(false);
                        alien.setVisible(false);
                    }                    
                }
            }
        }
    }
    /**
     *  Clase que controla los eventos del teclado
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            nave.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            nave.keyPressed(e);
        }
    }
}
