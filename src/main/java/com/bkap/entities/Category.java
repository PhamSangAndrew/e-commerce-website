package com.bkap.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Categories")
public class Category {
	@Id
	@Column(name="CategoryID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int CategoryID;
	@Column(name="CategoryName")
	String CategoryName;
	@Column(name="CateStatus")
	Boolean CateStatus;
	
	public Category(int categoryID, String categoryName, Boolean cateStatus) {
		super();
		CategoryID = categoryID;
		CategoryName = categoryName;
		CateStatus = cateStatus;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public Boolean getCateStatus() {
		return CateStatus;
	}
	public void setCateStatus(Boolean cateStatus) {
		CateStatus = cateStatus;
	}
	
	
	
}
