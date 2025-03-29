package com.bkap.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="OderDetail")
	public class Oderdetail {
		@Id
		@Column(name ="OderDetailId")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int OderDetailId;
		@Column(name="FruitId")
		private int FruitId;
		@Column(name="Quantity")
		private int Quantity;
		@Column(name="Price")
		private Float Price;
	
	public Oderdetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Oderdetail(int oderDetailId, int fruitId, int quantity, Float price) {
		super();
		OderDetailId = oderDetailId;
		FruitId = fruitId;
		Quantity = quantity;
		Price = price;
	}
	public int getOderDetailId() {
		return OderDetailId;
	}
	public void setOderDetailId(int oderDetailId) {
		OderDetailId = oderDetailId;
	}
	public int getFruitId() {
		return FruitId;
	}
	public void setFruitId(int fruitId) {
		FruitId = fruitId;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public Float getPrice() {
		return Price;
	}
	public void setPrice(Float price) {
		Price = price;
	}
	@ManyToOne
	@JoinColumn(name = "OrderId", nullable = false)
	private Oder order;

	public Oder getOrder() {
		return order;
	}
	public void setOrder(Oder order) {
		this.order = order;
	}
	
	

}