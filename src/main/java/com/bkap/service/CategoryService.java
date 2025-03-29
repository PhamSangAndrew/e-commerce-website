package com.bkap.service;

import java.util.List;

import com.bkap.entities.Category;

public interface CategoryService {

	  List<Category> getAll();
	  Category findById(Integer CategoryID);
	  Boolean insert(Category cate);
	  Boolean update(Category cate);
	  Boolean delete(Integer CategoryID);
}
