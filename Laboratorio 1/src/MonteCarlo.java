package Labortatorio_1;

import java.util.Random;

public class MonteCarlo {

	public static int NUMERO_HILOS = 10;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Hilos hilos[] = new Hilos[NUMERO_HILOS];
		int iteraciones = 10000000;

		
		// Calculo de PI paralelo
		long inicioTime = System.nanoTime();
		for(int i=0; i<NUMERO_HILOS; i++) {
			hilos[i] = new Hilos(i+1,iteraciones);
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
		long puntos = 0;
		for( int i=0; i<NUMERO_HILOS; i++ ) {
			puntos += hilos[i].getPuntos();
		}
		
		long finalTime   = System.nanoTime();
		
		long totalTime = finalTime - inicioTime;
		System.out.println("Número de hilos: "+NUMERO_HILOS);
		System.out.print("Tiempo paralelo: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		double pi = 4 * ( (double) puntos  /  (double) (NUMERO_HILOS*iteraciones)  );
		System.err.print(pi);
		System.err.println(" paralelo");
		
		
		
		// Calculo de PI sin parelelizar
		Random rand = new Random();
		double r = 1.0;
		double x;
		double y;
		inicioTime = System.nanoTime();
		puntos = 0;
		for(int i=0; i<NUMERO_HILOS*iteraciones; i++){
			x = rand.nextDouble() * r;
			y = rand.nextDouble() * r;
			double d = Math.sqrt( x*x + y*y  );
			if( d <= r ) puntos++;
		}
		
		finalTime   = System.nanoTime();
		totalTime = finalTime - inicioTime;
		System.out.println("Metodo secuencial");
		System.out.print("Tiempo secuencial: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		pi = 4 * ( (double) puntos  /  (double) (NUMERO_HILOS*iteraciones)  );
		System.err.print(pi);
		System.err.println(" secuencial");
		
	}

}
