package com.bank.service;

import com.bank.entity.BranchEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IBranchService {
	
	List<BranchEntity> getAllBranchs();
	BranchEntity getBranchById(Long id);
	BranchEntity createBranch(BranchEntity branch);
    void deleteBranch(Long id);
	List<BranchEntity> getBranchName(String branchName);
	List<BranchEntity> getByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);    
}
