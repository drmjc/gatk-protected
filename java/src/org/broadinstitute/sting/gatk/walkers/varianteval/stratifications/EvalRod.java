package org.broadinstitute.sting.gatk.walkers.varianteval.stratifications;

import org.broad.tribble.util.variantcontext.VariantContext;
import org.broadinstitute.sting.gatk.contexts.ReferenceContext;
import org.broadinstitute.sting.gatk.contexts.variantcontext.VariantContextUtils;

import java.util.ArrayList;
import java.util.Set;

public class EvalRod extends VariantStratifier implements RequiredStratification {
    // needs to know the eval rods
    private Set<String> evalNames;
    private ArrayList<String> states;

    @Override
    public void initialize(Set<VariantContextUtils.JexlVCMatchExp> jexlExpressions, Set<String> compNames, Set<String> knownNames, Set<String> evalNames, Set<String> sampleNames) {
        this.evalNames = evalNames;

        states = new ArrayList<String>();
        states.addAll(evalNames);
    }

    public ArrayList<String> getAllStates() {
        return states;
    }

    public ArrayList<String> getRelevantStates(ReferenceContext ref, VariantContext comp, String compName, VariantContext eval, String evalName, String sampleName) {
        ArrayList<String> relevantStates = new ArrayList<String>();

        relevantStates.add(evalName);

        return relevantStates;
    }
}