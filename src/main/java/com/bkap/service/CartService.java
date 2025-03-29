package com.bkap.service;

import java.util.Collection;

import com.bkap.entities.Basket;

public interface CartService {
	
	Basket add(Integer Id);
	Basket update(Integer Id, int quantity);
	public void remove(Integer Id);
	void clear();
	Collection<Basket> getBasket();
	int getCount();
	double getAmount();

}
