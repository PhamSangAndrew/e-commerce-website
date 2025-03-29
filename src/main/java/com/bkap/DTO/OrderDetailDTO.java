package com.bkap.DTO;

public class OrderDetailDTO {
	private int orderId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
	public OrderDetailDTO(int orderId, String productName, int quantity, double price, double totalPrice) {
		super();
		this.orderId = orderId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}
	public OrderDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    

}
