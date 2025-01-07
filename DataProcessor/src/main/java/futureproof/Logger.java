package futureproof;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class Logger implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("In execution " + execution.getId());
        System.out.println("From process instance " + execution.getSuperExecutionId());
        System.out.println("From process definition " + execution.getProcessDefinitionId());
        System.out.println("With variables : " + execution.getVariables());
        System.out.println(execution.getVariable("userVariable"));
        execution.setVariable("userVariable","yes its changed");
        System.out.println("after try :"+execution.getVariable("userVariable"));
    }
}
