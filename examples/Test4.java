package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;

public class Test4 {

	public static void main(String[] args) {

		// set up vtree and manager
		Vtree vtree = Vtree.read("input/rotate-left.vtree");
		SddManager manager = new SddManager(vtree);

		// construct the term X_1 ^ X_2 ^ X_3 ^ X_4
		Sdd alpha = new Sdd(1, manager);
		alpha = alpha.conjoin(new Sdd(2,manager));
		alpha = alpha.conjoin(new Sdd(3,manager));
		alpha = alpha.conjoin(new Sdd(4,manager));

		// to perform a rotate, we need the manager's vtree
		Vtree manager_vtree = manager.getVtree();
		Vtree manager_vtree_right = manager_vtree.getRightChild();

		System.out.println("saving vtree & sdd ...");
		manager_vtree.saveAsDot("output/before-rotate-vtree.dot");
		alpha.saveAsDot("output/before-rotate-sdd.dot");

		// ref alpha (no dead nodes when rotating)
		alpha.ref();

                // garbage collect (no dead nodes when performing vtree operations)
                System.out.println("dead sdd nodes = "+manager.getDeadCount());
                System.out.println("garbage collection ...");
                manager.garbageCollect();
                System.out.println("dead sdd nodes = "+manager.getDeadCount());

		System.out.println("left rotating ... ");
		boolean succeeded = manager.rotateLeft(manager_vtree_right, false); //not limited
		System.out.println(succeeded?"succeeded":"did not succeed");

		// deref alpha, since ref's are no longer needed
		alpha.deref();

		// the root changed after rotation, so get the manager's vtree again
		manager_vtree =  manager.getVtree();

		System.out.println("saving vtree & sdd ...");
		manager_vtree.saveAsDot("output/after-rotate-vtree.dot");
		alpha.saveAsDot("output/after-rotate-sdd.dot");

		vtree.free();
		manager.free();
	}
}
