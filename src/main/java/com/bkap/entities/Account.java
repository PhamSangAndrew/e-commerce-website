package com.bkap.entities;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;


@Entity
@Table(name="Account")
public class Account {
	@Id
	@Column(name="AccountId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AccountId;

	@NotBlank(message = "Tên không được để trống")
	@Column(name= "UserName")
	private String UserName;
	
	@Column(name="Password")
	@Size(min = 6, message = "Mật khẩu ít nhất 6 ký tự")
	private String Password;
	
	@Column(name ="FullName")
	@NotBlank(message = "Tên không được để trống")
	private String FullName;
	
	@Column(name ="Image")
	private String Image;
	
	@Column(name = "Email")
	@Email(message = "Email không hợp lệ")
	private String Email;
	
	@Column(name = "Address")
	private String Address;
	
	@Column(name ="Phone")
	@Size(max = 10, message = "Số điện thoại không xác định")
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


	public Account(int accountId, @NotBlank(message = "Tên không được để trống") String userName,
			@Size(min = 6, message = "Mật khẩu ít nhất 6 ký tự") String password,
			@NotBlank(message = "Tên không được để trống") String fullName, String image,
			@jakarta.validation.constraints.Email(message = "Email không hợp lệ") String email, String address,
			@Size(max = 10, message = "Số điện thoại không xác định") String phone, Boolean isAdmin, Boolean active,
			String role) {
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
		return IsAdmin;
	}


	public void setIsAdmin(Boolean isAdmin) {
		IsAdmin = isAdmin;
	}


	public Boolean getActive() {
		return Active;
	}


	public void setActive(Boolean active) {
		Active = active;
	}


	public String getRole() {
		return Role;
	}


	public void setRole(String role) {
		Role = role;
	}
	
	
	
	
	
}
