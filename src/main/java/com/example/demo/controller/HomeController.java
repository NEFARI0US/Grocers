package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.global.GlobalData;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryservice;
	
	@Autowired
	ProductService productservice;
	
	@GetMapping({"/", "/home"})
	public String home(Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryservice.getAllCategories());
		model.addAttribute("products", productservice.getAllProducts());
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id )
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryservice.getAllCategories());
		model.addAttribute("products", productservice.getAllProductsByCategoryId(id));
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable long id )
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("product", productservice.getProductById(id).get());
		return "viewProduct";
	}
}
