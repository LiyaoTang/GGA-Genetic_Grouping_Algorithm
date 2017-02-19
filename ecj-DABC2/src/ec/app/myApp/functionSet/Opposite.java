package ec.app.myApp.functionSet;

import ec.EvolutionState;
import ec.Problem;
import ec.app.myApp.DoubleData;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class Opposite extends GPNode {

    @Override
    public String toString() {
        return "-";
    }

    public void checkConstraints(final EvolutionState state, final int tree,
            final GPIndividual typicalIndividual, final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length != 1) {
            state.output.error("Incorrect number of chiildren for node "
                    + toStringForError() + " at " + individualBase);
        }
    }

    @Override
    public void eval(final EvolutionState state, final int thread,
            final GPData input, final ADFStack stack,
            final GPIndividual individual, final Problem problem) {
        double result;
        /**
         * Note that we reuse the GPData object rather than allocating new ones.
         * You are free to allocate a new GPData object to pass to your
         * children, but you should use the GPData object provided you when you
         * return a value to your parent. But I suggest you reuse the GPData
         * object to keep from allocating millions of objects during evaluation.
         * */
        DoubleData rd = (DoubleData) input;

        children[0].eval(state, thread, input, stack, individual, problem);
        result = -rd.x;
        System.out.println("相反数："+rd.x);
    }
}
