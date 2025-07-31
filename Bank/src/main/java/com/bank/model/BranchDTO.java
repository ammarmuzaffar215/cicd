package com.bank.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BranchDTO {
    private Long branchID;
    private String branchName;
    private String branchPostCode;
    private LocalDateTime creationDate;
}
