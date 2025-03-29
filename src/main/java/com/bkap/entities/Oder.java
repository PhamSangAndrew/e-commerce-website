package com.bkap.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "Oder")
public class Oder {
    @Id
    @Column(name = "OderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OderId;
    @Column(name = "OderDate")
    private Date OderDate;
    @Column(name="AccountId")
    private int AccountId;
    @Column(name = "ReceiverAddress")
    private String ReceiverAddress;
    @Column(name = "ReceiverPhone")
    private String ReceiverPhone;
    @Column(name="ReceiverDate")
    private Date ReceiverDate;
    @Column(name="Note")
    private String Note;
    @Enumerated(EnumType.STRING) // Lưu trạng thái dưới dạng chuỗi
    @Column(name = "Status")
    private Status status;
           
	
	public Oder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Oder(int oderId, Date oderDate, int accountId, String receiverAddress, String receiverPhone,
			Date receiverDate, String note, Status status, List<Oderdetail> details) {
		super();
		OderId = oderId;
		OderDate = oderDate;
		AccountId = accountId;
		ReceiverAddress = receiverAddress;
		ReceiverPhone = receiverPhone;
		ReceiverDate = receiverDate;
		Note = note;
		this.status = status;
		this.details = details;
	}

	
	public int getOderId() {
		return OderId;
	}

	public void setOderId(int oderId) {
		OderId = oderId;
	}

	public Date getOderDate() {
		return OderDate;
	}

	public void setOderDate(Date oderDate) {
		OderDate = oderDate;
	}

	public int getAccountId() {
		return AccountId;
	}

	public void setAccountId(int accountId) {
		AccountId = accountId;
	}

	public String getReceiverAddress() {
		return ReceiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		ReceiverAddress = receiverAddress;
	}

	public String getReceiverPhone() {
		return ReceiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		ReceiverPhone = receiverPhone;
	}

	public Date getReceiverDate() {
		return ReceiverDate;
	}

	public void setReceiverDate(Date receiverDate) {
		ReceiverDate = receiverDate;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}


	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Oderdetail> details = new ArrayList<>();

	public List<Oderdetail> getDetails() {
		return details;
	}
	public void setDetails(List<Oderdetail> details) {
		this.details = details;
	}
	public enum Status {
	    PENDING,   // Đơn hàng đang chờ xử lý
	    PAID,      // Đơn hàng đã thanh toán
	    SHIPPED,   // Đơn hàng đã được giao
	    CANCELLED  // Đơn hàng đã bị hủy
	}
	
    
}
