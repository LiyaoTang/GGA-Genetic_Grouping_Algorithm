package ec.app.myApp.functionSet;

import ec.EvolutionState;
import ec.Problem;
import ec.app.myApp.DoubleData;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class Max extends GPNode {

    @Override
    public String toString() {
        return "Max";
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
//        System.out.println("-法："+result+"-"+rd.x);
        
        if(result > rd.x){
        	rd.x = result;
        }else{
        	result = rd.x;
        	rd.x = result;
        }
        
//        System.out.println("结果"+rd.x);
    }

}
