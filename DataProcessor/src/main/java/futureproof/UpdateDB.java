package futureproof;

import com.bridgingcode.springbootactivemqdemo.AdService;
import com.bridgingcode.springbootactivemqdemo.Context;
import com.bridgingcode.springbootactivemqdemo.model.Ad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDB implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDB.class);
    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("update db executing...");
/*

    --- this is the code that should exist once the merge entries task is implemented
    and the new ad is passed as a process variable with name adString ---



        System.out.println("update db service task is being executed");
        AdService adService = Context.applicationContext.getBean(AdService.class);

        //get the ad in a string format passed as a process variable
        String adString = (String) execution.getVariable("adString");

        //to create an Ad object out of the adString String
        ObjectMapper objectMapper = new ObjectMapper();
        Ad adv = null;
        try {
            adv = objectMapper.readValue(adString, Ad.class);

            //add to Ad to database
            adService.addAd(adv);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

 */


    }
}