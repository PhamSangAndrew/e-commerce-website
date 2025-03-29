package com.bkap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkap.entities.Category;
import com.bkap.reponsistory.CategoryRepossitory;
@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	CategoryRepossitory categoryRepossitory;
	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return this.categoryRepossitory.findAll();
	}

	@Override
	public Category findById(Integer CategoryID) {
		// TODO Auto-generated method stub
		return this.categoryRepossitory.findById(CategoryID).get();
	}

	@Override
	public Boolean insert(Category cate) {
		// TODO Auto-generated method stub
		try {
			 this.categoryRepossitory.save(cate);
			 return true;
		} catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean update(Category cate) {
		// TODO Auto-generated method stub
		try {
			
			 this.categoryRepossitory.save(cate);
			 return true;
		} catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer CategoryID) {
		// TODO Auto-generated method stub
		try {
			this.categoryRepossitory.delete(findById(CategoryID));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
