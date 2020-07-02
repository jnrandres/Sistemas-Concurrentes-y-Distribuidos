package BattleCity_1;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bloque {

	int bloquesXPos[] = {50,350,450,550,50,300,350,450,550,150,150,450,550,
			250,50,100,150,550,250,350,450,550,50,250,350,550,
			50,150,250,300,350,550,50,150,250,350,450,550,50,
			250,350,550};

	int bloquesYPos[] = {50,50,50,50,100,100,100,100,100,150,200,200,200,250,
				300,300,300,300,350,350,350,350,400,400,400,400,450,
				450,450,450,450,450,500,500,500,500,500,500,550,550,
				550,550};
	
	int solidBloquesXPos[] = {150,350,150,500,450,300,600,400,350,200,0,200,500};
	
	int solidBloquesYPos[] = {0,0,50,100,150,200,200,250,300,350,400,400,450};
	
	int bloqueON[] = new int[42];
	
	private ImageIcon simpleBloqueImage;
	private ImageIcon solidBloqueImage;
	
	
	public Bloque () {
		simpleBloqueImage = new ImageIcon("simple_bloque.jpg");
		solidBloqueImage = new ImageIcon("solid_bloque.jpg");
		
		for(int i=0; i<bloqueON.length; i++) {
			bloqueON[i] = 1;
		}
	}
	
	
	public void dibujarBloquesSimples (Component c, Graphics g) {
		for(int i=0; i<bloqueON.length; i++) {
			if( bloqueON[i] == 1 ) {
				simpleBloqueImage.paintIcon(c, g, bloquesXPos[i], bloquesYPos[i]);
			}
		}
	}
	
	public void dibujarBloquesSolidos (Component c, Graphics g) {
		for(int i=0; i<solidBloquesXPos.length; i++) {
			solidBloqueImage.paintIcon(c, g, solidBloquesXPos[i], solidBloquesYPos[i]);
		}
	}
	
	
	// Verificar colisiones entre el jugador y lo bloques
	public boolean checkColisionJugador (int x, int y) {
		boolean colision = false;
		boolean colisionSimple = false;
		for(int i=0; i<bloqueON.length; i++) {
			if(  bloqueON[i] == 1 ) {
				if(  new Rectangle(x,y,50,50).intersects(new Rectangle( bloquesXPos[i], bloquesYPos[i], 50, 50 ))) {
					colision = true;
					colisionSimple = true;
					break;
				}
			}
		}
		
		if( !colisionSimple ) {
			for(int i=0; i< solidBloquesXPos.length;i++)
			{		
				if(new Rectangle(x, y, 50, 50).intersects(new Rectangle(solidBloquesXPos[i], solidBloquesYPos[i], 50, 50)))
				{		
					colision = true;
					break;
				}			
			}
		}
		
		return colision;
	}
	
	//Verifica si la bomba colisiona con los bloques simples
	public boolean checkColision (int x, int y) {
		boolean colision = false;
		
		for(int i=0; i<bloqueON.length; i++) {
			if(  bloqueON[i] == 1 ) {
				if(  new Rectangle(x,y,10,10).intersects(new Rectangle( bloquesXPos[i], bloquesYPos[i], 50, 50 )) ) {
					bloqueON[i] = 0;
					colision = true;
					break;
				}
			}
		}
		return colision;
	}
	
	
	// Verificar si la bomba colisiona con un bloque solido
	// pero no puede derrumbar dichos bloques
	public boolean checkSolidColision(int x, int y){
		boolean colision = false;
		for(int i=0; i< solidBloquesXPos.length;i++)
		{		
			if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(solidBloquesXPos[i], solidBloquesYPos[i], 50, 50)))
			{		
				colision = true;
				break;
			}			
		}
		
		return colision;
	}
	
}
