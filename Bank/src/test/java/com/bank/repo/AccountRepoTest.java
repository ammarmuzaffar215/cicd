
package com.bank.repo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.bank.entity.AccountEntity;
import com.bank.entity.CustomerEntity;
import com.bank.entity.ProductEntity;

// TODO: SpringBoot: Practical Bonus 1 - Unit Test for AccountRepo
// create unit test for create, delete and get account 
// use CustomerRepoTest as an example

@SpringBootTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup

class AccountRepoTest {
	
	@Autowired
	private IAccountRepo accountRepo;

    @Autowired
    private ICustomerRepo customerRepo;

    @Autowired
    private IProductRepo productRepo;

    private static Long savedAccountId;
	
	@Test
	@Order(1)
	void testCreateAccount() {
        // Set up customer
        CustomerEntity customer = new CustomerEntity();
        customer.setIcNumber("IC123456");
        customer.setLastname("Doe");
        customer.setSurname("John");
        customer.setDescription("Test customer");
        customer.setCreationDate(LocalDateTime.now());
        CustomerEntity savedCustomer = customerRepo.save(customer);

        // Set up product
        ProductEntity product = new ProductEntity();
        product.setProductName("Asus ROG ALLY X");
        product.setDescription("Gaming handheld");
        ProductEntity savedProduct = productRepo.save(product);

        // Create account
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("AC999999999");
        account.setBalance(9999.99);
        account.setCreationDate(LocalDateTime.now());
        account.setCustomerEntity(savedCustomer);
        account.setProductEntity(savedProduct);

        AccountEntity savedAccount = accountRepo.save(account);
        savedAccountId = savedAccount.getAccountID(); // Save for later tests

        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getAccountID());
        assertEquals("AC999999999", savedAccount.getAccountNumber());
	}
	
	@Test
	@Order(2)
	void testGetAccountById() {
        Optional<AccountEntity> accountOpt = accountRepo.findById(savedAccountId);
        assertTrue(accountOpt.isPresent(), "Account should be found");
        assertEquals("AC999999999", accountOpt.get().getAccountNumber());
	}
	
	@Test
	@Order(3)
	void testDeleteAccount() {
		accountRepo.deleteById(savedAccountId);
		Optional<AccountEntity> deleted = accountRepo.findById(savedAccountId);
		assertFalse(deleted.isPresent(), "Account should be deleted");
	}
}
