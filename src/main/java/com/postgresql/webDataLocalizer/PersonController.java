package com.postgresql.webDataLocalizer;

import com.postgresql.webDataLocalizer.model.DataFetcherApp;
import com.postgresql.webDataLocalizer.model.Person;
import com.postgresql.webDataLocalizer.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PersonController {
    @Autowired //automatic dependency injection
    private DataFetcherApp dataFetcherApp;
    @Autowired
    PersonRepo repo;

    @PostMapping("/addPersons")
    public ResponseEntity<String> addPersons(@RequestBody List<Person> persons) {
        List<UUID> duplicateIds = new ArrayList<>();
        List<Person> conflictingPersons = new ArrayList<>();

        for (Person person : persons) {
            if (person.getId() == null || repo.findById(person.getId()).isEmpty()) {
                // If person doesn't have an ID or ID is not found in the database, assign a new ID
                person.setId(UUID.randomUUID());
            } else {
                duplicateIds.add(person.getId()); // Add duplicate ID to the list
                continue; // Skip the rest of the loop iteration if ID is duplicate
            }

            // Check if there's any existing person with the same name and address
            List<Person> existingPersonsByNameAndAddress = repo.findByNameAndAddress(person.getName(), person.getAddress());
            if (!existingPersonsByNameAndAddress.isEmpty()) {
                conflictingPersons.add(person); // Add conflicting person to the list
                continue; // Skip saving if person with same name and address exists
            }

            // Save the person to the database
            repo.save(person);
        }

        // Construct error messages if there are duplicates or conflicting persons
        String errorMessage = constructErrorMessage(duplicateIds, conflictingPersons);

        // Return appropriate response
        if (errorMessage != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            return ResponseEntity.ok("Persons added successfully");
        }
    }

    // Helper method to construct error message
    private String constructErrorMessage(List<UUID> duplicateIds, List<Person> conflictingPersons) {
        if (!duplicateIds.isEmpty()) {
            return "Error: Persons with IDs " + duplicateIds + " already exist in the database.";
        } else if (!conflictingPersons.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Error: The following persons were not added due to conflicts - ");
            for (Person conflictingPerson : conflictingPersons) {
                errorMessage.append(conflictingPerson.getName()).append(" with address ").append(conflictingPerson.getAddress()).append(", ");
            }
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length()); // Remove the extra comma
            return errorMessage.toString();
        } else {
            return null; // No errors
        }
    }

    @GetMapping("/getAllPersons") // Endpoint to get all persons
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> allPersons = repo.findAll();
        return ResponseEntity.ok(allPersons);
    }

    @DeleteMapping("/deleteAllPersons")
    public ResponseEntity<String> deleteAllPersons() {
        repo.deleteAll();
        return ResponseEntity.ok("All persons deleted successfully");
    }

    @PostMapping("/storeLocally")
    public String handlePostmanRequest() {
        // Define the API URL
        String apiUrl = "http://localhost:8080/getAllPersons";

        // Define the file name and path where data will be stored
        String fileName = "data.json";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;

        // Call the fetchDataAndStoreLocally method
        dataFetcherApp.fetchDataAndStoreLocally(apiUrl, filePath);

        // Log completion message
        return "Data fetching and storing completed.";


    }
}
