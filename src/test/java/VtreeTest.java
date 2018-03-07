import org.junit.Test;
import sdd.Sdd;
import sdd.SddManager;
import sdd.Vtree;
import sdd.VtreeType;

import java.io.File;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class VtreeTest {

	@Test
	public void testVtreeSizeType() {
		Vtree vtree = new Vtree(5, VtreeType.Balanced);
		assertEquals(5, vtree.getVarCount());
	}

	@Test
	public void testVtreeVarOrderType() {
		Vtree vtree = new Vtree(new long[] { 1, 2, 3, 4, 5 }, VtreeType.Left);
		assertEquals(5, vtree.getVarCount());
		assertEquals(5, vtree.getRightChild().getVar());

		Vtree vtree2 = new Vtree(new long[] { 5, 4, 3, 2, 1 }, VtreeType.Left);
		assertEquals(5, vtree2.getVarCount());
		assertEquals(1, vtree2.getRightChild().getVar());
	}

	@Test
	public void testVtreeXConstrained() {
		Vtree vtree = new Vtree(5, new long[] { 2, 3 }, VtreeType.Right);
		assertEquals(5, vtree.getVarCount());
		assert(vtree.getLeftChild().getVar()== 2 ||  vtree.getLeftChild().getVar()== 3);
		assert(vtree.getRightChild().getLeftChild().getVar()== 2 ||  vtree.getRightChild().getLeftChild().getVar()== 3);

	}


	@Test
	public void testSaveRead() {
        Vtree vtree = new Vtree(4,VtreeType.Balanced);
        vtree.save("vtree.vtree");
        Vtree read = Vtree.read("vtree.vtree");
        assertEquals(vtree.getVisualization(), read.getVisualization());
        read.saveAsDot("vtree.dot");
        
        new File("vtree.vtree").delete();
        new File("vtree.dot").delete();
        
	}


	@Test
	public void testGetLeftChild() {
        Vtree vtree = new Vtree(2, VtreeType.Balanced);
        assertEquals(vtree.getLeftChild().getVar(),1);
	}

	@Test
	public void testGetRightChild() {
        Vtree vtree = new Vtree(2, VtreeType.Balanced);
        assertEquals(vtree.getRightChild().getVar(),2);

	}

	@Test
	public void testGetParent() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        assertEquals(vtree.getLeftChild().getRightChild().getParent(), vtree.getLeftChild());
	}

	@Test
	public void testIsLeaf() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        assertTrue(vtree.getLeftChild().getRightChild().isLeaf());
        assertFalse(vtree.getLeftChild().isLeaf());

	}

	@Test
	public void testIsSub() {
        Vtree vtree = new Vtree(4, VtreeType.Left);
        assertTrue(vtree.getLeftChild().getLeftChild().isSub(vtree));
        assertFalse(vtree.getLeftChild().getLeftChild().isSub(vtree.getRightChild()));

	}

	@Test
	public void testGetLca() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        assertEquals(vtree.getLca(vtree.getLeftChild().getLeftChild(), vtree.getLeftChild().getRightChild()),vtree.getLeftChild());

	}

	@Test
	public void testGetVarCount() {
        Vtree vtree = new Vtree(4, VtreeType.Right);
        assertEquals(vtree.getVarCount(),4);

	}

	@Test
	public void testGetVar() {
        Vtree vtree = new Vtree(4, VtreeType.Right);
        assertEquals(vtree.getLeftChild().getVar(),1);

	}

    @Test
    public void testRotateLeft(){
        Vtree vtree = new Vtree(4, VtreeType.Right);
        SddManager manager = new SddManager(vtree);
        vtree = manager.getVtree();
        manager.useAutoGcMin(false);
        assert(vtree.getLeftChild().getVar()==1);
        assert(vtree.getRightChild().getLeftChild().getVar()==2);
        assert(vtree.getRightChild().getRightChild().getLeftChild().getVar()==3);
        assert(vtree.getRightChild().getRightChild().getRightChild().getVar()==4);

        manager.rotateLeft(vtree.getRightChild(), false);
        vtree = manager.getVtree();

        assert(vtree.getLeftChild().getLeftChild().getVar()==1);
        assert(vtree.getLeftChild().getRightChild().getVar()==2);
        assert(vtree.getRightChild().getLeftChild().getVar()==3);
        assert(vtree.getRightChild().getRightChild().getVar()==4);

    }

    @Test
    public void testRotateRight(){
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager manager = new SddManager(vtree);
        manager.useAutoGcMin(false);
        vtree = manager.getVtree();
        assert(vtree.getLeftChild().getLeftChild().getVar()==1);
        assert(vtree.getLeftChild().getRightChild().getVar()==2);
        assert(vtree.getRightChild().getLeftChild().getVar()==3);
        assert(vtree.getRightChild().getRightChild().getVar()==4);

        manager.rotateRight(vtree, false);
        vtree = manager.getVtree();
        assert(vtree.getParent()==null);

        assert(vtree.getLeftChild().getVar()==1);
        assert(vtree.getRightChild().getLeftChild().getVar()==2);
        assert(vtree.getRightChild().getRightChild().getLeftChild().getVar()==3);
        assert(vtree.getRightChild().getRightChild().getRightChild().getVar()==4);

    }

    @Test
    public void testSwap(){
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager manager = new SddManager(vtree);
        vtree = manager.getVtree();
        manager.useAutoGcMin(false);
        assert(vtree.getLeftChild().getLeftChild().getVar()==1);
        assert(vtree.getLeftChild().getRightChild().getVar()==2);
        assert(vtree.getRightChild().getLeftChild().getVar()==3);
        assert(vtree.getRightChild().getRightChild().getVar()==4);

        manager.swap(vtree, false);
        vtree = manager.getVtree();
        assert(vtree.getParent()==null);

        assert(vtree.getLeftChild().getLeftChild().getVar()==3);
        assert(vtree.getLeftChild().getRightChild().getVar()==4);
        assert(vtree.getRightChild().getLeftChild().getVar()==1);
        assert(vtree.getRightChild().getRightChild().getVar()==2);

    }

    @Test
    public void testGetSize() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(6, vtree.getSize());
        assertEquals(4, vtree.getLeftChild().getSize());
        mgr.garbageCollect();
        assertEquals(2, vtree.getSize());
        assertEquals(2, vtree.getLeftChild().getSize());
    }

    @Test
    public void testGetLiveSize() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();
        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        assertEquals(0, vtree.getLiveSize());
        sdd1.ref();
        assertEquals(2, vtree.getLiveSize());
        assertEquals(2, vtree.getLeftChild().getLiveSize());
        mgr.garbageCollect();
        assertEquals(2, vtree.getLiveSize());
        assertEquals(2, vtree.getLeftChild().getLiveSize());
    }

    @Test
    public void testGetDeadSize() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();
        assertEquals(0, vtree.getDeadSize());
        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        assertEquals(6, vtree.getDeadSize());
        sdd1.ref();
        assertEquals(4, vtree.getDeadSize());
        assertEquals(2, vtree.getLeftChild().getDeadSize());
        mgr.garbageCollect();
        assertEquals(0, vtree.getDeadSize());
        assertEquals(0, vtree.getLeftChild().getDeadSize());
    }

    @Test
    public void testGetCount() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(3, vtree.getCount());
        assertEquals(2, vtree.getLeftChild().getCount());
        mgr.garbageCollect();
        assertEquals(1, vtree.getCount());
        assertEquals(1, vtree.getLeftChild().getCount());
    }

    @Test
    public void testGetLiveCount() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();
        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        assertEquals(0, vtree.getLiveCount());
        sdd1.ref();
        assertEquals(1, vtree.getLiveCount());
        assertEquals(1, vtree.getLeftChild().getLiveCount());
        mgr.garbageCollect();
        assertEquals(1, vtree.getLiveCount());
        assertEquals(1, vtree.getLeftChild().getLiveCount());
    }

    @Test
    public void testGetDeadCount() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();
        assertEquals(0, vtree.getDeadCount());
        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        assertEquals(3, vtree.getDeadCount());
        sdd1.ref();
        assertEquals(2, vtree.getDeadCount());
        assertEquals(1, vtree.getLeftChild().getDeadCount());
        mgr.garbageCollect();
        assertEquals(0, vtree.getDeadCount());
        assertEquals(0, vtree.getLeftChild().getDeadCount());
    }

    @Test
    public void testGetSizeAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(2, vtree.getSizeAt());
        assertEquals(4, vtree.getLeftChild().getSizeAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getSizeAt());
        assertEquals(2, vtree.getLeftChild().getSizeAt());
    }

    @Test
    public void testGetLiveSizeAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(0, vtree.getLiveSizeAt());
        assertEquals(2, vtree.getLeftChild().getLiveSizeAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getLiveSizeAt());
        assertEquals(2, vtree.getLeftChild().getLiveSizeAt());
    }

	@Test
	public void testGetDeadSizeAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(2, vtree.getDeadSizeAt());
        assertEquals(2, vtree.getLeftChild().getDeadSizeAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getDeadSizeAt());
        assertEquals(0, vtree.getLeftChild().getDeadSizeAt());

	}

    @Test
    public void testGetCountAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(1, vtree.getCountAt());
        assertEquals(2, vtree.getLeftChild().getCountAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getCountAt());
        assertEquals(1, vtree.getLeftChild().getCountAt());
    }


	@Test
	public void testGetLiveCountAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(0, vtree.getLiveCountAt());
        assertEquals(1, vtree.getLeftChild().getLiveCountAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getLiveCountAt());
        assertEquals(1, vtree.getLeftChild().getLiveCountAt());
	}

	@Test
	public void testGetDeadCountAt() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        vtree = mgr.getVtree();

        Sdd sdd1 = new Sdd(2, mgr).conjoin(new Sdd(1, mgr));
        Sdd sdd = sdd1.conjoin(new Sdd(3, mgr));
        sdd1.ref();
        assertEquals(1, vtree.getDeadCountAt());
        assertEquals(1, vtree.getLeftChild().getDeadCountAt());
        mgr.garbageCollect();
        assertEquals(0, vtree.getDeadCountAt());
        assertEquals(0, vtree.getLeftChild().getDeadCountAt());

	}

	@Test
	public void testBit() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        vtree.getLeftChild().setBit(true);
        vtree.getRightChild().setBit(false);
        assertTrue(vtree.getLeftChild().getBit());
        assertFalse(vtree.getRightChild().getBit());

	}

	@Test
	public void testEqualsVtree() {
        Vtree vtree = new Vtree(4, VtreeType.Balanced);
        SddManager mgr = new SddManager(vtree);
        Vtree mgr_Vtree1 = mgr.getVtree();
        Vtree mgr_Vtree2 = mgr.getVtree();

        assertEquals(mgr_Vtree1, mgr_Vtree2);
        assertNotEquals(vtree, mgr_Vtree1);
        assertFalse(mgr_Vtree1==mgr_Vtree2);


    }

	@Test
	public void testDataStorage() {
		Vtree vtree1 = new Vtree(5, VtreeType.Balanced);
		Vtree vtree2 = new Vtree(5, VtreeType.Left);
		Vtree vtree3 = new Vtree(6, VtreeType.Right);
		Vtree vtree4 = new Vtree(6, VtreeType.Balanced);

		String data1 = "This is the first option";
		int data2 = 354;
		File data3 = new File("SDD");
		String[] data4 = { "option", "otheroption" };

		vtree1.setData(data1);
		vtree2.setData(data2);
		vtree3.setData(data3);
		vtree4.setData(data4);

		assertEquals(data1, vtree1.getData());
		assertEquals(data2, vtree2.getData());
		assertEquals(data3, vtree3.getData());
		assertEquals(data4, vtree4.getData());
	}

}
