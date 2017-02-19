package ec.app.myApp.terminalSet.sequencing;

import ec.EvolutionState;
import ec.Problem;
import ec.app.myApp.DoubleData;
import ec.app.myApp.EvolveBatchingRule;
import ec.app.myApp.EvolveSequencingRule;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

// 相当于一个输入变量
public class ProcessingTime extends GPNode {

    @Override
    public String toString() {
        return "PT";
    }

    public void checkConstraints(final EvolutionState state, final int tree,
            final GPIndividual typicalIndividual, final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length != 0) state.output
                .error("Incorrect number of children for node "
                        + toStringForError() + " at " + individualBase);
    }

    @Override
    public void eval(final EvolutionState state, final int thread, final GPData input,
            final ADFStack stack, final GPIndividual individual, final Problem problem) {
        DoubleData rd = (DoubleData) input;
        rd.x = ((EvolveSequencingRule)problem).procTime;
    }

}
