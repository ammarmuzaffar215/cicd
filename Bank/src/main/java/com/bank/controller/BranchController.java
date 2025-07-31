package com.bank.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.BranchEntity;
import com.bank.mapper.BranchMapper;
import com.bank.model.BranchDTO;
import com.bank.service.IBranchService;
import com.demo.exceptions.DemoAppException;

import lombok.AllArgsConstructor;

//Practical 8 - End to End Spring Boot 
//Create a branch entity with the data below
//a. BranchEntity.java

//branchID - Index ID auto generated
//branchName - length 100 - Not Nullable
//branchPostCode - length 30 - Not Nullable
//creationDate - Auto created when insert record - LocalDateTime 

//b. BranchController with get by ID, get all, add, and delete by ID only

//c. BranchDTO, BranchMapper and BranchMapperTest.java 
//Ensure table and column created on DB

//d. BranchService and BranchServiceImpl

//e. BranchRepo

//Additional Requirement
//f1. Exception Handling - When adding record, if the branchName is contain empty space, throw a DemoAppException with meaningful error message. i.e. Branch Name cannot be empty
//Enable package scanning "com.demo.exceptions"
//Ensure swagger output contains the DemoAppException type and error exist in the app.log file

//f2. BranchRepo - Basic Search Function
//Add a DOGET into controller above able to search by branchName
//Add on the method to the controller, service and repo
//Note: Refer to CustomerController.java getCustomersByDescriptionAndCreationDateBetween() as a sample

//f3. BranchRepo - Search Function by date in between
//Add a DOGET into controller above able to between date from and date to
//Add on the method to the controller, service and repo


//g1 - UnitTesting - Create a BranchSearchTest.java for g2 and g3 above.
@AllArgsConstructor
@RestController
@RequestMapping("/api/branchs")
public class BranchController {
	
	private final IBranchService branchService;
	
	private final BranchMapper branchMapper;
	
	@GetMapping
	public ResponseEntity<List<BranchDTO>> getAllBranchs() {
		return ResponseEntity.ok(
			branchMapper.toDtoList(branchService.getAllBranchs())
		);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(
            branchMapper.toDto(branchService.getBranchById(id))
        );
    }
	
	@GetMapping("/searchByBranchName")
    public List<BranchDTO> getBranchsByBranchName(@RequestParam String branchName) {
        List<BranchEntity> branchs = branchService.getBranchName(branchName);
        return branchMapper.toDtoList(branchs);
    }

	@GetMapping("/searchByBranchsByCreationDateBetween")
    public List<BranchDTO> getBranchsByCreationDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		    List<BranchEntity> branchs = branchService.getByCreationDateBetween(startDate, endDate);
		    return branchMapper.toDtoList(branchs);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDto) {
    	if (branchDto.getBranchName() == null || branchDto.getBranchName().trim().isEmpty()) {
	        throw new DemoAppException("Branch Name cannot be empty");
	    }
        return ResponseEntity.ok(
            branchMapper.toDto(
                branchService.createBranch(branchMapper.toEntity(branchDto))
            )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

}
