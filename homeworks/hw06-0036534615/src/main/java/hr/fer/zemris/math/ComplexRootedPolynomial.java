package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa s kojom modeliramo kompleksni polinom oblika
 * f(z) = Z0(Z-Z1)(Z-Z2)* ... * (Z-Zn) , gdje su Z1,Z2,...Zn njegove nultocke
 * @author Antonio
 *
 */
public class ComplexRootedPolynomial {

	private Complex constant;
	private List<Complex> roots;
	
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = new ArrayList<Complex>();
		for (var root : roots) {
			this.roots.add(root);
		}
	}
	
	/**
	 * Metoda izracunava vrijednost polinoma u zadanoj joj tocki	
	 * @param z
	 * @return Complex
	 */
	public Complex apply(Complex z) {
		
		Complex result = new Complex(constant.real(),constant.imaginary());
		
		for (var root : roots) {
			result = result.multiply(z.sub(root));
		}
			
		return result;
	}
	
	public ComplexPolynomial toComplexPolynomial() {
		
		ComplexPolynomial poly = new ComplexPolynomial(constant.multiply(this.roots.get(0).negate()),constant.multiply(Complex.ONE));
		
		for (int i=1;i<this.roots.size();i++) {
			poly = poly.multiply(new ComplexPolynomial(this.roots.get(i).negate(),Complex.ONE));
		}
		
		return poly;
	}
	
	
	public String toString() {
		String s= constant.toString();
		for(var element : this.roots) {
			s+= "(z-"+element.toString()+")";
		}
		
		return s;
	}
	
	public int indexOfClosestRootFor(Complex z, double treshold) {
		return 0;
	}
	
	public static void main(String[] args) {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE,Complex.IM_NEG,Complex.ZERO,Complex.ONE_NEG);
		System.out.println(poly.apply(Complex.ONE).toString());
		System.out.println(poly.toString());
	}
	
}
