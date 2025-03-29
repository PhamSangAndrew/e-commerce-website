package com.bkap.entities;

public class Basket {
	private int FruitId;
	private String Image;
	private String FruitName;
	private Float Price;
	private int Quantity;
	public Basket() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Basket(int fruitId, String image, String fruitName, Float price, int quantity) {
		super();
		FruitId = fruitId;
		Image = image;
		FruitName = fruitName;
		Price = price;
		Quantity = quantity;
	}
	public int getFruitId() {
		return FruitId;
	}
	public void setFruitId(int fruitId) {
		FruitId = fruitId;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getFruitName() {
		return FruitName;
	}
	public void setFruitName(String fruitName) {
		FruitName = fruitName;
	}
	public Float getPrice() {
		return Price;
	}
	public void setPrice(Float price) {
		Price = price;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	@Override
	public String toString() {
		return "Basket [FruitId=" + FruitId + ", Image=" + Image + ", FruitName=" + FruitName + ", Price=" + Price
				+ ", Quantity=" + Quantity + "]";
	}
	
	

}
