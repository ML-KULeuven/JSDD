package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;
import sdd.VtreeType;

public class Test1 {

	public static void main(String[] args) {
		// set up vtree and manager
		long[] varOrder = { 2, 1, 4, 3 };
		VtreeType type = VtreeType.Balanced;

		Vtree vtree = new Vtree(varOrder, type);
		SddManager manager = new SddManager(vtree);

		// construct a formula (A^B)v(B^C)v(C^D)
		System.out.println("constructing Sdd ... ");
		Sdd f_a = new Sdd(1, manager);
		Sdd f_b = new Sdd(2, manager);
		Sdd f_c = new Sdd(3, manager);
		Sdd f_d = new Sdd(4, manager);
		
		manager.refGcDeref(new Sdd[]{f_a,f_b,f_c,f_d});

		Sdd alpha = new Sdd(false, manager);
		Sdd beta;

		beta = f_a.conjoin(f_b);
		alpha = alpha.disjoin(beta);
		beta = f_b.conjoin(f_c);
		alpha = alpha.disjoin(beta);
		beta = f_c.conjoin(f_d);
		alpha = alpha.disjoin(beta);
		System.out.println("done\n");

		System.out.println("saving sdd and vtree ... ");
		alpha.saveAsDot("output/sdd.dot");
		vtree.saveAsDot("output/vtree.dot");
		System.out.println("done");

		vtree.free();
		manager.free();
	}
}
