package com.bkap.reponsistory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bkap.entities.Fruits;

public interface FruitsRopossistory extends JpaRepository<Fruits, Integer> {
	@Query("SELECT Quantity FROM Fruits f WHERE f.FruitId = :FruitId")
    int findStockById(@Param("FruitId") int FruitId);

    @Modifying
    @Transactional
    @Query("UPDATE Fruits f SET f.Quantity = :Quantity WHERE f.FruitId = :FruitId")
    void updateStock(@Param("FruitId") int FruitId, @Param("Quantity") int Quantity);
	@Query("from Fruits f where f.category.CategoryID=:cateId")
	  List<Fruits> search(Integer cateId);
	@Query("from Fruits f where f.category.CategoryID = 20")
	  List<Fruits> Listvergetable();
	@Query("from Fruits f where SalePrice > 0")
	  List<Fruits> ListSalePrice();
	@Query("from Fruits where Price <= :toPrice")
	  Page<Fruits> SearchPrice(Float toPrice,Pageable pageable);
	@Query("SELECT f FROM Fruits f WHERE LOWER(f.FruitName) LIKE LOWER(CONCAT('%', :FruitName, '%'))")
	Page<Fruits> SearchName(@Param("FruitName") String keyword, Pageable pageable);
}
