package com.bridgingcode.springbootactivemqdemo;

import com.bridgingcode.springbootactivemqdemo.metier.Scraper;
import com.bridgingcode.springbootactivemqdemo.model.Ad;
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
	private static String queueName;
	/**
	 * Configures a JMS listener container factory.
	 *
	 * @param connectionFactory the connection factory for ActiveMQ
	 * @param configurer        the default configurer for the factory
	 * @return the configured listener conftainer factory
	 */
	@Bean
	public JmsListenerContainerFactory<?> configureJmsListenerFactory(ConnectionFactory connectionFactory,
																	  DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	public static void main(String[] args) {

		System.out.println("Running Java version: " + System.getProperty("java.version"));


		// Launch the Spring Boot application
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootActiveMqDemoApplication.class, args);

		// Create an instance of the Scraper class to fetch data
		Scraper scraper = new Scraper();

		// Run the scraper and retrieve the JSON string containing ads
//		String jsonAdData = scraper.getAd(scraper.runScraper());
		String jsonAdData = "{\n" +
				"  \"selection1\": [\n" +
				"    {\n" +
				"      \"name\": \"Example Ad Name\",\n" +
				"      \"code_zimmo\": \"12345\",\n" +
				"      \"Prix\": \"€200,000\",\n" +
				"      \"Surface_habitable\": \"120 m²\",\n" +
				"      \"Ascenceur\": \"Yes\",\n" +
				"      \"Superficie_du_terrain\": \"300 m²\",\n" +
				"      \"meuble\": \"No\",\n" +
				"      \"extract1\": \"Some additional details\",\n" +
				"      \"piscine\": \"No\",\n" +
				"      \"Annee_de_construction\": \"2010\",\n" +
				"      \"etage\": \"2\",\n" +
				"      \"nbre_etage\": \"3\",\n" +
				"      \"nbre_facade\": \"2\",\n" +
				"      \"Garage\": \"Yes\",\n" +
				"      \"douche\": \"2\",\n" +
				"      \"adress\": \"123 Example Street, City, Country\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"name\": \"Another Ad Name\",\n" +
				"      \"code_zimmo\": \"67890\",\n" +
				"      \"Prix\": \"€300,000\",\n" +
				"      \"Surface_habitable\": \"150 m²\",\n" +
				"      \"Ascenceur\": \"No\",\n" +
				"      \"Superficie_du_terrain\": \"400 m²\",\n" +
				"      \"meuble\": \"Yes\",\n" +
				"      \"extract1\": \"Some other details\",\n" +
				"      \"piscine\": \"Yes\",\n" +
				"      \"Annee_de_construction\": \"2015\",\n" +
				"      \"etage\": \"1\",\n" +
				"      \"nbre_etage\": \"2\",\n" +
				"      \"nbre_facade\": \"4\",\n" +
				"      \"Garage\": \"No\",\n" +
				"      \"douche\": \"3\",\n" +
				"      \"adress\": \"456 Another Road, City, Country\"\n" +
				"    }\n" +
				"  ]\n" +
				"}\n";
		System.out.println("\n my log ==> jsonAdData = " + jsonAdData);

		// Parse the JSON data and process each ad
		try {
			JSONObject jsonResponse = new JSONObject(jsonAdData);
			JSONArray adsArray = jsonResponse.getJSONArray("selection1");

			// Create an ObjectMapper for JSON-to-Java object conversion
			ObjectMapper objectMapper = new ObjectMapper();

			// Process each ad in the JSON array
			for (int i = 0; i < adsArray.length(); i++) {
				// Extract the JSON representation of the ad
				String adJsonString = adsArray.getString(i);

				// Convert JSON string to Ad object
				Ad ad = objectMapper.readValue(adJsonString, Ad.class);

				// Log the ad details
				System.out.println("Ad details: " + ad);

				// Publish the ad to the ActiveMQ queue
				JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
				jmsTemplate.convertAndSend("testQ", adJsonString);
				System.out.println("Ad published to queue: " + ad);
			}

		} catch (JSONException e) {
			System.err.println("Error parsing JSON data: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error processing ads: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
