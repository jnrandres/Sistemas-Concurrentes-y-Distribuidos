package BattleCity_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Juego extends JPanel implements ActionListener{

	private Bloque br;
	
	private ImageIcon player;
	private int playerX = 200;
	private int playerY = 550;
	private boolean playerDerecha = false;
	private boolean playerIzquierda = false;
	private boolean playerAbajo = false;
	private boolean playerArriba = true;

	private boolean playerDisparo = false;
	private String bombaDisparoDir = "";
	
	private Timer timer;
	private int delay = 8;
	
	private JugadorListener playerListener;
	private JugadorBomba playerBomba;
	
	private boolean play = true;
	
	public Juego () {
		br = new Bloque();
		playerListener = new JugadorListener();
		setFocusable(true);
		addKeyListener(playerListener);
		
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer (delay, this);
		timer.start();
	}
	
	
	public void paint (Graphics g) {
		//Fondo del juego
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);
	
		//Fondo para las vidas y el score
		g.setColor(Color.DARK_GRAY);
		g.fillRect(650, 0, 140, 600);
		
		//Dibujando los muros solidos
		br.dibujarBloquesSolidos(this, g);
		br.dibujarBloquesSimples(this,g);
		
		
		
		if(play) {
			
			//Dibujando el jugador
			if( playerArriba )
				player = new ImageIcon("player_tank_arriba.png");
			else if (playerAbajo)
				player = new ImageIcon("player_tank_abajo.png");
			else if (playerDerecha)
				player = new ImageIcon("player_tank_derecha.png");
			else if (playerIzquierda)
				player = new ImageIcon("player_tank_izquierda.png");
			
			player.paintIcon(this, g, playerX, playerY);
			
			if( playerBomba != null && playerDisparo ) {
				if( bombaDisparoDir.equals("") ) {
					if( playerArriba )
						bombaDisparoDir = "arriba";
					else if (playerAbajo)
						bombaDisparoDir = "abajo";
					else if (playerDerecha)
						bombaDisparoDir = "derecha";
					else if (playerIzquierda)
						bombaDisparoDir = "izquierda";
				}else {
					playerBomba.mover(bombaDisparoDir);
					playerBomba.dibujar(g);
				}
				
				
				if( br.checkColision(playerBomba.getX(), playerBomba.getY())  || 
						br.checkSolidColision( playerBomba.getX(), playerBomba.getY()) ) {
					playerBomba = null;
					playerDisparo = false;
					bombaDisparoDir = "";
				}
				
				if( playerBomba != null ) {
					if( playerBomba.getY() < 1 || playerBomba.getY() > 580  || 
							playerBomba.getX() < 1 || playerBomba.getX() > 630) {
						playerBomba = null;
						playerDisparo = false;
						bombaDisparoDir = "";
					}
				}
				
			}
			
			g.dispose();
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		repaint();
	}
	
	
	public class JugadorListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			if( e.getKeyCode() == KeyEvent.VK_U ) {
				if(!playerDisparo) {
					if( playerArriba )
						playerBomba = new JugadorBomba( playerX + 20, playerY );
					else if( playerAbajo )
						playerBomba = new JugadorBomba( playerX + 20, playerY + 40);
					else if( playerDerecha )
						playerBomba = new JugadorBomba( playerX + 40, playerY  + 20);
					else if( playerIzquierda )
						playerBomba = new JugadorBomba( playerX, playerY + 20 );
					playerDisparo = true;
				}
			}
			
			if( e.getKeyCode() == KeyEvent.VK_W ) {
				playerDerecha = false;
				playerIzquierda = false;
				playerAbajo = false;
				playerArriba = true;
				
				if( ! br.checkColisionJugador(playerX, playerY-10) ) {
					if( !( playerY < 10 ) )
						playerY -= 10;
				}
				
				
				
			}
			
			if( e.getKeyCode() == KeyEvent.VK_A ) {
				playerDerecha = false;
				playerIzquierda = true;
				playerAbajo = false;
				playerArriba = false;
				
				if( ! br.checkColisionJugador(playerX-10, playerY) ) {
					if( !( playerX < 10 ) )
						playerX -= 10;
				}
				
			}
			
			if( e.getKeyCode() == KeyEvent.VK_S ) {
				playerDerecha = false;
				playerIzquierda = false;
				playerAbajo = true;
				playerArriba = false;
				
				if( ! br.checkColisionJugador(playerX, playerY+10) ) {
					if( !( playerY > 540 ) )
						playerY += 10;
				}
				
				
			}
			
			if( e.getKeyCode() == KeyEvent.VK_D ) {
				playerDerecha = true;
				playerIzquierda = false;
				playerAbajo = false;
				playerArriba = false;
				
				if( ! br.checkColisionJugador(playerX+10, playerY) ) {
					if( !( playerX > 590 ) )
						playerX += 10;
				}
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
}
