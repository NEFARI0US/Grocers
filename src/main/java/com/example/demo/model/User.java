package com.example.demo.model;

import java.util.Iterator;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name ="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotEmpty
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@NotEmpty
	private String password;
	
	public User(User user) {
		super();
		
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.roles = user.getRoles();
		this.password = user.getPassword();
	}
	public User () {
		
	}

	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email (message = "{errors.invalid_email}")
	private String email;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
	name = "user_role",
	joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
	inverseJoinColumns= {@JoinColumn (name = "ROLE_ID", referencedColumnName = "ID")}

	 

	)
	private List<Role> roles;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
//	public boolean hasRole(String roleName) {
//		// TODO Auto-generated method stub
//		Iterator<Role> iterator = this.roles.iterator();
//        while (iterator.hasNext()) {
//            Role role = iterator.next();
//            if (role.getName().equals(roleName)) {
//                return true;
//            }
//	}
//        return false;
//	
//	}
}
