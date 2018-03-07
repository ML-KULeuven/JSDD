package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.WmcManager;

public class WMC {

	public static void main(String[] args) {
		
		SddManager manager = new SddManager(2, false);
		long x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd and = xSDD.conjoin(ySDD);
		Sdd or = xSDD.disjoin(ySDD);
		
		WmcManager wmcAnd = new WmcManager(and, false);
		WmcManager wmcOr = new WmcManager(or, false);

		System.out.println("===== AND =====");
		System.out.println("Z = " + wmcAnd.propagate());
		System.out.println("W(x) = " + wmcAnd.getWeight(x));
		System.out.println("W(y) = " + wmcAnd.getWeight(y));
		System.out.println("P(x) = " + wmcAnd.getProbability(x));
		System.out.println("P(y) = " + wmcAnd.getProbability(y));
		System.out.println("D(x) = " + wmcAnd.getDerivative(x));
		System.out.println("D(y) = " + wmcAnd.getDerivative(y));
		System.out.println();
		System.out.println("===== OR =====");
		System.out.println("Z = " + wmcOr.propagate());
		System.out.println("W(x) = " + wmcOr.getWeight(x));
		System.out.println("W(y) = " + wmcOr.getWeight(y));
		System.out.println("P(x) = " + wmcOr.getProbability(x));
		System.out.println("P(y) = " + wmcOr.getProbability(y));
		System.out.println("D(x) = " + wmcOr.getDerivative(x));
		System.out.println("D(y) = " + wmcOr.getDerivative(y));
		
		System.out.println();
		System.out.println();
		System.out.println("-----------------change weights-----------------");
		System.out.println();

		wmcAnd.setLiteralWeight(x, 2);
		wmcAnd.setLiteralWeight(y, 0.5);
		wmcOr.setLiteralWeight(x, 2);
		wmcOr.setLiteralWeight(y, 0.5);

		System.out.println("===== AND =====");
		System.out.println("Z = " + wmcAnd.propagate());
		System.out.println("W(x) = " + wmcAnd.getWeight(x));
		System.out.println("W(y) = " + wmcAnd.getWeight(y));
		System.out.println("P(x) = " + wmcAnd.getProbability(x));
		System.out.println("P(y) = " + wmcAnd.getProbability(y));
		System.out.println("D(x) = " + wmcAnd.getDerivative(x));
		System.out.println("D(y) = " + wmcAnd.getDerivative(y));
		System.out.println();
		System.out.println("===== OR =====");
		System.out.println("Z = " + wmcOr.propagate());
		System.out.println("W(x) = " + wmcOr.getWeight(x));
		System.out.println("W(y) = " + wmcOr.getWeight(y));
		System.out.println("P(x) = " + wmcOr.getProbability(x));
		System.out.println("P(y) = " + wmcOr.getProbability(y));
		System.out.println("D(x) = " + wmcOr.getDerivative(x));
		System.out.println("D(y) = " + wmcOr.getDerivative(y));
		wmcAnd.free();
		wmcOr.free();
		manager.free();
		
		
	}

}
