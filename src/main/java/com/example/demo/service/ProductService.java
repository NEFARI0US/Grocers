package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productrepo;
	
	public List<Product> getAllProducts(){
		
		return productrepo.findAll();
	}
	
	public void addProduct(Product product) {
		
		productrepo.save(product);
	}
	
	public void removeProductById(long id) {
		
		productrepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		
		return productrepo.findById(id);
	}
	
	public List<Product> getAllProductsByCategoryId(int id){
		
		return productrepo.findAllByCategory_Id(id);
	}
	

}
