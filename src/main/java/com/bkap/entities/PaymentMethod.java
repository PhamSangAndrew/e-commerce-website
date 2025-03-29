package com.bkap.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="order_payment_methods")
public class PaymentMethod {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name ="OderId")
	int OderId;
	@Column(name = "paymentMethod")
	String paymentMethod;
	public PaymentMethod() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentMethod(int id, int oderId, String paymentMethod) {
		super();
		this.id = id;
		OderId = oderId;
		this.paymentMethod = paymentMethod;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOderId() {
		return OderId;
	}
	public void setOderId(int oderId) {
		OderId = oderId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	

}
