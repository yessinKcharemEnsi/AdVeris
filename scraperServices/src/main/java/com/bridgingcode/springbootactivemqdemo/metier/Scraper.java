package com.bridgingcode.springbootactivemqdemo.metier;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The Scraper class interacts with the ParseHub API to manage scraping projects,
 * start runs, and fetch the scraped data.
 */
public class Scraper {
    // API key and project token for accessing ParseHub services
    public String api_key = "tH_JPiY0Y3fL";
    public String Project_Token = "tHHkvRCiudLU";

    /**
     * Corrects a string by removing newlines and unnecessary spaces.
     * @param toCorrect The string to correct.
     * @return The corrected string.
     */
    public String correctStringJson(String toCorrect) {
        return toCorrect.replace("\n", "").replace(" ", "");
    }

    /**
     * Fetches a list of projects from the ParseHub API.
     * @return The JSON response as a string containing the list of projects.
     */
    public String getProjects() {
        var client = HttpClient.newHttpClient();
        URI uri;
        try {
            // Construct the URI for the API endpoint
            uri = new URIBuilder("https://parsehub.com/api/v2/projects")
                    .addParameter("api_key", api_key)
                    .addParameter("offset", "0") // Starting point for pagination
                    .addParameter("limit", "20") // Limit results to 20
                    .addParameter("include_options", "1") // Include options in the response
                    .build();

            // Prepare the GET request
            var request = HttpRequest.newBuilder(uri)
                    .header("accept", "application/json;charset=UTF-8")
                    .GET()
                    .build();

            // Send the request and get the response
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body(); // Return the response body as a string
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null; // Return null if an exception occurs
    }

    /**
     * Starts a scraper run for a given project token.
     * @return The run token used to track the scraper run.
     */
    public String runScraper() {
        var client = HttpClient.newHttpClient();
        URI uri;
        try {
            // Construct the URI for starting the run
            uri = new URIBuilder("https://www.parsehub.com/api/v2/projects/" + Project_Token + "/run")
                    .addParameter("api_key", api_key)
                    .addParameter("start_template", "main_template")
                    .addParameter("send_email", "1") // Sends an email when the run completes
                    .build();

            // Prepare the POST request
            var request = HttpRequest.newBuilder(uri)
                    .header("accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody()) // No body for the POST request
                    .build();

            // Send the request and get the response
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject myObject = new JSONObject(response.body());

            // Extract and return the run token from the response
            return myObject.getString("run_token");
        } catch (URISyntaxException | IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return null; // Return null if an exception occurs
    }

    /**
     * Fetches the data for a completed scraper run.
     * @param runToken The token used to identify the run.
     * @return The scraped data as a JSON string.
     */
    public String getAd(String runToken) {
        var client = HttpClient.newHttpClient();
        URI uri;
        int dataReady = 0;
        String url = "https://www.parsehub.com/api/v2/runs/" + runToken;

        while (dataReady == 0) {
            try {
                // Construct the URI for fetching the run status
                uri = new URIBuilder(url)
                        .addParameter("api_key", api_key)
                        .build();

                // Prepare the GET request
                var request = HttpRequest.newBuilder(uri)
                        .header("accept", "application/json;charset=UTF-8")
                        .GET()
                        .build();

                // Send the request and get the response
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject jsonResponse = new JSONObject(response.body());

                // Check if the data is ready
                dataReady = jsonResponse.getInt("data_ready");
                System.out.println(dataReady); // Print the status

            } catch (URISyntaxException | IOException | InterruptedException | JSONException e) {
                System.out.println("\n my log ==>  error here");
                e.printStackTrace();
            }
        }

        System.out.println("Data is ready = " + fetchData(runToken));

        // Once data is ready, fetch and return it
        return fetchData(runToken);
    }

    /**
     * Fetches the actual scraped data once the run is complete.
     * @param runToken The token used to identify the completed run.
     * @return The scraped data as a JSON string.
     */
    public String fetchData(String runToken) {
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("https")
                        .host("www.parsehub.com")
                        .path("/api/v2/runs/" + runToken + "/data")
                        .queryParam("api_key", "tH_JPiY0Y3fL")
                        .queryParam("format", "json")
                        .build()
                        .encode();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(uriComponents.toUriString())
                .get()
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println("\n my log ==> response = " + response);


            // Correct the string json format of the response
            String jsonString = correctStringJson(response.body().string());
            System.out.println("\n my log ==> jsonString = " + jsonString);

            return jsonString;
        } catch (IOException e) {
            System.out.println("\n my log ==> failure at = fetchData");

            e.printStackTrace();
        }

        return null;
    }


}
