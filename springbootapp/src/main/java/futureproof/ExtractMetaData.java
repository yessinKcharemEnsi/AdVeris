package futureproof;

import com.bridgingcode.springbootactivemqdemo.consumer.component.MessageConsumer;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractMetaData implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractMetaData.class);
    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("extract metadata executing...");
/*

    this class should implement the logic of passing a description to the NER model and get
    Entities predicted back

*/

    }
}
