package com.bkap.DTO;

import java.util.Date;

public class OderSummaryDTO {
	private int oderId; // Chuyển thành chữ thường
    private Date oderDate;
    private int totalQuantity;
    private Double totalPrice;
	public OderSummaryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OderSummaryDTO(int oderId, Date oderDate, int totalQuantity, Double totalPrice) {
		super();
		this.oderId = oderId;
		this.oderDate = oderDate;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
	}
	public int getOderId() {
		return oderId;
	}
	public void setOderId(int oderId) {
		this.oderId = oderId;
	}
	public Date getOderDate() {
		return oderDate;
	}
	public void setOderDate(Date oderDate) {
		this.oderDate = oderDate;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    

}
