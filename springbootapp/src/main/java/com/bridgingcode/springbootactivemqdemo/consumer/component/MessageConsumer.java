package com.bridgingcode.springbootactivemqdemo.consumer.component;

import com.bridgingcode.springbootactivemqdemo.Context;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author AJ Catambay of Bridging Code
 *
 */
@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "testQ")
    public void messageListener(String message) {

        LOGGER.info("Message received! {}", message);

        //getting the application context to call the runtimeService to be able to instantiate new process instance every time a message is detected
        var runtimeService = Context.applicationContext.getBean(RuntimeService.class);

        //define the initial process instance variables
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("similaritiesDetected", false);
        variables.put("adString",message); //adding the message detected (ad) as a string ,it will later be mapped to an ad object before saving it into the database

        //instantiate a new process instance with variables
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("finalWorkflow",variables);



    }
}
