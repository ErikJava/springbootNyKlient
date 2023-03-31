package com.springboot.springboot;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Scanner;

public class Client {

    // URL of the REST API.
    private static final String SERVER_URL = "http://localhost:8080/json";

    public static void main(String[] args)  {

        // Prints messages to console.
        System.out.println("Connection Successful! \n");
        System.out.println("Instructions: \nIf you'd like to receive a JSON-Object, enter '1' & then enter the name of the object you'd like to receive.");
        System.out.println("If you'd like to receive the whole JSON-File, enter '2'.");
        System.out.println("To return to the main menu after receiving a JSON-Object, enter 'n' & then 'y'.\n");

        // Scanner for taking user input.
        Scanner scanner = new Scanner(System.in);
        String input;

        // Repeats until the user decides to exit.
        do {
            System.out.println("What would you like to receive?");
            System.out.println("1. A JSON-Object");
            System.out.println("2. The whole JSON-File\n");
            System.out.println("Type 'exit' to disconnect from the server.\n");
            input = scanner.nextLine();

            if (input.equals("1")) {
                String objName;
                do {
                    System.out.println("\nEnter name of JSON-Object to receive (object1/object2/object3)");
                    objName = scanner.nextLine();

                    // Sends GET request to REST API to retrieve the JSON object.
                    RestTemplate restTemplate = new RestTemplate();

                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SERVER_URL)
                            .queryParam("objName", objName);

                    ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

                    // If the JSON object is not found, print error message to console.
                    // Otherwise, print the JSON object to console.
                    String jsonString = response.getBody();
                    if (jsonString == null || jsonString.equals("null")) {
                        System.out.println("ERROR: JSON-Object not found.");
                    } else {
                        System.out.println("JSON Object:\n" + jsonString);
                    }

                    System.out.println("\nDo you want to receive another JSON-Object? (y/n)");
                    input = scanner.nextLine();
                } while (input.equalsIgnoreCase("y"));
            } else if (input.equals("2")) {
                // Sends GET request to REST API to retrieve the whole JSON file.
                RestTemplate restTemplate = new RestTemplate();

                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SERVER_URL)
                        .queryParam("retrieveWholeFile", true);

                ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

                // Prints the JSON file to console.
                String jsonString = response.getBody();
                System.out.println("JSON File:\n" + jsonString);
            } else if (input.equalsIgnoreCase("exit")) {
                // Exit the program if the user enters "exit"
                System.out.println("Exiting application...");
                System.exit(0);
            } else {
                System.out.println("Invalid option selected.");
            }

            System.out.println("\nDo you want to receive something else? (y/n)");
            input = scanner.nextLine();
        } while (input.equalsIgnoreCase("y"));

        System.out.println("Exiting application...");
    }
}
