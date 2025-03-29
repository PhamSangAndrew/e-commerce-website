package com.bkap.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.bkap.entities.Fruits;
import com.bkap.reponsistory.FruitsRopossistory;


@Service
public class FruitServiceImp implements FruitService{
	@Autowired
	FruitsRopossistory fruitsRopossistory;

	@Override
	public List<Fruits> getAll() {
		// TODO Auto-generated method stub
		return this.fruitsRopossistory.findAll();
	}

	@Override
	public Fruits findById(Integer FruitsId) {
		// TODO Auto-generated method stub
		return this.fruitsRopossistory.findById(FruitsId).get();
	}

	@Override
	public Boolean insert(Fruits fruits) {
		// TODO Auto-generated method stub
		try {
			this.fruitsRopossistory.save(fruits);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean update(Fruits fruits) {
		// TODO Auto-generated method stub
		try {
			this.fruitsRopossistory.save(fruits);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean delete(Integer FruitsId) {
		// TODO Auto-generated method stub
		try {
			this.fruitsRopossistory.delete(findById(FruitsId));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Fruits> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo-1, 6);
		return this.fruitsRopossistory.findAll(pageable);
	}

	@Override
	public List<Fruits> search(Integer cateId) {
		// TODO Auto-generated method stub
		if (cateId == null) {
			return this.fruitsRopossistory.findAll().stream()
					.filter(Fruits -> Fruits.getFruitStatus() == true).limit(8)
					.collect(Collectors.toList());
		} else {
			return this.fruitsRopossistory.search(cateId).stream()
					.filter(Fruits -> Fruits.getFruitStatus() == true).limit(8)
					.collect(Collectors.toList());
		}
	}

	@Override
	public List<Fruits> Listvergetable() {
		// TODO Auto-generated method stub
		return this.fruitsRopossistory.Listvergetable().stream()
				.filter(Fruits -> Fruits.getFruitStatus() == true)
				.collect(Collectors.toList());
	}

	@Override
	public List<Fruits> ListSalePrice() {
		// TODO Auto-generated method stub
		return this.fruitsRopossistory.ListSalePrice()
				.stream()
				.filter(Fruits -> Fruits.getSalePrice() > 0)
				.limit(6)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Fruits> SearchPrice(Float toPrice, Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 9);
		if (toPrice > 0) {
			return  this.fruitsRopossistory.SearchPrice(toPrice, pageable);
			
		} else {
           return this.fruitsRopossistory.findAll(pageable);
		}
	}

	@Override
	public Page<Fruits> searchName(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 9);
		if(keyword != null && !keyword.trim().isEmpty()) {
		  return this.fruitsRopossistory.SearchName(keyword, pageable);
		}else {
			return this.fruitsRopossistory.findAll(pageable);
		}
	}

	@Override
	public int getStockByProductId(int FruitId) {
		// TODO Auto-generated method stub
		return this.fruitsRopossistory.findStockById(FruitId);
	}

	@Override
	public boolean updateStock(int FruitId, int Quantity) {
		// TODO Auto-generated method stub
		 int stock = getStockByProductId(FruitId);
	        if (stock >= Quantity) {
	           this.fruitsRopossistory.updateStock(FruitId, stock - Quantity);
	            return true;
	        }
	        return false;
	}

	

}
