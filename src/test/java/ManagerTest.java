import org.junit.Test;
import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;
import sdd.VtreeType;

import java.io.File;

import static org.junit.Assert.*;

public class ManagerTest {

	@Test
	public void testSddManagerIntBoolean() {
		SddManager manager = new SddManager(5, false);
		assertEquals("the var count is not correct", 5, manager.getVarCount());
	}

    @Test
    public void testCopy() {
        SddManager manager = new SddManager(4,false);

        Sdd sdd1 = new Sdd(1, manager).conjoin(new Sdd(3, manager));
        Sdd sdd2 = new Sdd(2, manager).conjoin(new Sdd(4, manager));
		Sdd[] nodes = new Sdd[]{sdd1,sdd2};
        SddManager copy = manager.copy(nodes);
		assertEquals("The two manager should be of equal size",manager.getSize(), copy.getSize());
		assertNotEquals("The copy should not be the original manager.", manager, copy);
		assertNotEquals("The array should contain the copies.",sdd1, nodes[0]);
		assertNotEquals("The array should contain the copies.",sdd2, nodes[1]);
		assertEquals("The array should contain the copies.",nodes[0].getManager(), copy);
		assertEquals("The array should contain the copies.",nodes[1].getManager(), copy);
    }


//	 @Test
//	 public void testPrint() {
//	 SddManager manager = new SddManager(5, false);
//	 manager.print();
//	 }

    @Test
    public void testAutomaticGcMin() {
        SddManager manager = new SddManager(5, false);
        assertFalse(manager.isAutoGcMinOn());
        manager.useAutoGcMin(true);
        assertTrue(manager.isAutoGcMinOn());
        manager.useAutoGcMin(false);
        assertFalse(manager.isAutoGcMinOn());
    }

	@SuppressWarnings("unused")
	@Test
	public void testIsVarUsed() {
		SddManager manager = new SddManager(5, false);
		Sdd[] literalSdds = new Sdd[5];
		for (int i = 0; i < 5; i++)
			literalSdds[i] = new Sdd((i + 1), manager);
		Sdd sdd1a3 = literalSdds[0].conjoin(literalSdds[2]);
		Sdd sdd2o4 = literalSdds[1].disjoin(literalSdds[3]);
		Sdd sdd4an1a3 = literalSdds[3].conjoin(sdd1a3.negate());

		assertTrue(manager.isVarUsed(1));
		assertTrue(manager.isVarUsed(2));
		assertTrue(manager.isVarUsed(3));
		assertTrue(manager.isVarUsed(4));
		assertFalse(manager.isVarUsed(5));
	}

	@Test
	public void testRefGcDeref() {
		SddManager manager = new SddManager(5, false);
		Sdd[] literalSdds = new Sdd[5];
		for (int i = 0; i < 5; i++)
			literalSdds[i] = new Sdd((i + 1), manager);
		Sdd sdd1a3 = literalSdds[0].conjoin(literalSdds[2]);
		Sdd sdd2o4 = literalSdds[1].disjoin(literalSdds[3]);
		Sdd sdd4an1a3 = literalSdds[3].conjoin(sdd1a3.negate());

        long size = manager.getSize();
        manager.refGcDeref(new Sdd[]{sdd1a3,sdd2o4,sdd4an1a3});
        long size_after = manager.getSize();
        assertNotEquals(size, size_after);
	}

	@SuppressWarnings("unused")
	@Test
	public void testGetVtreeOfVar() {
		SddManager manager = new SddManager(5, false);
		Vtree vtree = manager.getVtreeOfVar(3);
        assertNotEquals(manager.getVtree(), vtree);
        Vtree vtree2 = manager.getVtree();
        while (vtree2.getLeftChild().getVar()!=3)
            vtree2 = vtree2.getRightChild();

        assertEquals(vtree2.getLeftChild(), vtree);
	}

	@Test
	public void testGetVarCount() {
		SddManager manager = new SddManager(5, false);
		assertEquals("the var count is not correct", 5, manager.getVarCount());
	}

	@Test
	public void testGetVarOrder() {
		SddManager manager = new SddManager(5, false);
		long[] vars = manager.getVarOrder();
		assertEquals(5, vars.length);
		for (int i = 0; i < vars.length; i++)
			assertEquals(vars[i], i + 1);
	}

	@Test
	public void testAddVarBeforeFirst() {
		SddManager manager = new SddManager(5, false);
		manager.addVarBeforeFirst();
		assertEquals("the var count is not correct", 6, manager.getVarCount());
        long[] varOrder = manager.getVarOrder();
        assertEquals(varOrder[0],6);
	}

	@Test
	public void testAddVarAfterLast() {
		SddManager manager = new SddManager(5, false);
		manager.addVarAfterLast();
		assertEquals("the var count is not correct", 6, manager.getVarCount());
        long[] varOrder = manager.getVarOrder();
        assertEquals(varOrder[varOrder.length-1],6);
	}

	@Test
	public void testAddVarBefore() {
		SddManager manager = new SddManager(5, false);
		manager.addVarBefore(3);
		assertEquals("the var count is not correct", 6, manager.getVarCount());
        long[] varOrder = manager.getVarOrder();
        int i3 = -1;
        int i6=-1;
        for (int i = 0; i<varOrder.length; i++){
            if (varOrder[i]==3) i3 = i;
            if (varOrder[i]==6) i6 = i;
        }
        assertNotEquals(i3,-1);
        assertNotEquals(i6,-1);
        assertEquals(i3, i6+1);
	}


	@Test
	public void testAddVarBeforeLca() {
		SddManager manager = new SddManager(5, false);
		manager.addVarBeforeLca(new long[]{3,5});
		assertEquals("the var count is not correct", 6, manager.getVarCount());

        int[] varPos = new int[7];
        long[] varOrder = manager.getVarOrder();
        for (int i = 1; i<varOrder.length; i++){
            varPos[(int) varOrder[i]]= i;
        }
        assertTrue(varPos[6]<varPos[3]);
        assertTrue(varPos[6]<varPos[5]);
	}


	@Test
	public void testMoveVarBeforeFirst() {
		Vtree vtree = new Vtree(5, VtreeType.Left);
		SddManager manager = new SddManager(vtree);
		manager.moveVarBeforeFirst(5);
		assertEquals("the var count is not correct", 5, manager.getVarCount());
		assertTrue(manager.getVtree().getVisualization().indexOf("5")<manager.getVtree().getVisualization().indexOf("1"));
	}


	@Test
	public void testMoveVarAfterLast() {
		Vtree vtree = new Vtree(5,VtreeType.Left);
		SddManager manager = new SddManager(vtree);
		assertEquals("((((1 2) 3) 4) 5)",manager.getVtree().getVisualization());
		manager.moveVarAfterLast(1);
		assertTrue(manager.getVtree().getVisualization().indexOf("5")<manager.getVtree().getVisualization().indexOf("1"));
	}


	@Test
	public void testMoveVarBefore() {
		Vtree vtree = new Vtree(5,VtreeType.Left);
		SddManager manager = new SddManager(vtree);
		manager.moveVarBefore(5,3);
		assertEquals("the var count is not correct", 5, manager.getVarCount());
		assertTrue(manager.getVtree().getVisualization().indexOf("5")<manager.getVtree().getVisualization().indexOf("3"));
	}


	@Test
	public void testMoveVarAfter() {
		Vtree vtree = new Vtree(5, VtreeType.Left);
		SddManager manager = new SddManager(vtree);
		manager.moveVarAfter(1,3);
		assertEquals("the var count is not correct", 5, manager.getVarCount());
		assertTrue(manager.getVtree().getVisualization().indexOf("3")<manager.getVtree().getVisualization().indexOf("1"));
	}

	@Test
	public void testMoveVarBeforeLca() {
		Vtree vtree = new Vtree(5,VtreeType.Balanced);
		SddManager manager = new SddManager(vtree);
		manager.moveVarBeforeLca(5, new long[]{3,4});
		assertEquals("the var count is not correct", 5, manager.getVarCount());
		assertTrue(manager.getVtree().getVisualization().indexOf("5")<manager.getVtree().getVisualization().indexOf("3"));
		assertTrue(manager.getVtree().getVisualization().indexOf("5")<manager.getVtree().getVisualization().indexOf("4"));

	}


	@Test
	public void testRemoveLastAddedVar() {
		SddManager manager = new SddManager(5, false);
		manager.addVarBefore(3);
		manager.removeLastAddedVar();
		assertEquals("the var count is not correct", 5, manager.getVarCount());
	}

	@Test
	public void testMultipleRemoveLastAddedVar() {
		SddManager manager = new SddManager(5, false);
		manager.removeLastAddedVar();
		manager.removeLastAddedVar();
		assertEquals("the var count is not correct", 3, manager.getVarCount());
	}

	@Test
	public void testAddVarAfter() {
		SddManager manager = new SddManager(5, false);
		manager.addVarAfter(3);
		assertEquals("the var count is not correct", 6, manager.getVarCount());
		long[] varOrder = manager.getVarOrder();
        int i3 = -1;
        int i6=-1;
        for (int i = 0; i<varOrder.length; i++){
            if (varOrder[i]==3) i3 = i;
            if (varOrder[i]==6) i6 = i;
        }
        assertNotEquals(i3,-1);
        assertNotEquals(i6,-1);
        assertEquals(i3, i6-1);

	}

	@SuppressWarnings("unused")
	@Test
	public void SddManagerSizeAndCount() {
		SddManager manager = new SddManager(5, false);
		Sdd[] literalSdds = new Sdd[5];
		for (int i = 0; i < 5; i++)
			literalSdds[i] = new Sdd((i + 1), manager);
		Sdd sdd1a3 = literalSdds[0].conjoin(literalSdds[2]);
		Sdd sdd2o4 = literalSdds[1].disjoin(literalSdds[3]);
		Sdd sdd5an1a3 = literalSdds[4].conjoin(sdd1a3.negate());

		sdd1a3.ref();
		assertEquals(5, manager.getCount());
		assertEquals(1, manager.getLiveCount());
		assertEquals(4, manager.getDeadCount());

		assertEquals(10, manager.getSize());
		assertEquals(2, manager.getLiveSize());
		assertEquals(8, manager.getDeadSize());
	}

//	@Test
//	public void testSaveSharedDot() {
//		SddManager manager = new SddManager(3, false);
//		Sdd lit1 = new Sdd(1, manager);
//		Sdd lit2 = new Sdd(2, manager);
//		Sdd lit3 = new Sdd(3, manager);
//		Sdd sdd1 = lit1.conjoin(lit2);
//        Sdd sdd2 = lit2.conjoin(lit3);
//
//        manager.saveSharedAsDot("shared.dot");
//	}

    
	@Test
	public void testGarbageCollect() {
        SddManager manager = new SddManager(5, false);
        Sdd sdd = new Sdd(1, manager).conjoin(new Sdd(2, manager));
        long count_before = manager.getDeadCount();
        manager.garbageCollect();
        long count_after= manager.getDeadCount();
		assertTrue(count_before>count_after);
	}

	 @Test
	 public void testOption() {
	 SddManager manager1 = new SddManager(5, false);
	 SddManager manager2 = new SddManager(5, true);
	 SddManager manager3 = new SddManager(6, false);
	 SddManager manager4 = new SddManager(6, true);

	 String options1 = "This is the first option";
	 int options2 = 354;
	 File options3 = new File("Sdd");
	 String[] options4 = {"option","otheroption"};

	 manager1.setOptions(options1);
	 manager2.setOptions(options2);
	 manager3.setOptions(options3);
	 manager4.setOptions(options4);


	 assertEquals(options1, manager1.getOptions());
	 assertEquals(options2, manager2.getOptions());
	 assertEquals(options3, manager3.getOptions());
	 assertEquals(options4, manager4.getOptions());

	 }

	@Test
	public void testGetLcaOfLiterals(){
		SddManager manager = new SddManager(5, false);
		Sdd l2 = new Sdd(2, manager);
		Sdd l3 = new Sdd(3, manager);
		Sdd l4 = new Sdd(4, manager);
		Sdd alpha = l4.conjoin(l2);
		alpha = alpha.conjoin(l3);
		long maxsize = manager.getVtree().getSize();

		assertTrue(manager.getLcaOfLiterals(new long[]{1}).getSize()==0);
		assertTrue(manager.getLcaOfLiterals(new long[]{1,2,3,4,5}).getSize()==maxsize);

	}

    @Test
    public void testMinimize(){
        SddManager manager = new SddManager(5, false);
        Sdd l2 = new Sdd(2, manager);
        Sdd l3 = new Sdd(3, manager);
        Sdd l4 = new Sdd(4, manager);
        Sdd sdd = l3.conjoin(l2).disjoin(l3.negate().conjoin(l2.negate()).conjoin(l4));
        sdd.ref();
        assertEquals(10, manager.getSize());
        manager.minimize();
        assertEquals(4,manager.getSize());
    }

    @Test
    public void testMinimizeVtree(){
        SddManager manager = new SddManager(5, false);
        Sdd l2 = new Sdd(2, manager);
        Sdd l3 = new Sdd(3, manager);
        Sdd l4 = new Sdd(4, manager);
        Sdd sdd = l3.conjoin(l2).disjoin(l3.negate().conjoin(l2.negate()).conjoin(l4));
        sdd.ref();
        assertEquals(10, manager.getSize());
        manager.minimize(manager.getVtree().getRightChild());
        assertEquals(8,manager.getSize());
    }

    @Test
    public void testMinimizeLimited(){
        SddManager manager = new SddManager(5, false);
        Sdd l2 = new Sdd(2, manager);
        Sdd l3 = new Sdd(3, manager);
        Sdd l4 = new Sdd(4, manager);
        Sdd sdd = l3.conjoin(l2).disjoin(l3.negate().conjoin(l2.negate()).conjoin(l4));
        sdd.ref();
        assertEquals(10, manager.getSize());
        manager.minimizeLimited();
        assertEquals(4,manager.getSize());
    }

    @Test
    public void testMinimizeVtreeLimited(){
        SddManager manager = new SddManager(5, false);
        Sdd l2 = new Sdd(2, manager);
        Sdd l3 = new Sdd(3, manager);
        Sdd l4 = new Sdd(4, manager);
        Sdd sdd = l3.conjoin(l2).disjoin(l3.negate().conjoin(l2.negate()).conjoin(l4));
        sdd.ref();
        assertEquals(10, manager.getSize());
        manager.minimizeLimited(manager.getVtree().getRightChild());
        assertEquals(8,manager.getSize());
    }

    @Test
    public void testSetConvergenceThreshold(){
        SddManager manager = new SddManager(5,false);
        manager.setConvergenceThreshold(1.0f);
    }
    @Test
    public void testSetVtreeSearchTimeLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeSearchTimeLimit(1.0f);
    }
    @Test
    public void testSetVtreeFragmentTimeLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeFragmentTimeLimit(1.0f);
    }
    @Test
    public void testSetVtreeOperationTimeLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeOperationTimeLimit(1.0f);
    }
    @Test
    public void testSetVtreeApplyTimeLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeApplyTimeLimit(1.0f);
    }
    @Test
    public void testSetVtreeOperationMemoryLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeOperationMemoryLimit(1.0f);
    }
    @Test
    public void testSetVtreeOperationSizeLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeOperationSizeLimit(1.0f);
    }
    @Test
    public void testSetVtreeCartesianProductLimit(){
        SddManager manager = new SddManager(5,false);
        manager.setVtreeCartesianProductLimit(100l);
    }

    @Test
    public void testInitVtreeSizeLimit(){
        SddManager manager = new SddManager(5, false);
        manager.initVtreeSizeLimit(manager.getVtree());
    }

    @Test
    public void testUpdateVtreeSizeLimit(){
        SddManager manager = new SddManager(5, false);
        manager.updateVtreeSizeLimit();
    }



}
