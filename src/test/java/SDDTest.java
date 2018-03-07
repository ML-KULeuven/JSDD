import org.junit.Test;
import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;
import sdd.VtreeType;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SDDTest {

	@Test
	public void testFalseSDD() {
		SddManager manager = new SddManager(5, false);
		Sdd sdd = new Sdd(false, manager);
		assertTrue(sdd.isFalse());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isLiteral());
		assertFalse(sdd.isDecision());
	}

	@Test
	public void testTrueSDD() {
		SddManager manager = new SddManager(5, false);
		Sdd sdd = new Sdd(true, manager);
		assertTrue(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isLiteral());
		assertFalse(sdd.isDecision());
	}

	@Test
	public void testLiteralSDD() {
		SddManager manager = new SddManager(5, false);
		Sdd sdd = new Sdd(3, manager);
		assertTrue(sdd.isLiteral());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isDecision());

		assertEquals(3, sdd.getLiteral());
	}

	@Test
	public void testGetManager() {
		SddManager manager = new SddManager(5, false);
		Sdd sdd = new Sdd(3, manager);
		assertEquals(manager, sdd.getManager());
	}

	@Test
	public void testApply() {
		SddManager manager = new SddManager(5, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd sdd = lit1.apply(lit2, Sdd.CONJOIN);

		assertTrue(sdd.isDecision());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isLiteral());
	}
//
//	@Test
//	public void testApplyInVtree() {
//		// TODO
//	}

	@Test
	public void testConjoin() {
		SddManager manager = new SddManager(5, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd sdd = lit1.conjoin(lit2);

		assertTrue(sdd.isDecision());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isLiteral());
	}

	@Test
	public void testDisjoin() {
		SddManager manager = new SddManager(5, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd sdd = lit1.disjoin(lit2);

		assertTrue(sdd.isDecision());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isLiteral());
	}

	@Test
	public void testNegate() {
		SddManager manager = new SddManager(5, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd sdd = lit1.negate();

		assertTrue(sdd.isLiteral());
		assertFalse(sdd.isTrue());
		assertFalse(sdd.isFalse());
		assertFalse(sdd.isDecision());
	}

	@Test
	public void testCondition() {
		SddManager manager = new SddManager(5, false);

		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);

		Sdd disjoin = lit1.disjoin(lit2);
		Sdd cond = disjoin.condition(-1);

		assertEquals(cond, lit2);
	}

    @Test
    public void testExists() {
        SddManager mgr = new SddManager(5,false);
        Sdd sdd1 = new Sdd(1,mgr).conjoin(new Sdd(2,mgr)).conjoin(new Sdd(3, mgr)).conjoin(new Sdd(4, mgr)).conjoin(new Sdd(5, mgr));
        Sdd sdd2 = new Sdd(1,mgr).conjoin(new Sdd(2, mgr));
        Sdd sdd1e = sdd1.exists(3).exists(4).exists(5);
        assertEquals(sdd1e,sdd2);
    }

	@Test
	public void testExistsMultiple() {

		SddManager mgr = new SddManager(5,false);
		Sdd sdd1 = new Sdd(1,mgr).conjoin(new Sdd(2,mgr)).conjoin(new Sdd(3, mgr)).conjoin(new Sdd(4, mgr)).conjoin(new Sdd(5, mgr));
		Sdd sdd2 = new Sdd(1,mgr).conjoin(new Sdd(2, mgr));
		Sdd sdd1e = sdd1.exists(new boolean[]{false, false, false, true, true, true});

		assertEquals(sdd1e,sdd2);
	}


	@Test
	public void testExistsMultipleStatic() {

		SddManager mgr = new SddManager(5,false);
		Sdd sdd1 = new Sdd(1,mgr).conjoin(new Sdd(2,mgr)).conjoin(new Sdd(3, mgr)).conjoin(new Sdd(4, mgr)).conjoin(new Sdd(5, mgr));
		Sdd sdd2 = new Sdd(1,mgr).conjoin(new Sdd(2, mgr));
		Sdd sdd1e = sdd1.existsStatic(new boolean[]{false, false, false, true, true, true});

		assertEquals(sdd1e,sdd2);
	}

	@Test
    public void testProject() {
        SddManager mgr = new SddManager(5,false);
        Sdd sdd1 = new Sdd(1,mgr).conjoin(new Sdd(2,mgr)).conjoin(new Sdd(3, mgr)).conjoin(new Sdd(4, mgr)).conjoin(new Sdd(5, mgr));
        Sdd sdd2 = new Sdd(1,mgr).conjoin(new Sdd(2, mgr));
        Sdd sdd1e = sdd1.project(new long[]{1,2});

        assertEquals(sdd1e,sdd2);
    }

	@Test
	public void testForall() {
        SddManager mgr = new SddManager(5,false);
        Sdd lit1 = new Sdd(1,mgr);
        Sdd lit2 = new Sdd(2,mgr);
        Sdd disjoin = lit1.disjoin(lit2);
        Sdd forall = disjoin.forall(1);
        assertEquals(forall, lit2);
	}

	@Test
	public void testMinimizeCardinality() {
		SddManager manager = new SddManager(2, false);

		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd disjoin = lit1.disjoin(lit2);

		Sdd xor = lit1.conjoin(lit2.negate()).disjoin(lit1.negate().conjoin(lit2));

		Sdd minCard = disjoin.minimizeCardinality();

		assertEquals(xor, minCard);
	}

	@Test
	public void testGlobalMinimizeCardinality() {
		SddManager manager = new SddManager(3, false);

		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd lit3 = new Sdd(3, manager);
		Sdd disjoin = lit1.disjoin(lit2);

		Sdd xorNot3 = lit1.conjoin(lit2.negate()).disjoin(lit1.negate().conjoin(lit2)).conjoin(lit3.negate());


		Sdd minCard = disjoin.globalMinimizeCardinality();

		assertEquals(xorNot3, minCard);
	}


	@Test
	public void testMinimumCardinality() {
		SddManager manager = new SddManager(2, false);

		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd disjoin = lit1.disjoin(lit2);

		assertEquals(disjoin.minimumCardinality(), 1);

	}
	@Test
	public void testModelCount() {
		SddManager manager = new SddManager(2, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd disjoin = lit1.disjoin(lit2);
		Sdd conjoin = lit1.conjoin(lit2);

		assertEquals(disjoin.ModelCount(), 3);
		assertEquals(conjoin.ModelCount(), 1);
	}

	@Test
	public void testGlobalModelCount() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd disjoin = lit1.disjoin(lit2);
		Sdd conjoin = lit1.conjoin(lit2);

		assertEquals(disjoin.GlobalModelCount(), 6);
		assertEquals(conjoin.GlobalModelCount(), 2);
	}

	@Test
	public void testGetNodeSize() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd lit3 = new Sdd(3, manager);
		Sdd conjoin = lit1.conjoin(lit2).conjoin(lit3);

		assertEquals(2, conjoin.getNodeSize());
		assertEquals(0, lit1.getNodeSize());
	}

	@Test
	public void testGetElements() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd lit3 = new Sdd(3, manager);
		Sdd conjoin = lit1.conjoin(lit2).conjoin(lit3);

		Sdd[] elements = conjoin.getElements();

		assertEquals(conjoin.getSize(), elements.length);

		assertTrue(elements[0].isLiteral());
		assertEquals(1, elements[0].getLiteral());
		assertTrue(elements[1].isDecision());
		assertTrue(elements[2].isLiteral());
		assertEquals(-1, elements[2].getLiteral());
		assertTrue(elements[3].isFalse());
	}

	@Test
	public void testBit() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		assertFalse(lit1.getBit());
		lit1.setBit(true);
		assertTrue(lit1.getBit());
		lit1.setBit(false);
		assertFalse(lit1.getBit());
	}

	@Test
	public void testCountSize() {
		Vtree vtree = new Vtree(5, VtreeType.Balanced);

		SddManager mgr = new SddManager(vtree);
		Sdd A = new Sdd(1,mgr);
		Sdd B = new Sdd(2,mgr);
		Sdd C = new Sdd(3,mgr);
		Sdd D = new Sdd(4,mgr);
		Sdd E = new Sdd(5,mgr);
		Sdd sdd = A.conjoin(B.conjoin(C));
		sdd = sdd.disjoin(A.conjoin(B.conjoin(D)));
		sdd = sdd.disjoin(A.conjoin(B.conjoin(E)));
		sdd = sdd.disjoin(A.conjoin(C.conjoin(D)));
		sdd = sdd.disjoin(A.conjoin(C.conjoin(E)));
		sdd = sdd.disjoin(A.conjoin(D.conjoin(E)));
		sdd = sdd.disjoin(B.conjoin(C.conjoin(D)));
		sdd = sdd.disjoin(B.conjoin(C.conjoin(E)));
		sdd = sdd.disjoin(B.conjoin(D.conjoin(E)));
		sdd = sdd.disjoin(C.conjoin(D.conjoin(E)));


		assertEquals(19, sdd.getSize());
		assertEquals(9, sdd.getCount());
		assertEquals(3, sdd.getNodeSize());

	}

	@Test
	public void testSharedSize() {
        SddManager manager = new SddManager(4,false);
        Sdd[] lits = new Sdd[5];
        for (int i=1; i<lits.length; i++){
            lits[i] = new Sdd(i, manager);
        }

        Sdd sdd_shared = lits[3].conjoin(lits[4]);
        Sdd sdd1 = lits[2].disjoin(sdd_shared);
        Sdd sdd2 = lits[1].disjoin(sdd_shared);
        long size1 = sdd1.getSize();
        long size2 = sdd2.getSize();
		long sharedSize = Sdd.sharedSize(new Sdd[]{sdd1, sdd2});

        assertTrue(size1+" "+sharedSize, size1<sharedSize);
        assertTrue(size2+" "+sharedSize, size2<sharedSize);
        assertTrue(size1+" "+size2+" "+sharedSize, size1+size2>sharedSize);
	}

	@Test
	public void testRef() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd sdd = lit1.conjoin(lit2);
		sdd.ref();
		sdd.ref();
		sdd.deref();
		sdd.ref();
		sdd.ref();
		sdd.deref();

		assertEquals(2, sdd.getRefCount());

	}

	@Test
	public void testRenameGetVariables() {
		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd sdd = lit1.conjoin(lit2);
		boolean[] vars = sdd.getVariables();
		assertEquals(4,vars.length);
		assertFalse(vars[0]);
		assertTrue(vars[1]);
		assertTrue(vars[2]);
		assertFalse(vars[3]);

		long[] variableMap = new long[4];
		variableMap[1]=3;
		variableMap[2]=2;
		variableMap[3]=1;
		Sdd sdd2 = sdd.renameVariables(variableMap);
		boolean[] vars2 = sdd2.getVariables();

		assertFalse(vars2[0]);
		assertFalse(vars2[1]);
		assertTrue(vars2[2]);
		assertTrue(vars2[3]);
	}

	@Test
	public void testGetNodes(){

		SddManager manager = new SddManager(3, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(2, manager);
		Sdd lit3 = new Sdd(3, manager);
		Sdd alpha = (lit1.conjoin(lit2)).disjoin(lit1.conjoin(lit3.negate()));
//		System.out.println(alpha.getVisualization());

		Sdd[] nodes = alpha.getElements();
		assertEquals(4, nodes.length);
		assertEquals(1, nodes[0].getLiteral());
		assertTrue(nodes[1].isDecision());
		assertEquals(-1, nodes[2].getLiteral());
		assertTrue(nodes[3].isFalse());
	}

//	@Test
//	public void testSave() {
//		SddManager manager = new SddManager(3, false);
//        Sdd lit1 = new Sdd(1, manager);
//        Sdd lit2 = new Sdd(2, manager);
//        Sdd lit3 = new Sdd(3, manager);
//		Sdd sdd = lit1.conjoin(lit2).conjoin(lit3);
//
//		sdd.save("sdd.sdd");
//		sdd.saveAsDot("sdd.dot");
//
//
//		SddManager manager2 = new SddManager(3, false);
//		Sdd sdd2 = Sdd.read("sdd.sdd", manager2);
//		sdd2.saveAsDot("sdd2.dot");
//	}


	@Test
	public void testGetUsedVars() {
		SddManager manager = new SddManager(10, false);
		Sdd lit1 = new Sdd(1, manager);
		Sdd lit2 = new Sdd(4, manager);
		Sdd lit3 = new Sdd(6, manager);
		Sdd alpha = (lit1.conjoin(lit2)).disjoin(lit1.conjoin(lit3.negate()));

		Set<Long> vars = alpha.getUsedVariables();

		assertEquals(3, vars.size());
		assertTrue(vars.contains(1L));
		assertTrue(vars.contains(4L));
		assertTrue(vars.contains(6L));

        assertTrue(new Sdd(true, manager).getUsedVariables().isEmpty());
	}

}
