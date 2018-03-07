import org.junit.Test;
import sdd.Sdd;
import sdd.SddManager;
import sdd.WmcManager;

import static org.junit.Assert.assertTrue;

public class WmcManagerTest {

	@Test
	public void testPropagate() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmcOr = new WmcManager(or, false);
		wmcOr.setLiteralWeight(x, 2);
		wmcOr.setLiteralWeight(y, 0.5);

		assertTrue(Math.abs(3.5-wmcOr.propagate())<0.001);
	}

	@Test
	public void testGetZeroWeight() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmc = new WmcManager(or, false);
		WmcManager wmcLog = new WmcManager(or, true);
		assertTrue(wmc.getZeroWeight()==0);
		assertTrue(wmcLog.getZeroWeight()==Double.NEGATIVE_INFINITY);
	}

	@Test
	public void testGetOneWeight() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmc = new WmcManager(or, false);
		WmcManager wmcLog = new WmcManager(or, true);
		assertTrue(wmc.getOneWeight()==1);
		assertTrue(wmcLog.getOneWeight()==0);
	}

	@Test
	public void testGetWeight() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmcOr = new WmcManager(or, false);
		wmcOr.setLiteralWeight(x, 2);
		wmcOr.setLiteralWeight(y, 0.5);
		wmcOr.propagate();

		assertTrue(wmcOr.getWeight(x)==2);
		assertTrue(wmcOr.getWeight(y)==0.5);
	}

	@Test
	public void testGetDerivative() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmcOr = new WmcManager(or, false);
		wmcOr.setLiteralWeight(x, 2);
		wmcOr.setLiteralWeight(y, 0.5);
		wmcOr.propagate();

		assertTrue(wmcOr.getDerivative(x)==1.5);
		assertTrue(wmcOr.getDerivative(y)==3);
	}

	@Test
	public void testGetProbability() {
		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd xSDD = new Sdd(x, manager);
		Sdd ySDD = new Sdd(y, manager);
		Sdd or = xSDD.disjoin(ySDD);
		WmcManager wmcOr = new WmcManager(or, false);
		wmcOr.setLiteralWeight(x, 2);
		wmcOr.setLiteralWeight(y, 0.5);
		wmcOr.propagate();

		assertTrue(wmcOr.getProbability(x)==6.0/7);
		assertTrue(wmcOr.getProbability(y)==3.0/7);
	}

	@Test
	public void bugTest1(){

		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd SDD1 = new Sdd(x, manager);
		Sdd SDD2 = new Sdd(true, manager);

		WmcManager wmc1 = new WmcManager(SDD1, false);
		wmc1.propagate();
		WmcManager wmc2 = new WmcManager(SDD2, false);
		wmc2.propagate();

		assertTrue(wmc2.getProbability(x)==0.5);
		assertTrue(wmc2.getProbability(y)==0.5);
	}

	@Test
	public void bugTest2(){

		SddManager manager = new SddManager(2, false);
		int x=1, y=2;
		Sdd SDD1 = new Sdd(x, manager);
		Sdd SDD2 = new Sdd(y, manager);

		WmcManager wmc1 = new WmcManager(SDD1, false);
		wmc1.propagate();
		WmcManager wmc2 = new WmcManager(SDD2, false);
		wmc2.propagate();

		assertTrue(wmc2.getProbability(x)==0.5);
		assertTrue(wmc2.getProbability(y)==1);
	}



}
