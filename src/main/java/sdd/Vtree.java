package sdd;

import helpers.Storage;
import jni.JNITYPE_p_vtree_t;
import jni.SddLib;

import java.io.File;

public class Vtree {

	JNITYPE_p_vtree_t vtree;
	private static final Storage dataStorage = new Storage();
	private static final Storage stateStorage = new Storage();

	public Vtree(JNITYPE_p_vtree_t vtree) {
		this.vtree = vtree;
	}

	/**
	 * Creates a vtree of the given type over a given number of variables (var
	 * count).
	 *
	 * @param varCount
	 * @param type
	 */
	public Vtree(long varCount, VtreeType type) {
		vtree = SddLib.sdd_vtree_new(varCount, type.toString());
	}

    /**
     * Creates a vtree of the given type whose left-to-right variable ordering
     * is given in array varOrder. The contents of array varOrder must be a
     * permutation of the integers from 1 to varOrder.length.
     *
     * @param varOrder
     * @param type
     */
    public Vtree(long[] varOrder, VtreeType type) {
        vtree = SddLib.sdd_vtree_new_with_var_order(varOrder.length, varOrder,
                type.toString());
    }

    /**
     * Creates an X-constrained vtree over a given number of variables (var count). The input is X var is an
     array of size var count + 1 specifying variables X. For variables i where 1 ≤ i ≤ var count, if is X var[i]
     is 1 then i ∈ X, and if it is 0 then i 6∈ X. The type of a vtree may be "right" (right linear), "left" (left
     linear), "vertical", "balanced", or "random".

     * Creates a vtree of the given type whose left-to-right variable ordering
     * is given in array varOrder. The contents of array varOrder must be a
     * permutation of the integers from 1 to varOrder.length.
     *
     * @param varCount
     * @param xConstrained
     * @param type
     */
    public Vtree(int varCount,  long[] xConstrained, VtreeType type) {
        long[] xConstrained2 = new long[varCount+1];
        for (int i = 0; i<varCount+1; i++) xConstrained2[i]=0;
        for (long i : xConstrained) xConstrained2[(int)i]=1;
        vtree = SddLib.sdd_vtree_new_X_constrained(varCount, xConstrained2,
                type.toString());
    }


	public JNITYPE_p_vtree_t getPointer() {
		return vtree;
	}

	/**
	 * Frees the memory of a vtree.
	 */
	public void free() {
		SddLib.sdd_vtree_free(vtree);
	}

	/**
	 * Reads a vtree from file.
	 *
	 * @param filename
	 * @return vtree, read from file
	 */
	public static Vtree read(String filename) {
		if (!(new File(filename)).exists())
			throw new IllegalArgumentException("file " + filename
					+ " does not exist.");
		return new Vtree(SddLib.sdd_vtree_read(filename));
	}

	/**
	 * Saves a vtree to file.
	 *
	 * @param filename
	 */
	public void save(String filename) {
		File parent = new File(filename).getParentFile();
		if (parent!=null)
			parent.mkdirs();
		SddLib.sdd_vtree_save(filename, vtree);
	}

	/**
	 * Saves a vtree to file, formatted for use with Graphviz dot.
	 *
	 * @param filename
	 */
	public void saveAsDot(String filename) {
		File parent = new File(filename).getParentFile();
		if (parent!=null)
			parent.mkdirs();
		SddLib.sdd_vtree_save_as_dot(filename, vtree);
	}

	/**
	 * @return the left child of a vtree node (returns null if the vtree is a
	 *         leaf node).
	 */
	public Vtree getLeftChild() {
		JNITYPE_p_vtree_t child = SddLib.sdd_vtree_left(vtree);
		return child == null ? null : new Vtree(child);
	}

	/**
	 *
	 * @return the right child of a vtree node (returns null if the vtree is a
	 *         leaf node).
	 */
	public Vtree getRightChild() {
		JNITYPE_p_vtree_t child = SddLib.sdd_vtree_right(vtree);
		return child == null ? null : new Vtree(child);
	}

	/**
	 *
	 * @return the parent of a vtree node (returns null if the vtree is a root
	 *         node).
	 */
	public Vtree getParent() {
		JNITYPE_p_vtree_t parent = SddLib.sdd_vtree_parent(vtree);
		return parent == null ? null : new Vtree(parent);
	}

	public boolean isLeaf() {
		return SddLib.sdd_vtree_is_leaf(vtree) != 0;
	}

	/**
	 *
	 * @return true if this vtree is a sub-vtree other and false otherwise.
	 */
	public boolean isSub(Vtree other) {
		return SddLib.sdd_vtree_is_sub(vtree, other.getPointer()) != 0;
	}

	/**
	 *
	 * @return the lowest common ancestor (lca) of vtree nodes vtree1 and
	 *         vtree2, assuming that this vtree is a common ancestor of these
	 *         two nodes.
	 */
	public Vtree getLca(Vtree vtree1, Vtree vtree2) {
		return new Vtree(SddLib.sdd_vtree_lca(vtree1.getPointer(),
				vtree2.getPointer(), vtree));
	}

	/**
	 *
	 * @return the number of variables contained in the vtree.
	 */
	public long getVarCount() {
		return SddLib.sdd_vtree_var_count(vtree);
	}

	/**
	 *
	 * @return the variable associated with a vtree node, if the vtree node is a
	 *         leaf, and returns 0 otherwise.
	 */
	public long getVar() {
		return SddLib.sdd_vtree_var(vtree);
	}

	/**
	 *
	 * @return the position of a given vtree node in the vtree inorder. Position
	 *         indices start at 0
	 */
	public long getPosition() {
		return SddLib.sdd_vtree_position(vtree);
	}

	/**
	 *
	 * @return the size of all SDD nodes in the vtree.
	 */
	public long getSize() {
		return SddLib.sdd_vtree_size(vtree);
	}

	/**
	 *
	 * @return the size of all live SDD nodes in the vtree.
	 */
	public long getLiveSize() {
		return SddLib.sdd_vtree_live_size(vtree);
	}

	/**
	 *
	 * @return the size of all dead SDD nodes in the vtree.
	 */
	public long getDeadSize() {
		return SddLib.sdd_vtree_dead_size(vtree);
	}

	/**
	 *
	 * @return the node count of all SDD nodes in the vtree.
	 */
	public long getCount() {
		return SddLib.sdd_vtree_count(vtree);
	}

	/**
	 *
	 * @return the node count of all live SDD nodes in the vtree.
	 */
	public long getLiveCount() {
		return SddLib.sdd_vtree_live_count(vtree);
	}

	/**
	 *
	 * @return the node count of all dead SDD nodes in the vtree.
	 */
	public long getDeadCount() {
		return SddLib.sdd_vtree_dead_count(vtree);
	}

	/**
	 * Returns the size of all SDD nodes normalized for a vtree node. For
	 * example, getSize(vtree) returns the sum of getSizeAt(v) where v ranges
	 * over all nodes of vtree.
	 *
	 * @return the size of all SDD nodes normalized for a vtree node.
	 */
	public long getSizeAt() {
		return SddLib.sdd_vtree_size_at(vtree);
	}

	/**
	 * Returns the size of all live SDD nodes normalized for a vtree node. For
	 * example, getLiveSize(vtree) returns the sum of getLiveSizeAt(v) where v
	 * ranges over all live nodes of vtree.
	 *
	 * @return the size of all live SDD nodes normalized for a vtree node.
	 */
	public long getLiveSizeAt() {
		return SddLib.sdd_vtree_live_size_at(vtree);
	}

	/**
	 * Returns the size of all dead SDD nodes normalized for a vtree node. For
	 * example, getDeadSize(vtree) returns the sum of getDeadSizeAt(v) where v
	 * ranges over all dead nodes of vtree.
	 *
	 * @return the size of all dead SDD nodes normalized for a vtree node.
	 */
	public long getDeadSizeAt() {
		return SddLib.sdd_vtree_dead_size_at(vtree);
	}



	/**
	 * Returns the size of all SDD nodes normalized for a vtree node that is
	 * an ancestor of the given vtree node (not including those of the vtree node itself). For
	 * example, getSize(vtree) returns the sum of getSizeAt(v) where v ranges
	 * over all nodes of vtree.
	 *
	 * @return the size of all SDD nodes normalized for a vtree node.
	 */
	public long getSizeAbove() {
		return SddLib.sdd_vtree_size_above(vtree);
	}

	/**
	 * Returns the size of all live SDD nodes normalized for a vtree node that is
	 * an ancestor of the given vtree node (not including those of the vtree node itself). For
	 * example, getLiveSize(vtree) returns the sum of getLiveSizeAt(v) where v
	 * ranges over all live nodes of vtree.
	 *
	 * @return the size of all live SDD nodes normalized for a vtree node.
	 */
	public long getLiveSizeAbove() {
		return SddLib.sdd_vtree_live_size_above(vtree);
	}

	/**
	 * Returns the size of all dead SDD nodes normalized for a vtree node that is an
	 * ancestor of the given vtree node (not including those of the vtree node itself). For
	 * example, getDeadSize(vtree) returns the sum of getDeadSizeAt(v) where v
	 * ranges over all dead nodes of vtree.
	 *
	 * @return the size of all dead SDD nodes normalized for a vtree node.
	 */
	public long getDeadSizeAbove() {
		return SddLib.sdd_vtree_dead_size_above(vtree);
	}


	/**
	 * Returns the node count of all SDD nodes normalized for a vtree node. For
	 * example, getCount(vtree) returns the sum of getCountAt(v) where v ranges
	 * over all nodes of vtree.
	 *
	 * @return the node count of all SDD nodes normalized for a vtree node.
	 */
	public long getCountAt() {
		return SddLib.sdd_vtree_count_at(vtree);
	}

	/**
	 * Returns the node count of all live SDD nodes normalized for a vtree node.
	 * For example, getLiveCount(vtree) returns the sum of getLiveCountAt(v)
	 * where v ranges over all live nodes of vtree.
	 *
	 * @return the node count of all live SDD nodes normalized for a vtree node.
	 */
	public long getLiveCountAt() {
		return SddLib.sdd_vtree_live_count_at(vtree);
	}

	/**
	 * Returns the node count of all dead SDD nodes normalized for a vtree node.
	 * For example, getDeadCount(vtree) returns the sum of getDeadCountAt(v)
	 * where v ranges over all dead nodes of vtree.
	 *
	 * @return the node count of all dead SDD nodes normalized for a vtree node.
	 */
	public long getDeadCountAt() {
		return SddLib.sdd_vtree_dead_count_at(vtree);
	}


	/**
	 * Returns the node count of all SDD nodes normalized for a vtree node that is an
	 * ancestor of the given vtree node (not including those of the vtree node itself). For
	 * example, getCount(vtree) returns the sum of getCountAt(v) where v ranges
	 * over all nodes of vtree.
	 *
	 * @return the node count of all SDD nodes normalized for a vtree node.
	 */
	public long getCountAbove() {
		return SddLib.sdd_vtree_count_above(vtree);
	}

	/**
	 * Returns the node count of all live SDD nodes normalized for a vtree node that is an
	 * ancestor of the given vtree node (not including those of the vtree node itself).
	 * For example, getLiveCount(vtree) returns the sum of getLiveCountAt(v)
	 * where v ranges over all live nodes of vtree.
	 *
	 * @return the node count of all live SDD nodes normalized for a vtree node.
	 */
	public long getLiveCountAbove() {
		return SddLib.sdd_vtree_live_count_above(vtree);
	}

	/**
	 * Returns the node count of all dead SDD nodes normalized for a vtree node that is an
	 * ancestor of the given vtree node (not including those of the vtree node itself).
	 * For example, getDeadCount(vtree) returns the sum of getDeadCountAt(v)
	 * where v ranges over all dead nodes of vtree.
	 *
	 * @return the node count of all dead SDD nodes normalized for a vtree node.
	 */
	public long getDeadCountAbove() {
		return SddLib.sdd_vtree_dead_count_above(vtree);
	}

	/**
	 *
	 * @return the bit flag.
	 */
	public boolean getBit() {
		return SddLib.sdd_vtree_bit(vtree) != 0;
	}

	/**
	 * Sets the bit flag. Bit flags are initialized to false, and as a general
	 * rule, they should be reset to false when flags are not being used.
	 *
	 * @param bit
	 */
	public void setBit(boolean bit) {
		SddLib.sdd_vtree_set_bit(bit ? 1 : 0, vtree);
	}

	/**
	 * Returns the data field
	 *
	 * @return data field
	 */
	public Object getData() {
		return dataStorage.get((int) SddLib.sdd_vtree_data(vtree));
	}

	/**
	 * Sets the data field for an SDD manager. This field can be used to
	 * allocate auxiliary data to an SDD manager. The SDD library does not
	 * access this field directly
	 *
	 * @param data
	 */
	public void setData(Object data) {
		SddLib.sdd_vtree_set_data((long) dataStorage.store(data), vtree);
	}


	@Override
	public boolean equals(Object o) {
		return o instanceof Vtree && this.vtree.equals(((Vtree) o).vtree);
	}

	@Override
	public String toString() {
		return "VTREE " + vtree.toString();
	}

	/**
	 * Returns a string with the Vtree structure.
	 *
	 * @return visualization
	 */
	public String getVisualization() {
		if (isLeaf()) {
			return "" + getVar();
		}
		return "(" + getLeftChild().getVisualization() + " "
				+ getRightChild().getVisualization() + ")";
	}

	/**
	 * Returns a set with the variables that are in this Vtree.
	 *
	 * @return a set with the variables that are in this Vtree.
	 */
	public long[] getVars(){
		if (this.isLeaf()){
			return new long[]{getVar()};
		}
		long[] leftVars = getLeftChild().getVars();
		long[] rightVars = getRightChild().getVars();

		long[] vars = new long[leftVars.length+rightVars.length];

		int i = 0;
		for (long v : leftVars){
			vars[i] = v;
			i++;
		}
		for (long v : rightVars){
			vars[i] = v;
			i++;
		}
		return vars;

	}
}
