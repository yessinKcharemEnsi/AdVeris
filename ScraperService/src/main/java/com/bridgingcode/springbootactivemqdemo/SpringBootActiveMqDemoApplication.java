package com.bridgingcode.springbootactivemqdemo;

import com.bridgingcode.springbootactivemqdemo.metier.Scraper;
import com.bridgingcode.springbootactivemqdemo.model.Ad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@SpringBootApplication
public class SpringBootActiveMqDemoApplication {

    @Value("${activemq.queue.name}")
    private String queueName;

    @Value("${json.ad.selection}")
    private String jsonAdSelection;

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootActiveMqDemoApplication.class, args);
        SpringBootActiveMqDemoApplication app = context.getBean(SpringBootActiveMqDemoApplication.class);
        app.processAds(context);
    }

    private void processAds(ConfigurableApplicationContext context) {
        Scraper scraper = new Scraper();
        String jsonStringAd = scraper.getAd(scraper.runScraper());

        try {
            JSONObject jsonResponse = new JSONObject(jsonStringAd);
            JSONArray jsonArray = jsonResponse.getJSONArray(jsonAdSelection);
            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {
                String jsonString = jsonArray.get(i).toString();
                Ad ad = objectMapper.readValue(jsonString, Ad.class);
                System.out.println(ad);

                JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
                jmsTemplate.convertAndSend(queueName, jsonString);
                System.out.println("Message sent to queue: " + queueName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}