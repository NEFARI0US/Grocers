package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;


@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@Autowired
	CategoryService categoryservice;
	UserService userservice;
	
	
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "adminHome";
	}
	
	
	@GetMapping("/admin/categories")
	public String getCategory(Model model) {
		model.addAttribute("categories",categoryservice.getAllCategories());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCategoryAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategoryAdd(@PathVariable int id) {
		categoryservice.deleteCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategoryById(@PathVariable int id , Model model) {
		Optional<Category> category = categoryservice.getCategoryById(id); 
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}
		else {
			return "404";
		}
	}
	@PostMapping("/admin/categories/add")
	public String postCategoryAdd(@ModelAttribute ("category") Category category) {
		categoryservice.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	
	@GetMapping("/admin/user")
	public String getUser(Model model) {
		model.addAttribute("user",userservice.getAllUser());
		return "user";
	}
	@GetMapping("/admin/user/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		userservice.deleteUserById(id);
		return "redirect:/admin/user";
	}
	
	@GetMapping("/admin/user/update/{id}")
	public String updateUserById(@PathVariable int id , Model model) {
		Optional<User> user = userservice.getUserById(id); 
		if(user.isPresent()) {
			model.addAttribute("user",user.get());
			return "user";
		}
		
		
		else {
			return "404";
		}
	}
	
	
	
	@Autowired
	ProductService productservice;
	
	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products",productservice.getAllProducts());
		return "products";
	}
	
	
	@GetMapping("/admin/products/add")
	public String getProductsAdd(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories", categoryservice.getAllCategories());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
									@RequestParam("productImage")MultipartFile file,
									@RequestParam("imgName")String imgName) throws IOException {
		
		Product product = new Product();
		product.setId(productDTO.getProductId());
		product.setName(productDTO.getName());
		product.setCategory(categoryservice.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else
		{
			imageUUID = imgName;			
		}
		product.setImageName(imageUUID);
		productservice.addProduct(product);
		
		
		return "redirect:/admin/products";
		
	}
	
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProductAdd(@PathVariable long id) {
		productservice.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProductById(@PathVariable long id , Model model) {
		
		Product product = productservice.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(product.getProductId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryservice.getAllCategories());
		model.addAttribute("productDTO", productDTO);
		
		return "productsAdd";
	}
	
	
	
	


}
