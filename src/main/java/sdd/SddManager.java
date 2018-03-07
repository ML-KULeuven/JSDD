package sdd;

import helpers.Storage;
import jni.JNITYPE_p_sdd_manager_t;
import jni.JNITYPE_p_sdd_node_t;
import jni.SddLib;

import java.io.File;

public class SddManager {

	private SddManager(JNITYPE_p_sdd_manager_t manager) {
		this.manager = manager;
	}

	/**
	 * Creates a new SDD manager, given a vtree. The manager copies the input
	 * vtree. Any manipulations performed by the manager are done on its own
	 * copy, and does not affect the input vtree.
	 * 
	 * @param vtree
	 */
	public SddManager(Vtree vtree) {
		manager = SddLib.sdd_manager_new(vtree.getPointer());
	}

	/**
	 * Creates a new SDD manager using a balanced vtree over the given number of
	 * variables. Automatic garbage collection and automatic SDD minimization
	 * are activated in the created manager when autoGcMin is true
	 * 
	 * @param varCount
	 * @param autoGcMin
	 *            automatic garbage collection and minimization
	 */
	public SddManager(long varCount, boolean autoGcMin) {
		manager = SddLib.sdd_manager_create(varCount, autoGcMin ? 1 : 0);
	}

	private JNITYPE_p_sdd_manager_t manager;
	private static final Storage optionsStorage = new Storage();


	public JNITYPE_p_sdd_manager_t getPointer() {
		return manager;
	}

    /**
     * Returns a new SDD manager, while copying the vtree and all the SDD nodes of this manager to it.
     * After executing this method, the SDD nodes in the provided array will be replaced with the copies
	 * of those nodes in the new manager.
     *
     * @param nodes list of SDD nodes to copy
     * @return copy of the SDD manager
     */
	public SddManager copy(Sdd[] nodes){
		JNITYPE_p_sdd_node_t[] pointers = new JNITYPE_p_sdd_node_t[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			pointers[i] = nodes[i].getPointer();
		}
		SddManager mgr_copy = new SddManager(SddLib.sdd_manager_copy(pointers, manager));
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Sdd(pointers[i],mgr_copy);
        }
        return mgr_copy;
	}

	/**
	 * Frees the memory of an SDD manager created by new sdd manager, including
	 * all SDD nodes created using that manager.
	 */
	public void free() {
		SddLib.sdd_manager_free(manager);
	}

	/**
	 * Prints various statistics that are collected by an SDD manager.
	 */
	public void print() {
		SddLib.sdd_manager_print(manager);
	}

    /**
     * Activates/Deactivates automatic garbage collection and automatic SDD
     * minimization.
     *
     * @param autoGcMin
     */
    public void useAutoGcMin(boolean autoGcMin) {
        if (autoGcMin)
            SddLib.sdd_manager_auto_gc_and_minimize_on(manager);
        else
            SddLib.sdd_manager_auto_gc_and_minimize_off(manager);
    }

    /**
     * Returns true if automatic garbage collection and automatic SDD minimization is activated,
     * and returns false otherwise.
     */
    public boolean isAutoGcMinOn() {
        return SddLib.sdd_manager_is_auto_gc_and_minimize_on(manager) != 0;
    }

	/**
	 * Returns the options field
	 *
	 * @return options field
	 */
	public Object getOptions() {
		return optionsStorage.get((int) SddLib.sdd_manager_options(manager));
	}

	/**
	 * Sets the options field for an SDD manager. This field can be used to
	 * allocate auxiliary data to an SDD manager. The SDD library does not
	 * access this field directly
	 *
	 * @param options
	 */
	public void setOptions(Object options) {
		SddLib.sdd_manager_set_options((long) optionsStorage.store(options),
				manager);
	}

	/**
	 * Returns true if var is referenced by a decision SDD node (dead or alive);
	 * returns false otherwise.
	 *
	 * @param var
	 * @return true if var is referenced by a decision SDD node (dead or alive);
	 *         false otherwise.
	 */
	public boolean isVarUsed(long var) {
		return SddLib.sdd_manager_is_var_used(var, manager) != 0;
	}

	/**
	 * Returns the leaf node of a manager’s vtree, which is associated with var.
	 *
	 * @param var
	 * @return the leaf node of a manager’s vtree, which is associated with var.
	 */
	public Vtree getVtreeOfVar(long var) {
		return new Vtree(SddLib.sdd_manager_vtree_of_var(var, manager));
	}

	/**
	 * Returns the smallest vtree which contains the variables of literals,
	 * where count is the number of literals. If we view the variable of each
	 * literal as a leaf vtree node, the function will then return the lowest
	 * common ancestor (lca) of these leaf nodes.
	 *
	 * @param literals
	 * @return lca of literals
	 */

	public Vtree getLcaOfLiterals(long[] literals) {

		return new Vtree(SddLib.sdd_manager_lca_of_literals(literals.length,
				literals, manager));
	}

	/**
	 * Returns the number of SDD variables currently associated with the
	 * manager.
	 *
	 * @return the number of SDD variables currently associated with the
	 *         manager.
	 */
	public long getVarCount() {
		return SddLib.sdd_manager_var_count(manager);
	}

	/**
	 *
	 * @return an array with the left-to-right variable ordering of the
	 *         manager’s vtree.
	 */
	public long[] getVarOrder() {
		long[] varOrder = new long[(int) getVarCount()];
		SddLib.sdd_manager_var_order(varOrder, manager);
		return varOrder;
	}

	/**
	 * Add a new variable with index n + 1 to the manager, where n is the
	 * current number of variables in the manager. Let v be the leftmost leaf
	 * node in the vtree. A new leaf node labeled with variable n + 1 is created
	 * and made a left sibling of leaf v.
	 */
	public void addVarBeforeFirst() {
		SddLib.sdd_manager_add_var_before_first(manager);
	}

	/**
	 * Add a new variable with index n + 1 to the manager, where n is the
	 * current number of variables in the manager. Let v be the rightmost leaf
	 * node in the vtree. A new leaf node labeled with variable n + 1 is created
	 * and made a right sibling of leaf v.
	 */
	public void addVarAfterLast() {
		SddLib.sdd_manager_add_var_after_last(manager);
	}

	/**
	 * Add a new variable with index n + 1 to the manager, where n is the
	 * current number of variables in the manager. Let v be the vtree leaf node
	 * labeled with variable target var. A new leaf node labeled with variable n
	 * + 1 is created and made a left sibling of leaf v
	 */
	public void addVarBefore(long targetVar) {
		SddLib.sdd_manager_add_var_before(targetVar, manager);
	}

	/**
	 * Add a new variable with index n + 1 to the manager, where n is the
	 * current number of variables in the manager. Let v be the vtree leaf node
	 * labeled with variable target var. A new leaf node labeled with variable n
	 * + 1 is created and made a right sibling of leaf v
	 */
	public void addVarAfter(long targetVar) {
		SddLib.sdd_manager_add_var_after(targetVar, manager);
	}

	/**
	 * Add a variable. The position in the Vtree of this new variable will be
	 * before the LCA (lowest common ancestor) of the given variables.
	 *
	 * @param lcaVars The variables that are used to find the position of the new variable.
	 */
	public void addVarBeforeLca(long[] lcaVars) {
		SddLib.add_var_before_lca(lcaVars.length, lcaVars, manager);
	}

	/**
	 * Move the given variable before the first variable in the Vtree.
	 *
	 * @param var The variable to be moved.
	 */
	public void moveVarBeforeFirst(long var) {
		SddLib.move_var_before_first(var, manager);
	}


	/**
	 * Move the given variable after the last variable in the Vtree.
	 *
	 * @param var The variable to be moved.
	 */
	public void moveVarAfterLast(long var) {
		SddLib.move_var_after_last(var, manager);
	}


	/**
	 * Move the given variable before the target variable in the Vtree.
	 *
	 * @param var The variable to be moved.
	 * @param targetVar The target variable.
	 */
	public void moveVarBefore(long var, long targetVar) {
		SddLib.move_var_before(var, targetVar, manager);
	}

	/**
	 * Move the given variable after the target variable in the Vtree.
	 *
	 * @param var The variable to be moved.
	 * @param targetVar The target variable.
	 */
	public void moveVarAfter(long var, long targetVar) {
		SddLib.move_var_after(var, targetVar, manager);
	}

	/**
	 * Move the given variable before the LCA (least common ancestor)
	 * of the given variables in the Vtree.
	 *
	 * @param var The variable to be moved.
	 * @param lcaVars The variables that are used to find the new
	 * position of the moved variable.
	 */
	public void moveVarBeforeLca(long var, long[] lcaVars) {
		addVarBeforeLca(lcaVars);
		moveVarBefore(var, getVarCount());
		removeLastAddedVar();
	}

	/**
	 * Remove the variable that was last added to the manager.
	 */
	public void removeLastAddedVar(){
		SddLib.remove_var_added_last(manager);
	}

	/**
	 *
	 * @return Size of all SDD nodes in the manager.
	 */
	public long getSize() {
		return SddLib.sdd_manager_size(manager);
	}

	/**
	 *
	 * @return Size of all live SDD nodes in the manager.
	 */
	public long getLiveSize() {
		return SddLib.sdd_manager_live_size(manager);
	}

	/**
	 *
	 * @return Size of all dead SDD nodes in the manager.
	 */
	public long getDeadSize() {
		return SddLib.sdd_manager_dead_size(manager);
	}

	/**
	 *
	 * @return Count of all SDD nodes in the manager.
	 */
	public long getCount() {
		return SddLib.sdd_manager_count(manager);
	}

	/**
	 *
	 * @return Count of all live SDD nodes in the manager.
	 */
	public long getLiveCount() {
		return SddLib.sdd_manager_live_count(manager);
	}

	/**
	 *
	 * @return Count of all dead SDD nodes in the manager.
	 */
	public long getDeadCount() {
		return SddLib.sdd_manager_dead_count(manager);
	}

	/**
	 * Returns the root node of a manager’s vtree.
	 *
	 * @return the root node of a manager’s vtree.
	 */
	public Vtree getVtree() {
		return new Vtree(SddLib.sdd_manager_vtree(manager));
	}

	/**
	 * Returns a copy of the root node of a manager’s vtree.
	 *
	 * @return a copy of the root node of a manager’s vtree.
	 */
	public Vtree getVtreeCopy() {

		return new Vtree(SddLib.sdd_manager_vtree_copy(manager));
	}

	/**
	 * Saves the SDD of the manager’s vtree (a shared SDD), formatted for use
	 * with Graphviz dot.
	 *
	 * @param filename
	 */
	public void saveSharedAsDot(String filename) {
		File parent = new File(filename).getParentFile();
		if (parent!=null)
			parent.mkdirs();
		SddLib.sdd_shared_save_as_dot(filename, manager);
	}

	/**
	 * Performs a global garbage collection: Claims all dead SDD nodes in the
	 * manager.
	 */
	public void garbageCollect() {
		SddLib.sdd_manager_garbage_collect(manager);
	}

	/**
	 * Performs local garbage collection: Claims all dead SDD nodes inside or
	 * above vtree.
	 *
	 * @param vtree
	 */
	public void garbageCollect(Vtree vtree) {
		SddLib.sdd_vtree_garbage_collect(vtree.getPointer(), manager);
	}

	/**
	 * Performs a global garbage collection if the number of dead SDD nodes over
	 * the number of all SDD nodes in the manager exceeds dead node threshold.
	 *
	 * @param deadNodeThreshold
	 * @return true if garbage collection is performed; false otherwise.
	 */
	public boolean garbageCollect(float deadNodeThreshold) {
		return SddLib
				.sdd_manager_garbage_collect_if(deadNodeThreshold, manager) != 0;
	}

	/**
	 * Performs local garbage collection if the number of dead SDD nodes over
	 * the number of all SDD nodes in the vtree exceeds dead node threshold.
	 *
	 * @param deadNodeThreshold
	 * @param vtree
	 * @return true if garbage collection is performed; false otherwise.
	 */
	public boolean garbageCollect(float deadNodeThreshold, Vtree vtree) {
		return SddLib.sdd_vtree_garbage_collect_if(deadNodeThreshold,
				vtree.getPointer(), manager) != 0;
	}

    /**
     * Performs global garbage collection and then tries to minimize the size of
     * manager’s SDD.
     */
    public void minimize() {
        SddLib.sdd_manager_minimize(manager);
    }

    /**
     * Performs local garbage collection on vtree and then tries to minimize the
     * size of SDD of vtree by searching for a different vtree.
     *
     * @param vtree
     * @return the root of found vtree.
     */
    public Vtree minimize(Vtree vtree) {
        return new Vtree(SddLib.sdd_vtree_minimize(vtree.getPointer(), manager));
    }


    /**
     * Performs global garbage collection and then tries to minimize the size of
     * manager’s SDD.
     */
    public void minimizeLimited() {
        SddLib.sdd_manager_minimize_limited(manager);
    }

    /**
     * Performs local garbage collection on vtree and then tries to minimize the
     * size of SDD of vtree by searching for a different vtree.
     *
     * @param vtree
     * @return the root of found vtree.
     */
    public Vtree minimizeLimited(Vtree vtree) {
        return new Vtree(SddLib.sdd_vtree_minimize_limited(vtree.getPointer(), manager));
    }

    /**
     * Sets the threshold for terminating the passes performed by the vtree search algorithm.
     * Default value is 1.0,
     * which means that search will terminate when a pass reduces the SDD size by less than 1.0%
     *
     * @param threshold
     */
    public void setConvergenceThreshold(float threshold){
        SddLib.sdd_manager_set_vtree_search_convergence_threshold(threshold, manager);
    }

    /**
     * Set the time limit for a vtree fragment in the vtree search algorithm.
     * Time is in seconds and corresponds to CPU time.
     * Default Value is 180 seconds.
     *
     * @param time_limit
     */
    public void setVtreeSearchTimeLimit(float time_limit){
        SddLib.sdd_manager_set_vtree_search_time_limit(time_limit, manager);
    }

    /**
     * Set the time limit for a vtree fragment in the vtree search algorithm.
     * Time is in seconds and corresponds to CPU time.
     * Default Value is 60 seconds.
     *
     * @param time_limit
     */
    public void setVtreeFragmentTimeLimit(float time_limit){
        SddLib.sdd_manager_set_vtree_fragment_time_limit(time_limit, manager);
    }

    /**
     * Set the time limit for executing an operation (rotation or swap) in the vtree search algorithm.
     * Time is in seconds and corresponds to CPU time.
     * Default Value is 30 seconds.
     *
     * @param time_limit
     */
    public void setVtreeOperationTimeLimit(float time_limit){
        SddLib.sdd_manager_set_vtree_operation_time_limit(time_limit, manager);
    }

    /**
     * Set the time limit for executing an "apply" in the vtree search algorithm.
     * Time is in seconds and corresponds to CPU time.
     * Default Value is 10 seconds.
     *
     * @param time_limit
     */
    public void setVtreeApplyTimeLimit(float time_limit){
        SddLib.sdd_manager_set_vtree_apply_time_limit(time_limit, manager);
    }

    /**
     * Sets the relative memory limit for rotation and swap operations.
     * Default value is 3.0.
     *
     * @param memory_limit
     */
    public void setVtreeOperationMemoryLimit(float memory_limit){
        SddLib.sdd_manager_set_vtree_operation_memory_limit(memory_limit, manager);
    }

    /**
     * Sets the relative size limit for rotation and swap operations.
     * Default value is 1.2. A size limit l is relative to the size s of an SDD for a given
     * vtree (s is called the reference size). Hence, using this size limit requires
     * calling the following functions to set and update the reference size s,
     * otherwise the behavior is not defined.
     *
     * @param size_limit
     */
    public void setVtreeOperationSizeLimit(float size_limit){
        SddLib.sdd_manager_set_vtree_operation_size_limit(size_limit, manager);
    }

    /**
     * Sets the absolute size limit on the size of a cartesian product for right rotation and swap operations.
     * Default value is 8,192
     * @param size_limit
     */
    public void setVtreeCartesianProductLimit(long size_limit){
        SddLib.sdd_manager_set_vtree_cartesian_product_limit(size_limit, manager);
    }

	// LIMITS FOR VTREE/SDD EDIT OPERATIONS

	/**
     * Declares the size s of current SDD for vtree as the reference size for the relative size limit l.
     * That is, a rotation or swap operation will fail if the SDD size grows to larger than s × l.
	 *
	 * @param vtree
	 */
	public void initVtreeSizeLimit(Vtree vtree) {
		SddLib.sdd_manager_init_vtree_size_limit(vtree.getPointer(), manager);
	}

	/**
	 * Updates the reference size for relative size limits.
     * It is preferrable to invoke this function, as it is more efficent than the function
     * sdd manager init vtree size limit as it does not directly recompute the size of vtree.
	 */
	public void updateVtreeSizeLimit() {
		SddLib.sdd_manager_update_vtree_size_limit(manager);
	}

	/**
	 * Rotates a vtree node left.
     * This operation assumes no dead SDD nodes inside or above vtree.
     * Moreover, this operation does not introduce any new dead SDD nodes
	 *
	 * @param vtree the vtree node to rotate
	 * @param limited enforces Time and size limits, among others, if set to true
	 * @return true if the operation succeeds and false otherwise (i.e., limits
	 *         exceeded).
	 */
	public boolean rotateLeft(Vtree vtree, boolean limited) {
		return SddLib.sdd_vtree_rotate_left(vtree.getPointer(), manager,
				limited? 1:0) != 0;
	}

    /**
     * Rotates a vtree node right.
     * This operation assumes no dead SDD nodes inside or above vtree.
     * Moreover, this operation does not introduce any new dead SDD nodes
     *
     * @param vtree the vtree node to rotate
     * @param limited enforces Time and size limits, among others, if set to true
     * @return true if the operation succeeds and false otherwise (i.e., limits
     *         exceeded).
     */
    public boolean rotateRight(Vtree vtree, boolean limited) {
        return SddLib.sdd_vtree_rotate_right(vtree.getPointer(), manager,
                limited?1:0) != 0;
    }

    /**
     * Swaps a vtree node.
     * This operation assumes no dead SDD nodes inside or above vtree.
     * Moreover, this operation does not introduce any new dead SDD nodes
     *
     * @param vtree the vtree node to swap
     * @param limited enforces Time and size limits, among others, if set to true
     * @return true if the operation succeeds and false otherwise (i.e., limits
     *         exceeded).
     */
    public boolean swap(Vtree vtree, boolean limited) {
        return SddLib.sdd_vtree_swap(vtree.getPointer(), manager,
                limited?1:0) != 0;
    }

	public void refGcDeref(Sdd[] nodes){
            SddLib.sdd_ref_gc_deref(manager,nodes);
	}

	public void ref(Sdd[] nodes){
            SddLib.sdd_ref_multi(manager,nodes);
	}

	public void deref(Sdd[] nodes){
            SddLib.sdd_deref_multi(manager,nodes);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SddManager)
			return this.manager.equals(((SddManager) o).manager);
		return false;
	}

	@Override
	public int hashCode() {
		return manager.hashCode();
	}
	
	@Override
	public String toString(){
		return "SDDMANAGER " + manager.toString();
	}

}
