package futureproof;


import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class DetectSimilarities implements JavaDelegate {


    private static final Logger LOGGER = LoggerFactory.getLogger(DetectSimilarities.class);



    @Override
    public void execute(DelegateExecution execution) {

        LOGGER.info("detect similarities  executing...");

/*

    in this class we detect similarities between the coming ad and ads already existing in database
    if any similarities detected , we set the process variable to true using the following code :

    execution.setVariable("similaritiesDetected", true);




 */







    }
}