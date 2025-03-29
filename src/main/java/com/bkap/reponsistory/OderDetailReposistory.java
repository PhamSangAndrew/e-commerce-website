package com.bkap.reponsistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bkap.entities.Oderdetail;
@Repository
public interface OderDetailReposistory extends JpaRepository<Oderdetail, Integer>{
	 @Query(value = "SELECT od.OrderId, f.FruitName, od.quantity, od.price,  CAST(SUM(od.quantity * od.price) AS FLOAT) AS TotalPrice "
	 		+ "FROM OderDetail od "
	 		+ "JOIN  Oder o ON o.OderId = od.OrderId "
	 		+ "JOIN Fruits f ON f.FruitId = od.FruitId"
	 		+ " WHERE o.oderId = :oderId "
	 		+ " GROUP BY od.OrderId, f.FruitName, od.quantity, od.price ", nativeQuery = true)
	    List<Object[]> findOrderDetailsByOrderId(@Param("oderId") int oderId);
	    @Query(value = "SELECT CAST(SUM(od.quantity * od.price) AS FLOAT) FROM OderDetail od", nativeQuery = true)
	    float sumTotalAmount();

}
