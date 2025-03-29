package com.bkap.DTO;

import java.util.Date;

import com.bkap.entities.Oder;

public class OrderManagementDTO {

	private String Customername;
	private int OrderID	;
	private Date Orderdate;
	private Double TotalAmount;
	private String Address	;
	private String Phone;
	private String Paymentmethod;
	private String OrderStatus;
	public OrderManagementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderManagementDTO(String customername, int orderID, Date orderdate, Double totalAmount, String address,
			String phone, String paymentmethod, String orderStatus) {
		super();
		Customername = customername;
		OrderID = orderID;
		Orderdate = orderdate;
		TotalAmount = totalAmount;
		Address = address;
		Phone = phone;
		Paymentmethod = paymentmethod;
		OrderStatus = orderStatus;
	}
	public String getCustomername() {
		return Customername;
	}
	public void setCustomername(String customername) {
		Customername = customername;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public Date getOrderdate() {
		return Orderdate;
	}
	public void setOrderdate(Date orderdate) {
		Orderdate = orderdate;
	}
	public Double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		TotalAmount = totalAmount;
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
	public String getPaymentmethod() {
		return Paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		Paymentmethod = paymentmethod;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	
}
