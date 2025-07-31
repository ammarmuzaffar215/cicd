package com.bank.controller;

import com.bank.entity.BranchEntity;
import com.bank.repo.IBranchRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BranchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IBranchRepo branchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BranchEntity testBranch;

    @BeforeEach
    void setUp() {
        branchRepository.deleteAll();

        testBranch = new BranchEntity();
        testBranch.setBranchName("Kuala Selangor");
        testBranch.setBranchPostCode("42200");
        testBranch.setCreationDate(LocalDateTime.now());

        testBranch = branchRepository.save(testBranch);
    }

    @Test
    void testGetBranchById_success() throws Exception {
        mockMvc.perform(get("/api/branchs/{id}", testBranch.getBranchID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branchName").value("Kuala Selangor"));
    }

//    @Test
//    void testGetBranchById_notFound() throws Exception {
//        mockMvc.perform(get("/api/branchs/{id}", 9999))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void testCreateBranch() throws Exception {
        BranchEntity newBranch = new BranchEntity();
        newBranch.setBranchName("Kuala Selangor");
        newBranch.setBranchPostCode("42200");
        newBranch.setCreationDate(LocalDateTime.now());

        mockMvc.perform(post("/api/branchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBranch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branchName").value("Kuala Selangor"));
    }

    @Test
    void testDeleteBranch() throws Exception {
        mockMvc.perform(delete("/api/branchs/{id}", testBranch.getBranchID()))
                .andExpect(status().isNoContent());
    }
}
