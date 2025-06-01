package org.example.carrentalbackend.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.carrentalbackend.user.User;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class DataUploader {
    public static void fetchData(String api) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            URI uri = new URI(api);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<User> responseData = objectMapper.readValue(response.body(), new TypeReference<>() {
            });

            ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

            String prettyJson = objectWriter.writeValueAsString(responseData);

            System.out.println(prettyJson);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void uploadData(String api) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<User> users = objectMapper.readValue(
                    new File("src/main/resources/data/users.json"),
                    new TypeReference<>() {
                    }
            );

            for (User user : users) {
                System.out.println(user.getUsername());
            }

            URI uri = new URI(api);

            HttpClient client = HttpClient.newHttpClient();

            for (User user : users) {
                try {
                    String newUser = objectMapper.writeValueAsString(user);
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(uri)
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(newUser))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error ... Continuing");
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
//        String API_KEY = "http://localhost:8080/users/register";
//
////        uploadData(API_KEY);
//
//        HttpClient client = HttpClient.newHttpClient();
//        URI uri = URI.create(API_KEY);
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        for (int i = 0; i < 10000; i++) {
//            try {
//                String firstName = "Nishan" + i;
//                String lastName = "Subba" + i;
//                String username = "nishan" + i;
//                String email = "nishan" + i + "@gmail.com";
//                String password = "nishan" + i;
//                String phoneNumber = "269887" + i;
//                Role role = Role.USER;
//
//                User user = new User(firstName, lastName, username, email, password, phoneNumber, role);
//
//                String createdUser = objectMapper.writeValueAsString(user);
//
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(uri)
//                        .header("Content-Type", "application/json")
//                        .POST(HttpRequest.BodyPublishers.ofString(createdUser))
//                        .build();
//
//                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//                if (userId % 100 == 0) {
//                    System.out.println("Uploaded user #" + userId + ": " +  response.statusCode() );
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error with user #" + (i + 1) + ": " + e.getMessage());
//            }
//
//        }
    }
}
