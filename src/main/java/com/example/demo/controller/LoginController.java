package com.example.demo.controller;

import java.util.ArrayList;	
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.global.GlobalData;
import com.example.demo.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	RoleRepository rolerepo;
	
	@GetMapping("/login")
	public String getLogin() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegistern() {
		return "register";
	}
	
	@GetMapping("/regSuccess")
	public String getRegister() {
		return "regSuccess";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute ("user") User user , HttpServletRequest request )throws ServletException {
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(rolerepo.findById(2).get());
		user.setRoles(roles);
		userrepo.save(user);
		request.login(user.getEmail(), password);
		
		return "redirect:/";
	}
	
	@PostMapping("/login")
    public String loginUser(@ModelAttribute ("user") User user){

        User userEmailExists = userrepo.findUserByEmail(user.getEmail()).get();
        String existingPassword =userEmailExists.getPassword();
        String password=user.getPassword();
        String adminEmail = "admin@gmail.com";

        if (bCryptPasswordEncoder.matches(password,existingPassword)) {
        		return "redirect:/admin";
        }
        return "redirect:/login";
    }
    
	
	
	
}
