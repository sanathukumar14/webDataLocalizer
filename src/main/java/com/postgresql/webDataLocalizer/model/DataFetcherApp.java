package com.postgresql.webDataLocalizer.model;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class DataFetcherApp {

    public void fetchDataAndStoreLocally(String apiUrl, String filePath) {
        try {
            // Create URL object
            URL url = new URL(apiUrl);

            // Open connection
            URLConnection conn = url.openConnection();

            // Set up input stream reader
            InputStream inputStream = conn.getInputStream();

            // Create file output stream
            FileOutputStream outputStream = new FileOutputStream(filePath);

            // Buffer for reading data
            byte[] buffer = new byte[4096]; //read data from the input stream in chunks
            int bytesRead;

            // Read data from URL and write it to file
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            inputStream.close();
            outputStream.close();

            System.out.println("Data fetched from URL and stored locally at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}