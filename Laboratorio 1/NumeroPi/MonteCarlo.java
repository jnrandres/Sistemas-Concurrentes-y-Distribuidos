package Presentacion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


public class MonteCarlo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int NUMERO_HILOS = 10;
		BigDecimal iteraciones = new BigDecimal("100000000");
		BigDecimal puntos = BigDecimal.ZERO;
		
		// Calculo de PI paralelo
		MonteCarlo_Hilos hilos[] = new MonteCarlo_Hilos[NUMERO_HILOS];
		long inicioTime = System.nanoTime();
		for(int i=0; i<NUMERO_HILOS; i++) {
			hilos[i] = new MonteCarlo_Hilos(i,iteraciones);
			hilos[i].start();
		}
		
		
		try{
			for(int i=0; i<NUMERO_HILOS;i++){
				hilos[i].join();
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		// Capturando los resultados
		for( int i=0; i<NUMERO_HILOS; i++ ) {
			puntos = puntos.add(hilos[i].getPuntos());
		}
		
		long finalTime   = System.nanoTime();
		
		long totalTime = finalTime - inicioTime;
		System.out.println("Número de hilos: "+NUMERO_HILOS);
		System.out.print("Tiempo paralelo: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		
		BigDecimal pi = puntos.multiply(BigDecimal.valueOf(4));
		iteraciones = iteraciones.multiply(BigDecimal.valueOf(NUMERO_HILOS));
		pi = pi.divide(iteraciones,10, RoundingMode.HALF_UP);
		System.out.println(pi);
		
		// Modo Secuencial
		inicioTime = System.nanoTime();
		puntos = BigDecimal.ZERO;
		Random rand = new Random();
		double x,y,d;
		
		BigDecimal i = BigDecimal.ZERO;
		
		for(  ; ; i = i.add(BigDecimal.ONE)  ) {
			x = rand.nextDouble();
			y = rand.nextDouble();
			d = Math.sqrt( x*x + y*y  );
			if( d < 1.0 ) puntos = puntos.add(BigDecimal.ONE);
			if(i.compareTo(iteraciones)==0) break;
		}
		
		finalTime   = System.nanoTime();
		totalTime = finalTime - inicioTime;
		System.out.print("Tiempo secuencial: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		
		pi = puntos.multiply(BigDecimal.valueOf(4));
		pi = pi.divide(iteraciones,10, RoundingMode.HALF_UP);
		
		System.out.print(pi);
		
	}

}
