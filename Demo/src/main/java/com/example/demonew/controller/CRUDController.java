package com.example.demonew.controller;

import com.example.demonew.model.Data;
import com.example.demonew.model.Item;

import java.util.List;
import java.util.Map;

import com.example.demonew.service.*;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/demo/v1") // Base path for all endpoints in this controller

// TODO: SpringBoot:Practical 1
// Create a new Spring Boot Project as per guide
// Create a new RestController below and update your own GitHub local repo
// Add DOGET, DOPOST, DODELETE and DOPUT function as below 
// Ensure it's working with Postman
// Upload your code your Git Hub

// Hint: Create a HelloController as per sample and test ensure your springboot is working accordingly
// use browser to hit http://localhost:8080/

//TODO: SpringBoot:Practical 2
// Loose coupling design - Related slide (Microservices)

// Discuss in a group to identify 2-3 items below are duplicated and how to improve
// Brainstorm it together and exchange idea
// Create a new class to decouple the logic below


public class CRUDController {

    private final ItemServiceAnalysis itemServiceAnalysis;

//    @Autowired
//    public CRUDController(ItemServiceAnalysis itemServiceAnalysis) {
//        this.itemServiceAnalysis = itemServiceAnalysis;
//    }

	// --- CREATE (HTTP POST) ---
	@PostMapping
	public ResponseEntity<String> createItem(@RequestBody String newItemName) {
		if (newItemName == null || newItemName.isBlank()) {
			return new ResponseEntity<>("Item name cannot be empty or blank.", HttpStatus.BAD_REQUEST); // 400 Bad
																										// Request if
																										// name is empty
		}
		long newId = Data.getIdCounter().incrementAndGet();
		Data.getDataStore().put(newId, newItemName);
		// Returning the ID and the data for confirmation
		return new ResponseEntity<>("Item created successfully with ID: " + newId + " and data: " + newItemName,
				HttpStatus.CREATED); // 201 Created
	}

	// --- CREATE (HTTP POST) ---

	// --- READ (HTTP GET) ---
	@GetMapping
	public ResponseEntity<Map<Long, String>> getAllItems() {
		return new ResponseEntity<>(Data.getDataStore(), HttpStatus.OK); // 200 OK
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getItemById(@PathVariable Long id) {
		String item = Data.getDataStore().get(id);
		if (item != null) {
			return new ResponseEntity<>("Found item with ID: " + id + " and data: " + item, HttpStatus.OK); // 200 OK if
																											// found
		}
		return new ResponseEntity<>("Item with ID: " + id + " not found.", HttpStatus.NOT_FOUND); // 404 Not Found if
																									// not found
	}
	
	@GetMapping("/getDemoOnly")
    public ResponseEntity<List<Item>> getDemoOnly() {
        return new ResponseEntity<>(itemServiceAnalysis.getAllItemsWithDemo(), HttpStatus.OK);
    }

	// --- UPDATE (HTTP PUT) ---
	@PutMapping("/{id}")
	public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody String updatedName) {
	    if (updatedName == null || updatedName.isBlank()) {
	        return new ResponseEntity<>("Updated item name cannot be empty or blank.", HttpStatus.BAD_REQUEST); // 400 Bad Request
	    }

	    // Use computeIfPresent to update the item if it exists
	    String oldName = Data.getDataStore().computeIfPresent(id, (key, existingName) -> updatedName);

	    if (oldName != null) {
	        // If oldName is not null, it means the key was present and the value was updated
	        return new ResponseEntity<>("Item with ID: " + id + " updated successfully to: " + updatedName,
	                                HttpStatus.OK); // 200 OK if updated
	    } else {
	        // If oldName is null, it means the key was not present
	        return new ResponseEntity<>("Item with ID: " + id + " not found for update.", HttpStatus.NOT_FOUND); // 404 Not Found if not found
	    }
	}
	
	// --- DELETE (HTTP DELETE) ---
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable Long id) {
		String removedItem = Data.getDataStore().remove(id); // Returns the removed value or null if not found
		if (removedItem != null) {
			return new ResponseEntity<>(
					"Item with ID: " + id + " and data: '" + removedItem + "' deleted successfully.",
					HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
		}
		return new ResponseEntity<>("Item with ID: " + id + " not found for deletion.", HttpStatus.NOT_FOUND); // 404
																												// Not
																												// Found
																												// if
																												// item
																												// didn't
																												// exist
	}
	

}
