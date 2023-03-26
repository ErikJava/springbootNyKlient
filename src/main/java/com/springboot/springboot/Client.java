package com.springboot.springboot;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static final String SERVER_URL = "http://localhost:8080/json";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String objName, attributeName, input;
        boolean receiveWholeObject;

        do {
            System.out.println("You have successfully connected to the server" + ".\n");
            System.out.println("Enter name of JSON-Object to receive (object1/object2/object3)");
            objName = scanner.nextLine();

            System.out.println("Enter name of JSON-Attribute to receive (name/age/city");
            attributeName = scanner.nextLine();

            System.out.println("Receive all JSON-Data from the object instead? (y/n)");
            input = scanner.nextLine();
            receiveWholeObject = input.equalsIgnoreCase("y");

            System.out.println("Receive all JSON-data from the whole JSON-File? (y/n)");
            input = scanner.nextLine();
            boolean retrieveWholeFile = input.equalsIgnoreCase("y");

            RestTemplate restTemplate = new RestTemplate();

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SERVER_URL)
                    .queryParam("objName", objName)
                    .queryParam("attributeName", attributeName)
                    .queryParam("receiveWholeObject", receiveWholeObject)
                    .queryParam("retrieveWholeFile", retrieveWholeFile);

            ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

                String jsonString = response.getBody();
                if (retrieveWholeFile) {
                    System.out.println("JSON File:\n" + jsonString);
                } else {
                    System.out.println("JSON " + (receiveWholeObject ? "Object" : "Attribute") + ":\n" + jsonString);
                }


            System.out.println("Do you want to retrieve another JSON object/attribute? (y/n)");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("n")) {
                System.out.println("Exiting application...");
                break;
            }
        } while (input.equalsIgnoreCase("y"));
    }
}

