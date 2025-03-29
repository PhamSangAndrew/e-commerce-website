package com.bkap.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkap.entities.Basket;
import com.bkap.entities.Fruits;
import com.bkap.reponsistory.FruitsRopossistory;
@Service
public class CartServiceImp implements CartService{

	private Map<Integer, Basket> basketMap = new HashMap<>();
	@Autowired
	FruitService fruitsService;
	@Autowired
	FruitsRopossistory fruitsRopossistory;
	
	@Override
	public Basket add(Integer Id) {
		// TODO Auto-generated method stub
		List<Basket> baskets = new ArrayList<>();
		Basket basket = basketMap.get(Id);
		Fruits f = fruitsService.findById(Id);
		if (basket == null) {
			basket = new Basket(f.getFruitId(), f.getImage(), f.getFruitName(), f.getPrice(), 1);
			basketMap.put(Id, basket);
		} else {
			basket.setQuantity(basket.getQuantity() + 1);

		}
		
		return basket;
	}

	@Override
	public Basket update(Integer Id, int quantity) {
		// TODO Auto-generated method stub
		Basket basket = basketMap.get(Id);

		if (basket != null) {
			
			basket.setQuantity(quantity);
		}
		return basket;
	}

	@Override
	public void remove(Integer Id) {
		// TODO Auto-generated method stub
		basketMap.remove(Id);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		basketMap.clear();
	}

	@Override
	public Collection<Basket> getBasket() {
		// TODO Auto-generated method stub
		return basketMap.values();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return basketMap.values().stream().mapToInt(Basket::getQuantity).sum();
	}

	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return Math.round(basketMap.values().stream().mapToDouble(b -> b.getPrice() * b.getQuantity()).sum());
	}


}
