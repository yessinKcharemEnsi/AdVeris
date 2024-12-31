package futureproof;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeEntries implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeEntries.class);
    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("merge entries executing...");


/*
        if similaritiesDetected == true , then this task will be executed ,
        it should implement the logic of implementing how to merge the new ad , and the corresponding ad
        existing in the database .
 */

    }
}
