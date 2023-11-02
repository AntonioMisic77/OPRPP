package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class ComplexPolynomial {

	private Complex[] factors;
	private short order;
	
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
		this.order = (short) (this.factors.length-1);
	}
	
	/**
	 * Metoda racuna rednu velicinu polinoma.
	 * @return short
	 */
	
	public short order() {
		return order;
	}
	
	/**
	 * Metoda racuna umnozak dvaju polinoma.
	 * @param p
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
		Complex[] resultFactors = new Complex[p.factors.length + this.factors.length -1];
		
		for(int i=0;i<resultFactors.length;i++) {
			resultFactors[i] = Complex.ZERO;
		}
		
		for(int i=0;i<this.factors.length;i++) {
			for (int j=0;j<p.factors.length;j++) {
				resultFactors[i+j] = resultFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
				
			}
		}
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Metoda racuna derivaciju kompleksnog polinoma
	 * @return ComplexPolynomial koji predstavlja derivirani polinom.
	 */
	public ComplexPolynomial derive() {
		Complex[] resultFactors = new Complex[this.factors.length];
		
		for(int i=0;i<resultFactors.length;i++) {
			resultFactors[i] = Complex.ZERO;
		}
		
		for (int i=this.factors.length-1;i>=1;i--) {
			resultFactors[i-1] = this.factors[i].power(i-1);
		}
		
		order-=1;
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Metoda racuna vrijednost polinoma u zadanoj tocki
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for(int i=0;i<this.factors.length;i++) {
			result = result.add(this.factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	public String toString() {
		String result = "";
		
		for(int i=order;i>=0;i--) {
			result+=this.factors[i].toString()+"*z^"+i;
		}
		
		return result;
	}
}
