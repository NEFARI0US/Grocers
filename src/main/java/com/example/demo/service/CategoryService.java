package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryrepo;
	public void addCategory(Category category) {
		categoryrepo.save(category);
	}
	public List<Category> getAllCategories(){
		return categoryrepo.findAll();		
	}
	public void deleteCategoryById(int id) {
		categoryrepo.deleteById(id);
		
	}
	public Optional<Category> getCategoryById(int id){
		return categoryrepo.findById(id);
	}

}
