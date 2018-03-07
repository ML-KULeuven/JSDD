package examples;

import sdd.Sdd;
import sdd.SddManager;

public class Circuit {

	// returns an Sdd node representing ( node1 => node2 )
	public static Sdd imply(Sdd node1, Sdd node2) {
		return node1.negate().disjoin(node2);
	}

	// returns an Sdd node representing ( node1 <=> node2 )
	public static Sdd equiv(Sdd node1, Sdd node2) {
		return imply(node1, node2).conjoin(imply(node2, node1));
	}

	public static void main(String[] args) {

		// set up vtree and manager
		long varCount = 5;
		boolean autoGcMin = false;
		SddManager m = new SddManager(varCount, autoGcMin);

		long a = 1, b = 2, c = 3, faulty1 = 4, faulty2 = 5;
		Sdd delta = new Sdd(true, m);
		Sdd alpha;

		// //////// CONSTRUCT KNOWLEDGE BASE //////////

		// ~faulty1 => ( A <=> ~B )
		alpha = equiv(new Sdd(a, m), new Sdd(-b, m));
		alpha = imply(new Sdd(-faulty1, m), alpha);
		delta = delta.conjoin(alpha);

		// faulty1 => ( ( A <=> B ) v ~B )
		alpha = equiv(new Sdd(a, m), new Sdd(b, m));
		alpha = alpha.disjoin(new Sdd(-b, m));
		alpha = imply(new Sdd(faulty1, m), alpha);
		delta = delta.conjoin(alpha);

		// ~faulty2 => ( B <=> ~C )
		alpha = equiv(new Sdd(b, m), new Sdd(-c, m));
		alpha = imply(new Sdd(-faulty2, m), alpha);
		delta = delta.conjoin(alpha);

		// faulty2 => ( ( B <=> C ) v ~C )
		alpha = equiv(new Sdd(b, m), new Sdd(-c, m));
		alpha = alpha.disjoin(new Sdd(-c, m));
		alpha = imply(new Sdd(faulty2, m), alpha);
		delta = delta.conjoin(alpha);

		// //////// PERFORM QUERY //////////
		boolean[] variables;
		long healthVars = 2, healthVarsCount, missingHealthVars;

		// make observations
		delta = delta.condition(a);
		delta = delta.condition(-c);

		// check if observations are normal
		Sdd gamma;
		gamma = delta.condition(-faulty1);
		gamma = gamma.condition(-faulty2);
		boolean isAbnormal = gamma.equals(new Sdd(false, m));
		System.out.println("observations normal?  : "
				+ (isAbnormal ? "abnormal" : "normal"));

		// project onto faults
		Sdd diagnosis = delta.exists(b);
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
		variables = minDiagnosis.getVariables();
		// adjust for missing faults
		if (!variables[(int) faulty1])
			minDiagnosis = minDiagnosis.conjoin(new Sdd(-faulty1, m));
		if (!variables[(int) faulty2])
			minDiagnosis = minDiagnosis.conjoin(new Sdd(-faulty2, m));

		// count the number of minimum cardinality diagnoses, and minimum
		// cardinality
		long minCount = minDiagnosis.ModelCount();
		long minCard = minDiagnosis.minimumCardinality();

		System.out.println("sdd model count       : " + count);
		System.out.println("sdd model count (min) : " + minCount);
		System.out.println("sdd cardinality       : " + minCard);

		// ////////SAVE SDDs //////////

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
