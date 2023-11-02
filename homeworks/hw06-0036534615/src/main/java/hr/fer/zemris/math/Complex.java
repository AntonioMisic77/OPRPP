package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {

	private double re;
	private double im;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	public Complex() {
		this.re = 0;
		this.im = 0;
	}
	
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
		
	}
	
	/**
	 * Metoda izracunava i vraca udaljenost komplesnog broja od ishodista
	 * @return modul
	 */
	public double module() {
		return Math.hypot(re, im);
	}
	
	/**
	 * Metoda izracunava umnozak 2 kompleksna broja
	 * @param c
	 * @return Complex
	 */
	
	public Complex multiply(Complex c) {
		// ax - by + i (bx + ay) 
		double Newre = this.re*c.re-this.im*c.im;
		double Newim = this.im*c.re + this.re*c.im;
		
		return new Complex(Newre,Newim);		
	}
	
	/**
	 * Metoda izracunava kolicnik 2 kompleksna broja
	 * @param c
	 * @return Complex
	 */
	public Complex divide(Complex c) {
		return this.multiply(this.negativePow(c));
	}
	
	/**
	 * Metoda izracunava zbroj dva kompleksna broja.
	 * @param c
	 * @return Complex
	 */
	public Complex add(Complex c) {
		return new Complex(this.re+c.re,this.im+c.im);
	}
	
	
	/**
	 * Metoda izracunava razliku dva broja.
	 * @param c
	 * @return Complex
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re-c.re,this.im-c.im);
	}
	
	/**
	 * Metoda racuna konjugirano kompleksni broj
	 * @param c
	 * @return Complex
	 */
	public Complex negate() {
		return new Complex(-this.re,-this.im);
	}
	
	/**
	 * Metoda izracunava potenciju kompleksnog broja za zadanu joj potenciju
	 * @param n
	 * @return Complex
	 * @throws IllegalArgumentException ako je potencija manja od nule
	 */
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Ne smijete zadati negativan n");
		else if (n == 0) return new Complex(1,0);
		
		Complex c = new Complex(this.re,this.im);
		
		if (n > 1) {
		    for (int i=0;i<n-1;i++) {
			c = c.multiply(this);
		   }
		}
		return c;
	}
	
	/**
	 * Metoda racuna n-ti korijen kompleksnog broja
	 * @param n
	 * @return List<Complex>
	 * @throws IllegalArgumentException ako n nije veci od 2
	 */
	public List<Complex> root (int n){
		
		if (n < 2) throw new IllegalArgumentException("Unesite n veci od 2");
		
		List<Complex> list = new ArrayList<Complex>();
		
		double Basictheta = Math.atan2(this.im, this.re);
		double  module = Math.pow(this.module(), (double) 1/n);
		
		for (int i=0;i<n;i++) {
			double theta = (double) (Basictheta + 2*Math.PI*i) / n;
			
			list.add(new Complex(module*Math.cos(theta),module*Math.sin(theta)));
		}
		
		return list;
	}
	
	public String toString() {
		return "("+this.re+"+i"+this.im+")";
	}
	
	public Double real() {
		return this.re;
	}
	
	public Double imaginary() {
		return this.im;
	}
	
	
	private Complex negativePow(Complex c) {
		double nazivnik = Math.pow(re, 2) + Math.pow(im, 2);
		
		return new Complex((double)c.re / nazivnik,(double) -c.im / nazivnik);
	}
	
	
	public static void main(String[] args) {
		Complex c = new Complex(1,1);
		
		System.out.println(c.add(new Complex(1,1)).toString());
	}
}
