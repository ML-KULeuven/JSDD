package examples;

import sdd.Sdd;
import sdd.SddManager;

public class CircuitGC {
	// returns an SDD node representing ( node1 => node2 )
	public static Sdd imply(Sdd node1, Sdd node2) {
		Sdd negNode1 = node1.negate();
		negNode1.ref();
		Sdd alpha = negNode1.disjoin(node2);
		negNode1.deref();
		return alpha;
	}
	// returns an SDD node representing ( node1 <=> node2 )
	public static Sdd equiv(Sdd node1, Sdd node2) {
		Sdd imply1 = imply(node1, node2);
		imply1.ref();
		Sdd imply2 = imply(node2, node1);
		imply2.ref();
		Sdd alpha = imply1.conjoin(imply2);
		imply1.deref(); imply2.deref();
		return alpha;
	}
	
	public static void main(String[] args) {

		// set up vtree and manager
		long varCount = 5;
		boolean autoGcMin = true;
		SddManager m = new SddManager(varCount, autoGcMin);

		long a = 1, b = 2, c = 3, faulty1 = 4, faulty2 = 5;
		Sdd delta = new Sdd(true, m);
		Sdd alpha;
		Sdd tmp;

		////////// CONSTRUCT KNOWLEDGE BASE //////////

		// ~faulty1 => ( A <=> ~B )
		alpha = equiv(new Sdd(a, m), new Sdd(-b, m));
		alpha.ref();
		tmp=alpha;
		alpha = imply(new Sdd(-faulty1, m), alpha);
		alpha.ref(); tmp.deref();
		tmp = delta;
		delta = delta.conjoin(alpha);
		delta.ref(); alpha.deref(); tmp.deref();

		// faulty1 => ( ( A <=> B ) v ~B )
		alpha = equiv(new Sdd(a, m), new Sdd(b, m));
		alpha.ref();
		tmp = alpha;
		alpha = alpha.disjoin(new Sdd(-b, m));
		alpha.ref(); tmp.deref();
		tmp = alpha;
		alpha = imply(new Sdd(faulty1, m), alpha);
		alpha.ref(); tmp.deref();
		tmp = delta;
		delta = delta.conjoin(alpha);
		delta.ref(); alpha.deref(); tmp.deref();

		// ~faulty2 => ( B <=> ~C )
		alpha = equiv(new Sdd(b, m), new Sdd(-c, m));
		alpha.ref();
		tmp = alpha;
		alpha = imply(new Sdd(-faulty2, m), alpha);
		alpha.ref(); tmp.deref();
		tmp = delta;
		delta = delta.conjoin(alpha);
		delta.ref(); alpha.deref(); tmp.deref();
		
		// faulty2 => ( ( B <=> C ) v ~C )
		alpha = equiv(new Sdd(b, m), new Sdd(-c, m));
		alpha.ref();
		tmp = alpha;
		alpha = alpha.disjoin(new Sdd(-c, m));
		alpha.ref(); tmp.deref();
		tmp = alpha;
		alpha = imply(new Sdd(faulty2, m), alpha);
		alpha.ref(); tmp.deref();
		tmp = delta;
		delta = delta.conjoin(alpha);
		delta.ref(); alpha.deref(); tmp.deref();
		
		// //////// PERFORM QUERY //////////
		boolean[] variables;
		long healthVars = 2, healthVarsCount, missingHealthVars;

		// make observations
		tmp = delta;
		delta = delta.condition(a);
		delta.ref(); tmp.deref();
		tmp = delta;
		delta = delta.condition(-c);
		delta.ref(); tmp.deref();

		// check if observations are normal
		Sdd gamma;
		gamma = delta.condition(-faulty1);
		gamma.ref();
		tmp = gamma;
		gamma = gamma.condition(-faulty2);
		gamma.ref(); tmp.deref();
		boolean isAbnormal = gamma.equals(new Sdd(false, m));
		System.out.println("observations normal?  : "
				+ (isAbnormal ? "abnormal" : "normal"));
		gamma.deref();
		

		// project onto faults
		Sdd diagnosis = delta.exists(b);
		diagnosis.ref();
		// diagnosis no longer depends on variables A,B or C

		// count the number of diagnoses
		long count = diagnosis.ModelCount();
		// adjust for missing faults
		variables = diagnosis.getVariables();
		healthVarsCount = (variables[(int) faulty1] ? 1 : 0)
				+ (variables[(int) faulty2] ? 1 : 0);
		missingHealthVars = healthVars - healthVarsCount;
		count <<= missingHealthVars; // multiply by 2^missing_health_vars

		// find minimum cardinality diagnoses
		Sdd minDiagnosis = diagnosis.minimizeCardinality();
		minDiagnosis.ref();
		variables = minDiagnosis.getVariables();
		// adjust for missing faults
		if (!variables[(int) faulty1]){
			tmp = minDiagnosis;
			minDiagnosis = minDiagnosis.conjoin(new Sdd(-faulty1, m));
			minDiagnosis.ref(); tmp.deref();
		}
		if (!variables[(int) faulty2]){
			tmp = minDiagnosis;
			minDiagnosis = minDiagnosis.conjoin(new Sdd(-faulty2, m));
			minDiagnosis.ref(); tmp.deref();
		}
		
		// count the number of minimum cardinality diagnoses, and minimum
		// cardinality
		long minCount = minDiagnosis.ModelCount();
		long minCard = minDiagnosis.minimumCardinality();

		System.out.println("sdd model count       : " + count);
		System.out.println("sdd model count (min) : " + minCount);
		System.out.println("sdd cardinality       : " + minCard);

		// ////////SAVE SDDS //////////

		System.out.println("saving sdd and dot ...");

		delta.save("output/circuit-kb.sdd");
		diagnosis.save("output/diagnosis.sdd");
		minDiagnosis.save("output/diagnosis-min.sdd");

		delta.saveAsDot("output/circuit-kb.dot");
		diagnosis.saveAsDot("output/diagnosis.dot");
		minDiagnosis.saveAsDot("output/diagnosis-min.dot");

		// //////// CLEAN UP //////////

		m.free();
	}

}
