package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.ProductEntity;
import com.bank.repo.IProductRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
    private IProductRepo productRepo;

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepo.findAll();    
    }

    @Override
    public ProductEntity getProductById(Long id) {
    	return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));    
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepo.save(product);
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
    	return productRepo.findById(id).map(existing -> {
            existing.setProductName(updatedProduct.getProductName());
            existing.setDescription(updatedProduct.getDescription());
            return productRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    
    

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
