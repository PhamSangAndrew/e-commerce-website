package com.bkap.entities;

import java.util.Date;

import org.hibernate.annotations.GeneratorType;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @Column(name = "Title")
    private String Title ;
    @Column(name = "ImageUrl")
    private String ImageUrl;
    @Column(name = "Link ")
    private String Link;
    @Column(name = "Status")
    private boolean Status;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày
    @Column(name = "CreatedAt")
    private Date CreatedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày
    @Column(name = "UpdatedAt")
    private Date UpdatedAt;
	public Banner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Banner(int id, String title, String imageUrl, String link, boolean status, Date createdAt, Date updatedAt) {
		super();
		Id = id;
		Title = title;
		ImageUrl = imageUrl;
		Link = link;
		Status = status;
		CreatedAt = createdAt;
		UpdatedAt = updatedAt;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	public Date getCreatedAt() {
		return CreatedAt;
	}
	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}
	public Date getUpdatedAt() {
		return UpdatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}
    
    
}
