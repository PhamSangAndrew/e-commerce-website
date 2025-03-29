package com.bkap.entities;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Fruits")
public class Fruits {
	@Id
	@Column(name = "FruitId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int FruitId;
	@Column(name = "FruitName")
	String FruitName;
	@Column(name = "Description")
	String Description;
	@Column(name = "Price")
	Float Price;
	@Column(name = "SalePrice")
	Float SalePrice;
	@Column(name = "Quantity")
	int Quantity;
	@Column(name = "Image")
	String Image;
	@Column(name = "Dateadded")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Chọn định dạng phù hợp với form nhập liệu
	Date Dateadded;
	@Column(name = "FruitStatus")
	Boolean FruitStatus;

	public Fruits() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fruits(int fruitId, String fruitName, String description, Float price, Float salePrice, int quantity,
			String image, Date dateadded, Boolean fruitStatus) {
		super();
		FruitId = fruitId;
		FruitName = fruitName;
		Description = description;
		Price = price;
		SalePrice = salePrice;
		Quantity = quantity;
		Image = image;
		Dateadded = dateadded;
		FruitStatus = fruitStatus;
		
	}

	public int getFruitId() {
		return FruitId;
	}

	public void setFruitId(int fruitId) {
		FruitId = fruitId;
	}

	public String getFruitName() {
		return FruitName;
	}

	public void setFruitName(String fruitName) {
		FruitName = fruitName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		Price = price;
	}

	public Float getSalePrice() {
		return SalePrice;
	}

	public void setSalePrice(Float salePrice) {
		SalePrice = salePrice;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public Date getDateadded() {
		return Dateadded;
	}

	public void setDateadded(Date dateadded) {
		Dateadded = dateadded;
	}

	public Boolean getFruitStatus() {
		return FruitStatus;
	}

	public void setFruitStatus(Boolean fruitStatus) {
		FruitStatus = fruitStatus;
	}

	
	
	
	@ManyToOne
	@JoinColumn(name = "categoryID") 

	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
}