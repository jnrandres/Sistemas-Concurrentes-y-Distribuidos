package Presentacion;

import java.math.BigDecimal;
import java.util.Random;

public class MonteCarlo_Hilos extends Thread{

	private int id;
	private BigDecimal iteraciones;
	private Random rand;
	private BigDecimal puntos = BigDecimal.ZERO;

	public MonteCarlo_Hilos(int id, BigDecimal iteraciones) {
		this.id = id;
		this.iteraciones = iteraciones;
		rand = new Random();
	}

	public void run() {
		double x,y,d;
		BigDecimal i = BigDecimal.ZERO;
		for(  ; ; i = i.add(BigDecimal.ONE)  ) {
			x = rand.nextDouble();
			y = rand.nextDouble();
			d = Math.sqrt( x*x + y*y  );
			if( d < 1.0 ) puntos = puntos.add(BigDecimal.ONE);
			if(i.compareTo(iteraciones)==0) break;
		}
	}
	
	public int getId_() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(BigDecimal iteraciones) {
		this.iteraciones = iteraciones;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public BigDecimal getPuntos() {
		return puntos;
	}

	public void setPuntos(BigDecimal puntos) {
		this.puntos = puntos;
	}

	
}
