package com.bkap.reponsistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkap.entities.Category;

public interface CategoryRepossitory extends JpaRepository<Category, Integer> {

}
