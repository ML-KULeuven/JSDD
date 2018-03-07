package examples;

import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;

public class Test5 {
	
	
	public static void main(String[] args) {

		// set up vtree and manager
		Vtree vtree = Vtree.read("input/big-swap.vtree");
		SddManager manager = new SddManager(vtree);

		System.out.println("reading sdd from file ...");
		Sdd alpha = Sdd.read("input/big-swap.sdd",manager);
		System.out.println("  sdd size = " + alpha.getSize());

		// to perform a swap, we need the manager's vtree
		Vtree manager_vtree = manager.getVtree();
		float limit;

		// ref alpha (no dead nodes when swapping)
		alpha.ref();
		
                //using size of sdd normalized for manager_vtree as baseline for limit
                manager.initVtreeSizeLimit(manager_vtree);

		limit = 2;
                manager.setVtreeOperationSizeLimit(limit);
		
		System.out.print("modifying vtree (swap node 7) (limit growth by " + limit + "x) ... ");
		boolean succeeded;
		succeeded = manager.swap(manager_vtree, true); //limited
		System.out.println(succeeded?"succeeded":"did not succeed");
		System.out.println("  sdd size = " + alpha.getSize());
		
		System.out.print("modifying vtree (swap node 7) (no limit) ... ");
		succeeded = manager.swap(manager_vtree, false); //not limited
		System.out.println(succeeded?"succeeded":"did not succeed");
		System.out.println("  sdd size = " + alpha.getSize());
		
		System.out.println("updating baseline of size limit ...\n");
		manager.updateVtreeSizeLimit();

		Vtree left_vtree = manager_vtree.getLeftChild();
		limit = 1.2f;
		manager.setVtreeOperationSizeLimit(limit);
		System.out.print("modifying vtree (swap node 5) (limit growth by "+limit+"x) ... ");
		succeeded = manager.swap(left_vtree, true); // limited
		System.out.println(succeeded?"succeeded":"did not succeed");
		System.out.println("  sdd size = "+alpha.getSize());

		limit = 1.3f;
		manager.setVtreeOperationSizeLimit(limit);
		System.out.print("modifying vtree (swap node 5) (limit growth by "+limit+"x) ... ");
		succeeded = manager.swap(left_vtree, true); // limited
		System.out.println(succeeded?"succeeded":"did not succeed");
		System.out.println("  sdd size = "+alpha.getSize());

		// deref alpha, since ref's are no longer needed
		alpha.deref();

		vtree.free();
		manager.free();
	}
}
