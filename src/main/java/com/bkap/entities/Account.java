package com.bkap.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Account")
public class Account {
	@Id
	@Column(name="AccountId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AccountId;

	@Column(name= "UserName")
	private String UserName;
	@Column(name="Password")
	private String Password;
	@Column(name ="FullName")
	private String FullName;
	@Column(name ="Image")
	private String Image;
	@Column(name = "Email")
	private String Email;
	@Column(name = "Address")
	private String Address;
	@Column(name ="Phone")
	private String Phone;
	@Column(name = "IsAdmin")
	private Boolean IsAdmin = true;
	@Column(name = "Active")
	private Boolean Active = true;
	@Column(name="Role")
	private String Role = "USER";
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(int accountId, String userName, String password, String fullName, String image, String email,
			String address, String phone, Boolean isAdmin, Boolean active, String role) {
		super();
		AccountId = accountId;
		UserName = userName;
		Password = password;
		FullName = fullName;
		Image = image;
		Email = email;
		Address = address;
		Phone = phone;
		IsAdmin = isAdmin;
		Active = active;
		Role = role;
	}
	public int getAccountId() {
		return AccountId;
	}
	public void setAccountId(int accountId) {
		AccountId = accountId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public Boolean getIsAdmin() {
		return true;
	}
	public void setIsAdmin(Boolean isAdmin) {
		IsAdmin = true;
	}
	public Boolean getActive() {
		return true;
	}
	public void setActive(Boolean active) {
		Active = true;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	
	
	
}
