package com.bkap.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bkap.entities.Fruits;

public interface FruitService {
	  List<Fruits> getAll();
	  Fruits findById(Integer FruitsId);
	  Boolean insert(Fruits fruits);
	  Boolean update(Fruits fruits);
	  Boolean delete(Integer FruitsId);
	  Page<Fruits> getAll(Integer pageNo);
	  List<Fruits> search(Integer cateId);
	  Page<Fruits> searchName(String keyword, Integer pageNo );
	  List<Fruits> Listvergetable();
	  List<Fruits> ListSalePrice();
	  Page<Fruits> SearchPrice(Float toPrice, Integer pageNo);
	  public int getStockByProductId(int FruitId);
	  public boolean updateStock(int FruitId, int Quantity);

}
