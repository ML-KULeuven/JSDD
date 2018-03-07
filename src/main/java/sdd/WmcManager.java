package sdd;

import jni.JNITYPE_p_wmc_manager_t;
import jni.SddLib;

public class WmcManager {

	/**
	 * Creates a WMC manager for the SDD rooted at node and initializes literal
	 * weights. When log mode = false, all computations done by the manager will
	 * be in natural log-space. Literal weights are initialized to 0 in log-mode
	 * and to 1 otherwise. A number of functions are given below for passing
	 * values to, or recovering values from, a WMC manager. In log-mode, all
	 * these values are in natural logs. Finally, a WMC manager may become
	 * invalid if garbage collection or SDD minimization takes place.
	 *
	 * @param node
	 * @param logMode
	 */
	public WmcManager(Sdd node, boolean logMode) {
		wmc = SddLib.wmc_manager_new(node.getPointer(), logMode ? 1 : 0,
				node.getManager().getPointer());
	}

	private JNITYPE_p_wmc_manager_t wmc;

	/**
	 * Frees the memory of the WMC manager.
	 */
	public void free() {
		SddLib.wmc_manager_free(wmc);
	}

	/**
	 * Sets the weight of a literal in the given WMC manager (should pass the
	 * natural log of the weight in log-mode).
	 *
	 * @param literal
	 * @param weight
	 */
	public void setLiteralWeight(long literal, double weight) {
		SddLib.wmc_set_literal_weight(literal, weight, wmc);
	}

	/**
	 * Returns the weighted model count of the SDD underlying the WMC manager
	 * (using the current literal weights). This function should be called each
	 * time the weights of literals are changed.
	 *
	 * @return the weighted model count of the SDD underlying the WMC manager
	 */
	public double propagate() {
		return SddLib.wmc_propagate(wmc);
	}

	/**
	 *
	 * @return −∞ for log-mode and 0 otherwise
	 */
	public double getZeroWeight() {
		return SddLib.wmc_zero_weight(wmc);
	}

	/**
	 *
	 * @return 0 for log-mode and 1 otherwise.
	 */
	public double getOneWeight() {
		return SddLib.wmc_one_weight(wmc);
	}

	/**
	 * Returns the weight of a literal.
	 *
	 * @param literal
	 * @return the weight of a literal.
	 */
	public double getWeight(long literal) {
		return SddLib.wmc_literal_weight(literal, wmc);
	}

	/**
	 * Returns the partial derivative of the weighted model count with respect
	 * to the weight of literal. The result returned by this function is
	 * meaningful only after having called propagate().
	 *
	 * @param literal
	 * @return the partial derivative of the weighted model count
	 */
	public double getDerivative(long literal) {
		return SddLib.wmc_literal_derivative(literal, wmc);
	}

	/**
	 * Returns the probability of literal. The result returned by this function
	 * is meaningful only after having called propagate().
	 *
	 * @param literal
	 * @return the probability of literal.
	 */
	public double getProbability(long literal) {
		return SddLib.wmc_literal_pr(literal, wmc);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof WmcManager)
			return this.wmc == ((WmcManager) o).wmc;
		return false;
	}
	
	@Override
	public String toString(){
		return "WMCMANAGER " + wmc.toString();
	}

}
