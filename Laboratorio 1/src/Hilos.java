package Labortatorio_1;

import java.util.Random;

public class Hilos extends Thread{
	
	private int id;
	private int iteraciones;
	private Random rand;
	private long puntos;
	
	public Hilos (int id, int iteraciones) {
		this.id = id;
		this.iteraciones = iteraciones;
		rand = new Random();
		this.puntos = 0;
	}
	
	
	public long getPuntos() {
		return puntos;
	}


	public void setPuntos(long puntos) {
		this.puntos = puntos;
	}


	public void run () {
		
		double r = 1.0;
		double x;
		double y;
		
		for(int i=0; i<iteraciones; i++){
			x = rand.nextDouble() * r;
			y = rand.nextDouble() * r;
			double d = Math.sqrt( x*x + y*y  );
			if( d <= r ) puntos++;
		}
			

		//System.out.println("Hilo: "+this.id+"\tPuntos: "+this.puntos);
		
	}
}
