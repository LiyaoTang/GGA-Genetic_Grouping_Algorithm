package ec.app.fayApp.functionSet;

import ec.EvolutionState;
import ec.Problem;
import ec.app.fayApp.DoubleData;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class Div extends GPNode {

    @Override
    public String toString() {
        return "Div";
    }

    public void checkConstraints(final EvolutionState state, final int tree,
            final GPIndividual typicalIndividual, final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length != 2) {
            state.output.error("Incorrect number of chiildren for node "
                    + toStringForError() + " at " + individualBase);
        }
    }

    @Override
    public void eval(final EvolutionState state, final int thread,
            final GPData input, final ADFStack stack,
            final GPIndividual individual, final Problem problem) {
        double result;
        DoubleData rd = (DoubleData) input;

        children[0].eval(state, thread, input, stack, individual, problem);
        result = rd.x;

        children[1].eval(state, thread, input, stack, individual, problem);

        // detect weather divided by zero!!!
//        System.out.println("计算"+result+"/"+rd.x);
//        System.out.println("结果"+rd.x+Double.compare(rd.x, 0));
//        if( Double.compare(rd.x, 0) == 0 ){
        if( rd.x>-0.0000001 && rd.x< 0.000001 ){
//        	System.out.println("会有0");
            rd.x = 1;
        } else{
            rd.x = result / rd.x;
        }
//        System.out.println("结果"+rd.x);
    }
}

