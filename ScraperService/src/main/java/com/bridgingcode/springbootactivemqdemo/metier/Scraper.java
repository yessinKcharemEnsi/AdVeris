package com.bridgingcode.springbootactivemqdemo.metier;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;


public class Scraper {
    private final String apiKey;
    private final String projectToken;
    private final String baseUrl;

    public Scraper() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
            this.apiKey = properties.getProperty("api.key");
            this.projectToken = properties.getProperty("project.token");
            this.baseUrl = properties.getProperty("base.url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties.", e);
        }
    }

    public String sanitizeJsonString(String toCorrect) {
        return toCorrect.replace("\n", "").replace(" ", "");
    }

    public String getProjects() {
        var client = HttpClient.newHttpClient();
        try {
            URI uri = new URIBuilder(baseUrl + "/projects")
                    .addParameter("api_key", apiKey)
                    .addParameter("offset", "0")
                    .addParameter("limit", "20")
                    .addParameter("include_options", "1")
                    .build();

            var request = HttpRequest.newBuilder(uri)
                    .header("accept", "application/json;charset=UTF-8")
                    .GET()
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String runScraper() {
        var client = HttpClient.newHttpClient();
        try {
            URI uri = new URIBuilder(baseUrl + "/projects/" + projectToken + "/run")
                    .addParameter("api_key", apiKey)
                    .addParameter("start_template", "main_template")
                    .addParameter("send_email", "1")
                    .build();

            var request = HttpRequest.newBuilder(uri)
                    .header("accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject myObject = new JSONObject(response.body());
            return myObject.getString("run_token");
        } catch (URISyntaxException | IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAd(String runToken) {
        var client = HttpClient.newHttpClient();
        String url = baseUrl + "/runs/" + runToken;
        int dataReady = 0;

        while (dataReady == 0) {
            try {
                URI uri = new URIBuilder(url)
                        .addParameter("api_key", apiKey)
                        .build();

                var request = HttpRequest.newBuilder(uri)
                        .header("accept", "application/json;charset=UTF-8")
                        .GET()
                        .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject jsonResponse = new JSONObject(response.body());
                dataReady = jsonResponse.getInt("data_ready");
                Thread.sleep(1000);
            } catch (URISyntaxException | IOException | InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        }

        return fetchData(runToken);
    }
    public String fetchData(String runToken) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("www.parsehub.com")
                .path("/api/v2/runs/" + runToken + "/data")
                .queryParam("api_key", apiKey)
                .queryParam("format", "json")
                .build()
                .encode();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uriComponents.toUriString())
                .get()
                .build();

        com.squareup.okhttp.Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                String responseBody;
                try {
                    responseBody = response.body().string(); // Handle IOException
                    return sanitizeJsonString(responseBody);
                } finally {
                    response.body().close(); // Ensure the body is always closed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
