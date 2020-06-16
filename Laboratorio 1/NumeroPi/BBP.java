package Presentacion;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class BBP {

	public static void main(String[] args) {

		int NUMERO_HILOS = 4;
		
		BBP_Hilos bbp_hilos[] = new BBP_Hilos[NUMERO_HILOS];
		int iteraciones = 1000000;
		/*
		// Calculo de PI paralelo
		long inicioTime = System.nanoTime();
		
		// Inicializacion de los hilos
		bbp_hilos[0] = new BBP_Hilos(0,4,1);
		bbp_hilos[1] = new BBP_Hilos(1,2,4);
		bbp_hilos[2] = new BBP_Hilos(2,1,5);
		bbp_hilos[3] = new BBP_Hilos(3,1,6);
		
		for(int i=0; i<NUMERO_HILOS; i++) {
			bbp_hilos[i].setIteraciones(iteraciones);
			bbp_hilos[i].start();
		}
		
		
		try{
			for(int i=0; i<NUMERO_HILOS;i++){
				bbp_hilos[i].join();
			}
		}catch(Exception e){
			System.out.println(e);
		}
				
		// Capturando los resultados
		BigDecimal piTotal = bbp_hilos[0].getResultado();
		
		
		for( int i=1; i<NUMERO_HILOS; i++ ) {
			piTotal = piTotal.subtract(bbp_hilos[i].getResultado());
		}
		
		long finalTime   = System.nanoTime();
		
		long totalTime = finalTime - inicioTime;
		System.out.println("Número de hilos: "+NUMERO_HILOS);
		System.out.print("Tiempo paralelo join: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		System.out.println(piTotal);
		System.out.println("3.141592653589793238462643383279502884197169399375105820974944592307816406286");
		*/
		long inicioTime, finalTime, totalTime;
		
		// Modo secuencial
		inicioTime = System.nanoTime();
		BigDecimal pi = BigDecimal.ZERO;
		
		for (int i = 0; i <= iteraciones; i++) {
		    BigDecimal a = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(16).pow(i), 30, RoundingMode.HALF_UP);
		    BigDecimal b1 = BigDecimal.valueOf(4).divide(BigDecimal.valueOf(8).multiply(BigDecimal.valueOf(i)).add(BigDecimal.valueOf(1)), 30, RoundingMode.HALF_UP); 
		    BigDecimal b2 = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(8).multiply(BigDecimal.valueOf(i)).add(BigDecimal.valueOf(4)), 30, RoundingMode.HALF_UP); 
		    BigDecimal b3 = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8).multiply(BigDecimal.valueOf(i)).add(BigDecimal.valueOf(5)), 30, RoundingMode.HALF_UP); 
		    BigDecimal b4 = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8).multiply(BigDecimal.valueOf(i)).add(BigDecimal.valueOf(6)), 30, RoundingMode.HALF_UP); 
		    BigDecimal b = b1.subtract(b2).subtract(b3).subtract(b4);
		    pi = pi.add(a.multiply(b));
		}
		finalTime   = System.nanoTime();
		totalTime = finalTime - inicioTime;
		System.out.println("Metodo secuencial");
		System.out.print("Tiempo secuencial: ");
		System.out.print(totalTime/1000000000.0);
		System.out.println(" s");
		
		System.out.println(pi);
		System.out.println("3.141592653589793238462643383279502884197169399375105820974944592307816406286");

	}

}
