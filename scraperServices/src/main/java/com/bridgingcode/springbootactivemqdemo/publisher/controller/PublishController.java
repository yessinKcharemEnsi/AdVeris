package com.bridgingcode.springbootactivemqdemo.publisher.controller;

import com.bridgingcode.springbootactivemqdemo.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for publishing messages to a JMS queue.
 */
@RestController
@RequestMapping("/api/publish")
public class PublishController {

    private final JmsTemplate jmsTemplate;

    @Value("${jms.queue.name}")
    private String defaultQueueName;

    @Autowired
    public PublishController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Publish a simple string message to a JMS queue.
     *
     * @param message the message to be published
     * @param queueName the name of the queue (optional)
     * @return ResponseEntity with status
     */
    @PostMapping
    public ResponseEntity<String> publishMessage(
            @RequestParam String message,
            @RequestParam(required = false) String queueName) {
        try {
            String targetQueue = (queueName != null && !queueName.isEmpty()) ? queueName : defaultQueueName;
            jmsTemplate.convertAndSend(targetQueue, message);
            return ResponseEntity.ok("Message sent to queue: " + targetQueue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Publish an Ad object to a JMS queue.
     *
     * @param ad the Ad object to be published
     * @param queueName the name of the queue (optional)
     * @return ResponseEntity with status
     */
    @PostMapping("/ad")
    public ResponseEntity<String> publishAd(
            @RequestBody Ad ad,
            @RequestParam(required = false) String queueName) {
        try {
            String targetQueue = (queueName != null && !queueName.isEmpty()) ? queueName : defaultQueueName;
            jmsTemplate.convertAndSend(targetQueue, ad);
            return ResponseEntity.ok("Ad object sent to queue: " + targetQueue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
