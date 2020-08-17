package Presentacion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BBP_Hilos extends Thread{
	
	private int id_;
	private int numerador;
	private int termino;
	private int iteraciones;
	BigDecimal resultado = BigDecimal.ZERO;
	
	public BBP_Hilos (int id, int numerador, int termino) {
		this.id_ = id;
		this.numerador = numerador;
		this.termino = termino;
	}
	
	public void run() {
		
		for (int i = 0; i <= this.iteraciones; i++) {
		    BigDecimal a = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(16).pow(i), 30, RoundingMode.HALF_UP);
		    BigDecimal b1 = BigDecimal.valueOf(this.numerador).divide(BigDecimal.valueOf(8).multiply(BigDecimal.valueOf(i)).add(BigDecimal.valueOf(this.termino)), 30, RoundingMode.HALF_UP); 
		    this.resultado = this.resultado.add(a.multiply(b1));
		}
		
	}

	public int getId_() {
		return id_;
	}

	public void setId_(int id) {
		this.id_ = id;
	}

	public int getNumerador() {
		return numerador;
	}

	public void setNumerador(int numerador) {
		this.numerador = numerador;
	}

	public int getTermino() {
		return termino;
	}

	public void setTermino(int termino) {
		this.termino = termino;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
	}
	
	public BigDecimal getResultado() {
		return this.resultado;
	}
	

}
