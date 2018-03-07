package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;
import sdd.VtreeType;

public class Test2 {

	public static void main(String[] args) {
		// set up vtree and manager
		  long varCount = 4;
		  VtreeType type = VtreeType.Right;
		  Vtree vtree = new Vtree(varCount,type);
		  SddManager manager = new SddManager(vtree);

		  // construct the term X_1 ^ X_2 ^ X_3 ^ X_4
		  Sdd alpha = new Sdd(1, manager);
		  alpha = alpha.conjoin(new Sdd(2, manager));
		  alpha = alpha.conjoin(new Sdd(3, manager));
		  alpha = alpha.conjoin(new Sdd(4, manager));

		  // construct the term ~X_1 ^ X_2 ^ X_3 ^ X_4
		  Sdd beta = new Sdd(-1, manager);
		  beta = beta.conjoin(new Sdd(2, manager));
		  beta = beta.conjoin(new Sdd(3, manager));
		  beta = beta.conjoin(new Sdd(4, manager));

		  // construct the term ~X_1 ^ ~X_2 ^ X_3 ^ X_4
		  Sdd gamma = new Sdd(-1, manager);
		  gamma = gamma.conjoin(new Sdd(-2, manager));
		  gamma = gamma.conjoin(new Sdd(3, manager));
		  gamma = gamma.conjoin(new Sdd(4, manager));

		  System.out.println("== before referencing:");
		  System.out.println("  live sdd size = " + manager.getLiveSize());
		  System.out.println("  dead sdd size = " + manager.getDeadSize());

		  // ref SDDs so that they are not garbage collected
		  alpha.ref();
		  beta.ref();
		  gamma.ref();
		  
		  System.out.println("== after referencing:");
		  System.out.println("  live sdd size = " + manager.getLiveSize());
		  System.out.println("  dead sdd size = " + manager.getDeadSize());

		  // garbage collect
		  manager.garbageCollect();
		  System.out.println("== after garbage collection:");
		  System.out.println("  live sdd size = " + manager.getLiveSize());
		  System.out.println("  dead sdd size = " + manager.getDeadSize());

		  alpha.deref();
		  beta.deref();
		  gamma.deref();

		  System.out.println("saving vtree & shared sdd ...");
		  vtree.saveAsDot("output/shared-vtree.dot");
		  manager.saveSharedAsDot("output/shared.dot");

		  vtree.free();
		  manager.free();

	}
}
