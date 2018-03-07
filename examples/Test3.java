package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;

public class Test3 {

	public static void main(String[] args) {

		// set up vtree and manager
		Vtree vtree = Vtree.read("input/opt-swap.vtree");
		SddManager manager = new SddManager(vtree);

		System.out.println("reading sdd from file ...");
		Sdd alpha = Sdd.read("input/opt-swap.sdd",manager);
		System.out.println("  sdd size = " + alpha.getSize());

		// ref, perform the minimization, and then de-ref
		alpha.ref();
		System.out.println("minimizing sdd size ... ");
		manager.minimize();
		System.out.println("done!\n");
		System.out.println("  sdd size = " + alpha.getSize());
		alpha.deref();

		// augment the SDD
		System.out.println("augmenting sdd ...\n");
		Sdd beta = new Sdd(4, manager).disjoin(new Sdd(5, manager));
		beta = alpha.conjoin(beta);
		System.out.println("  sdd size = " + beta.getSize());

		// ref, perform the minimization again on new SDD, and then deref
		beta.ref();
		System.out.println("minimizing sdd ... ");
		manager.minimize();
		System.out.println("done!\n");
		System.out.println("  sdd size = " + beta.getSize());
		beta.deref();

		manager.free();
		vtree.free();
	}
}
