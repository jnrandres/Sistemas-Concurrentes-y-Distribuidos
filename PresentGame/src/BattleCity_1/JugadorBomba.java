package BattleCity_1;

import java.awt.Color;
import java.awt.Graphics;

public class JugadorBomba {

	private double x,y;
	
	public JugadorBomba(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	public void mover(String cara) {
		if( cara.equals("derecha") )
			x += 5;
		else if( cara.equals("izquierda") )
			x -= 5;
		else if ( cara.equals("arriba") )
			y -= 5;
		else
			y += 5;
	}
	
	public void dibujar (Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval((int)x, (int)y, 10, 10);
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
}
