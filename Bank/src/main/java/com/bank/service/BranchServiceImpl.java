package com.bank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.BranchEntity;
import com.bank.repo.IBranchRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchServiceImpl implements IBranchService {

	@Autowired
    private IBranchRepo branchRepo;

    @Override
    public List<BranchEntity> getAllBranchs() {
        return branchRepo.findAll();    
    }

    @Override
    public BranchEntity getBranchById(Long id) {
    	return branchRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Branch not found"));    
    }

    @Override
    public BranchEntity createBranch(BranchEntity branch) {
        return branchRepo.save(branch);
    }
    
    @Override
    public void deleteBranch(Long id) {
        branchRepo.deleteById(id);
    }

	@Override
	public List<BranchEntity> getBranchName(String branchName) {
    	log.info("Finding branch by branch name '{}'", branchName);
    	return branchRepo.findByBranchName(branchName);
	}
	
	public List<BranchEntity> getByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
		log.info("Finding branch between {} and {}", startDate, endDate);
		return branchRepo.findByCreationDateBetween(startDate, endDate);
	}
}
