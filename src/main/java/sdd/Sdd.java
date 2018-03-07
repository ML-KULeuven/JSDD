package sdd;

import jni.JNITYPE_p_sdd_node_t;
import jni.JNITYPE_p_wmc_manager_t;
import jni.SddLib;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Sdd {

	public Sdd(JNITYPE_p_sdd_node_t sdd, SddManager manager) {
		this.sddNode = sdd;
		this.manager = manager;
		this.id = SddLib.sdd_id(sddNode);
	}



	/**
	 * Creates an SDD representing a literal. The variable literal is of the
	 * form ±i, where i is an index of a variable, which ranges from 1 to the
	 * number of variables in the manager. If literal is positive, then the SDD
	 * representing the positive literal of the i-th variable is returned. If
	 * literal is negative, then the SDD representing the negative literal is
	 * returned.
	 *
	 * @param literal
	 * @param manager
	 */
	public Sdd(long literal, SddManager manager) {
		this.manager = manager;
		sddNode = SddLib.sdd_manager_literal(literal, manager.getPointer());
		this.id = SddLib.sdd_id(sddNode);
	}

	/**
	 * Creates an SDD representing the function val.
	 *
	 * @param val
	 * @param manager
	 */
	public Sdd(boolean val, SddManager manager) {
		this.manager = manager;
		sddNode = val ? SddLib.sdd_manager_true(manager.getPointer()) : SddLib
				.sdd_manager_false(manager.getPointer());
		this.id = SddLib.sdd_id(sddNode);
	}

	private JNITYPE_p_sdd_node_t sddNode;
	private SddManager manager;
	private int javaRefCount = 0;
	private long id;

	public static final short CONJOIN = 0;
	public static final short DISJOIN = 1;

	public JNITYPE_p_sdd_node_t getPointer() {
		return sddNode;
	}

	public SddManager getManager() {
		return manager;
	}

	/**
	 * Applies the given operation to this and the other SDD.
	 *
	 * @param other
	 * @param operation
	 *            the operation to apply
	 * @return an SDD the represents the result of applying the given operation
	 *         to this and the other SDD.
	 */
	public Sdd apply(Sdd other, short operation) {
		return new Sdd(SddLib.sdd_apply(this.sddNode, other.sddNode, operation,
				manager.getPointer()), manager);
	}

//	/**
//	 * Applies the given operation to this and the other SDD. The two SDD nodes
//	 * passed as arguments must be inside vtree. This operation can be more
//	 * efficient than sdd apply.
//	 *
//	 * @param other
//	 * @param operation
//	 *            the operation to apply
//	 * @param vtree
//	 * @return an SDD the represents the result of applying the given operation
//	 *         to this and the other SDD.
//	 */
//	public Sdd applyInVtree(Sdd other, short operation, Vtree vtree) {
//		return new Sdd(SddLib.sdd_apply_in_vtree(this.sddNode, other.sddNode,
//				operation, vtree.getPointer(), manager.getPointer()), manager);
//	}

	/**
	 * Returns an SDD that represents the conjunction of this SDD with the other
	 * SDD
	 *
	 * @param other
	 * @return an SDD that represents the conjunction of this SDD with the other
	 *         SDD
	 */
	public Sdd conjoin(Sdd other) {
		return new Sdd(SddLib.sdd_conjoin(sddNode, other.sddNode,
				manager.getPointer()), manager);
	}

	/**
	 * Returns an SDD that represents the disjunction of this SDD with the other
	 * SDD
	 *
	 * @param other
	 * @return an SDD that represents the disjunction of this SDD with the other
	 *         SDD
	 */
	public Sdd disjoin(Sdd other) {
		return new Sdd(SddLib.sdd_disjoin(sddNode, other.sddNode,
				manager.getPointer()), manager);
	}

	/**
	 * Returns an SDD that represents the negation of this SDD.
	 */
	public Sdd negate() {
		return new Sdd(SddLib.sdd_negate(sddNode, manager.getPointer()),
				manager);
	}

	/**
	 * Returns the result of conditioning an SDD on a literal, where a literal
	 * is a positive or negative integer.
	 *
	 * @param literal
	 * @return the result of conditioning an SDD on a literal, where a literal
	 *         is a positive or negative integer.
	 */
	public Sdd condition(long literal) {
		return new Sdd(SddLib.sdd_condition(literal, sddNode,
				manager.getPointer()), manager);
	}

	/**
	 * Returns the result of conditioning an SDD on a set of literals, where a literal
	 * is a positive or negative integer.
	 *
	 * @param literals
	 * @return the result of conditioning an SDD on a literal, where a literal
	 *         is a positive or negative integer.
	 */
	public Sdd condition(long[] literals) {
		jni.JNITYPE_p_sdd_node_t res = sddNode;
		for (long literal: literals) {
			res = SddLib.sdd_condition(literal, res,
					manager.getPointer());
		}
		return new Sdd(res,manager);
	}

	/**
	 * Returns the result of existentially quantifying out a variable from an
	 * SDD
	 *
	 * @param literal
	 * @return the result of existentially quantifying out a variable from an
	 *         SDD
	 */
	public Sdd exists(long literal) {
		return new Sdd(
				SddLib.sdd_exists(literal, sddNode, manager.getPointer()),
				manager);
	}



	/**
	 * This existentially quantifies a set of variables at once, so can also be
	 * used for projection
	 *
	 * @param exists_map is an array of length n+1 where exists_map[x] = true iff x
	 *         is to be quantified out
	 * @return the result of existentially quantifying out a set of variables
	 *         from an SDD
	 */
	public Sdd exists(boolean[] exists_map) {
		int[] int_exist_map = new int[exists_map.length];
		for (int i = 0; i < exists_map.length; i++) {
			int_exist_map[i] = exists_map[i] ? 1 : 0;
		}

		return new Sdd(
				SddLib.sdd_exists_multiple(int_exist_map, sddNode, manager.getPointer()),
				manager);
	}

	/**
	 * This is the same as 'exists', except that SDD minimization is never performed when
	 * quantifying out variables. This can be more efficient than deactivating automatic
	 * SDD minimization and calling 'exists'.
	 *
	 * @param exists_map is an array of length n+1 where exists_map[x] = true iff x
	 *         is to be quantified out
	 * @return the result of existentially quantifying out a set of variables
	 *         from an SDD
	 */
	public Sdd existsStatic(boolean[] exists_map) {
		int[] int_exist_map = new int[exists_map.length];
		for (int i = 0; i < exists_map.length; i++) {
			int_exist_map[i] = exists_map[i] ? 1 : 0;
		}

		return new Sdd(
				SddLib.sdd_exists_multiple_static(int_exist_map, sddNode, manager.getPointer()),
				manager);
	}



	/**
	 * This projects an SDD on a set of variables.
	 *
	 * @param projectVars is an array of variables on which the SDD is projected
	 * @return the result of projecting the SDD on a set of variables
	 */
	public Sdd project(long[] projectVars) {
		int[] int_exist_map = new int[((int) manager.getVarCount())+1];
		for (int i = 0; i < int_exist_map.length; i++)
			int_exist_map[i] = 1;
		for (long v: projectVars){
			int_exist_map[((int) v)] = 0;
		}
		return new Sdd(
				SddLib.sdd_exists_multiple(int_exist_map, sddNode, manager.getPointer()),
				manager);
	}


	/**
	 * Returns the result of universally quantifying out a variable from an SDD
	 *
	 * @param literal
	 * @return the result of universally quantifying out a variable from an SDD
	 */
	public Sdd forall(long literal) {
		return new Sdd(
				SddLib.sdd_forall(literal, sddNode, manager.getPointer()),
				manager);
	}

    /**
     *
     * @return Returns the SDD whose models are the minimum-cardinality models
     * of the given SDD (i.e. with respect to the SDD variables).
     */
    public Sdd minimizeCardinality() {
        return new Sdd(SddLib.sdd_minimize_cardinality(sddNode,
                manager.getPointer()), manager);
    }
    /**
     *
     * @return Returns the SDD whose models are the minimum-cardinality global models
     * of the given SDD (i.e., with respect to the manager variables).
     */
    public Sdd globalMinimizeCardinality() {
        return new Sdd(SddLib.sdd_global_minimize_cardinality(sddNode,
                manager.getPointer()), manager);
    }

	/**
	 *
	 * @return the minimum cardinality of an SDD: the smallest cardinality
	 *         attained by any of its models.
	 */
	public long minimumCardinality() {
		return SddLib.sdd_minimum_cardinality(sddNode);
	}

    /**
     *
     * @return the model count of an SDD (i.e., with respect to the SDD variables).
     */
    public long ModelCount() {
        return SddLib.sdd_model_count(sddNode, manager.getPointer())
                .longValue();
    }

    /**
     *
     * @return the global model count of an SDD (i.e., with respect to the manager variables).
     */
    public long GlobalModelCount() {
        return SddLib.sdd_global_model_count(sddNode, manager.getPointer())
                .longValue();
    }

	/**
	 *
	 * @return the model count of an SDD, conditioned on some literals.
	 */
	public long ModelCount(long[] conditions) {

		JNITYPE_p_wmc_manager_t wmc = SddLib.wmc_manager_new(sddNode, 0, manager.getPointer());

		for (long lit: conditions) {
			SddLib.wmc_set_literal_weight(-lit, SddLib.wmc_zero_weight(wmc),wmc);
		}
		long count = Math.round(SddLib.wmc_propagate(wmc));

		SddLib.wmc_manager_free(wmc);
		return count;
	}

	/**
	 *
	 * @return true iff this node is True
	 */
	public boolean isTrue() {
		return SddLib.sdd_node_is_true(sddNode) != 0;
	}

	/**
	 *
	 * @return true iff this node is False
	 */
	public boolean isFalse() {
		return SddLib.sdd_node_is_false(sddNode) != 0;
	}

	/**
	 *
	 * @return true iff this node a literal node
	 */
	public boolean isLiteral() {
		return SddLib.sdd_node_is_literal(sddNode) != 0;
	}

	/**
	 *
	 * @return true iff this node is a decision node
	 */
	public boolean isDecision() {
		return SddLib.sdd_node_is_decision(sddNode) != 0;
	}

	/**
	 *
	 * @return the size of an SDD node. Recall that the size is zero for
	 *         terminal nodes (i.e., non-decision nodes).
	 */
	public long getNodeSize() {
		return SddLib.sdd_node_size(sddNode);
	}

	public long getLiteral() {
		return SddLib.sdd_node_literal(sddNode);
	}

	/**
	 * Returns an array containing the elements of an SDD node. If the node has
	 * m elements, the array will be of size 2m, with primes appearing at
	 * locations 0, 2, . . . , 2m − 2 and their corresponding subs appearing at
	 * locations 1, 3, . . . , 2m − 1.
	 *
	 * @return an array containing the elements of an SDD node
	 */
	public Sdd[] getElements() {
		JNITYPE_p_sdd_node_t[] pointers = SddLib.sdd_node_elements(sddNode);
		Sdd[] elements = new Sdd[pointers.length];
		for (int i = 0; i < pointers.length; i++) {
			elements[i] = new Sdd(pointers[i], manager);
		}
		return elements;
	}

	/**
	 * Sets the bit flag for an SDD node. Bit flags are initialized to false,
	 * and as a general rule, they should be reset to false when flags are not
	 * being used.
	 *
	 * @param bit
	 */
	public void setBit(boolean bit) {
		SddLib.sdd_node_set_bit(bit ? 1 : 0, sddNode);
	}

	/**
	 *
	 * @return the bit flag of an SDD node.
	 */
	public boolean getBit() {
		return SddLib.sdd_node_bit(sddNode) != 0;
	}

	/**
	 *
	 * @return the node count of an SDD (rooted at node).
	 */
	public long getCount() {
		return SddLib.sdd_count(sddNode);
	}

	/**
	 *
	 * @return the size of an SDD (rooted at node).
	 */
	public long getSize() {
		return SddLib.sdd_size(sddNode);
	}

	/**
	 *
	 * @param nodes
	 * @return the size of a shared SDD: nodes contains the SDD roots
	 */
	public static long sharedSize(Sdd[] nodes) {
		JNITYPE_p_sdd_node_t[] pointers = new JNITYPE_p_sdd_node_t[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			pointers[i] = nodes[i].getPointer();
		}
		return SddLib.sdd_shared_size(pointers, pointers.length);
	}

	/**
	 *
	 * @return the reference count. The reference count of a terminal SDD is 0
	 *         (terminal SDDs are always live).
	 */
	public long getRefCount() {
		return SddLib.sdd_ref_count(sddNode);
	}

	/**
	 * References an SDD node if it is not a terminal node.
	 */
	public void ref() {
		++javaRefCount;
		SddLib.sdd_ref(sddNode, manager.getPointer());
	}

	/**
	 * Dereferences an SDD node if it is not a terminal node.
	 */
	public void deref() {
		--javaRefCount;
		SddLib.sdd_deref(sddNode, manager.getPointer());
	}

	/**
	 *
	 * @return the ID of SDD node. This function may be helpful for debugging.
	 */
	public long getId() {
		return id;
	}

	/**
	 *
	 * @param sddId
	 * @return true if the SDD node with the given ID has been garbage
	 *         collected; returns false otherwise. This function may be helpful
	 *         for debugging.
	 */
	public boolean isGarbageCollected(int sddId) {
		return SddLib.sdd_garbage_collected(sddNode, sddId) != 0;
	}

	/**
	 * Returns the vtree that an SDD node is normalized for.
	 *
	 * @return the vtree that an SDD node is normalized for.
	 */
	public Vtree getVtree() {
		return new Vtree(SddLib.sdd_vtree_of(sddNode));
	}

	/**
	 * Returns a copy of this SDD, with respect to a new manager
	 * destinationManager. The destination manager, and the manager associated
	 * with the SDD to be copied, must have copies of the same vtree.
	 *
	 * @param destinationManager
	 * @return a copy of this SDD, with respect to a new manager
	 *         destinationManager
	 */
	public Sdd getCopy(SddManager destinationManager) {
		return new Sdd(
				SddLib.sdd_copy(sddNode, destinationManager.getPointer()),
				destinationManager);
	}

	/**
	 * Returns an SDD which is obtained by renaming variables in the SDD node.
	 * The array variable map has size n + 1, where n is the number of variables
	 * in the manager. A variable i, 1 ≤ i ≤ n, that appears in the given SDD is
	 * renamed into variable variable map[i] (variable map[0] is not used).
	 *
	 * @param variableMap
	 * @return an SDD which is obtained by renaming variables in the SDD node.
	 */
	public Sdd renameVariables(long[] variableMap) {
		return new Sdd(SddLib.sdd_rename_variables(sddNode, variableMap,
				manager.getPointer()), manager);
	}

	/**
	 * Returns an array whose size is n + 1, where n is the number of variables
	 * in the manager. For each variable i, 1 ≤ i ≤ n, array[i] is true if
	 * variable i appears in the SDD node; otherwise, array[i] is false.
	 * (array[0] is not used)
	 *
	 * @return an array which indicates which variabels are used
	 */
	public boolean[] getVariables() {
		int[] ivars = SddLib.sdd_variables(sddNode, manager.getPointer());
		boolean[] bvars = new boolean[ivars.length];
		for (int i = 0; i < ivars.length; i++)
			bvars[i] = ivars[i] != 0;
		return bvars;
	}

	/**
	 * Reads an SDD from file. The read SDD is guaranteed to be equivalent to
	 * the one in the file, but may have a different structure depending on the
	 * current vtree of the manager. SDD minimization will not be performed when
	 * reading an SDD from file, even if auto SDD minimization is active.
	 *
	 * @param filename
	 * @param manager
	 * @return the sdd that was read from file
	 */
	public static Sdd read(String filename, SddManager manager) {
		if (!(new File(filename)).exists())
			throw new IllegalArgumentException("file " + filename
					+ " does not exist.");
		return new Sdd(SddLib.sdd_read(filename, manager.getPointer()), manager);
	}

	/**
	 * Saves an SDD to file. Typically, one also saves the corresponding vtree
	 * to file. This allows one to read the SDD back using the same vtree.
	 *
	 * @param filename
	 */
	public void save(String filename) {
		File parent = new File(filename).getParentFile();
		if (parent!=null)
			parent.mkdirs();
		SddLib.sdd_save(filename, sddNode);
	}

	/**
	 * Saves an SDD to file, formatted for use with Graphviz dot.
	 *
	 * @param filename
	 */
	public void saveAsDot(String filename) {
		File parent = new File(filename).getParentFile();
		if (parent!=null)
			parent.mkdirs();
		SddLib.sdd_save_as_dot(filename, sddNode);
	}

	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Sdd)
			return this.manager.equals(((Sdd) o).manager) && this.id == ((Sdd) o).id;
		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (javaRefCount > 0) {
			System.err.println("WARNING: Memory leak");
			System.err.println("SDD object " + this + " with reference count "
					+ javaRefCount + " is being gc'ed.");
		}
	}

	@Override
	public String toString() {
		return "SDD " + sddNode.toString();
	}

	/**
	 * Returns a string with the SDD structure.
	 *
	 * @return visualization
	 */
	public String getVisualization() {
		if (isFalse()) return "F";
		if (isTrue())  return "T";
		if (isLiteral()) return getLiteral()+"";

		Sdd[] nodes = getElements();
		String vis = "(";
		for (int i=0; i<nodes.length; i+=2){
			vis += "["+nodes[i].getVisualization()+" "+nodes[i+1].getVisualization()+"]";
		}
		vis+=")";
		return vis;
	}

	/**
	 * Returns a set with the variables that are in this SDD.
	 *
	 * @return a set with the variables that are in this SDD.
	 */
	public Set<Long> getUsedVariables() {
		HashSet<Long> usedVars = new HashSet<Long>();
		boolean[] vars = getVariables();
		for (int i=0; i<vars.length; i++){
			if (vars[i]) usedVars.add(new Long(i));
		}
		return usedVars;
	}

	public int getNbUsedVariables() {
		int nbUsedVars = 0;
		boolean[] vars = getVariables();
		for (int i=0; i<vars.length; i++){
			if (vars[i]) nbUsedVars++;
		}
		return nbUsedVars;
	}
}
