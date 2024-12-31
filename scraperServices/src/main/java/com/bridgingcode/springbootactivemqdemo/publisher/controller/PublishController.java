package com.bridgingcode.springbootactivemqdemo.publisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for publishing messages to an ActiveMQ queue.
 */
@RestController
@RequestMapping("/api")
public class PublishController {

    private final JmsTemplate jmsTemplate;
    private final String queueName;

    @Autowired
    public PublishController(JmsTemplate jmsTemplate, @Value("${activemq.queue.name}") String queueName) {
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }


    /**
     * Endpoint to publish a message to the "testQ" queue.
     *
     * @return ResponseEntity indicating the status of the operation
     */
    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage() {
        try {
            // Send a message to the "testQ" queue
            jmsTemplate.convertAndSend(queueName, "hello");            System.out.println("Message sent to queue: testQ");
            return new ResponseEntity<>("Message sent successfully.", HttpStatus.OK);
        } catch (Exception e) {
            // Log and return an error response
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
