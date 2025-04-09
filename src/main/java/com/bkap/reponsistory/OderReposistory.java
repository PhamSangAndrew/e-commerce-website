package com.bkap.reponsistory;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bkap.DTO.OrderManagementDTO;
import com.bkap.entities.Oder;
import com.bkap.entities.Oder.Status;


@Repository
public interface OderReposistory extends JpaRepository<Oder, Integer> {

	@Query(value = "SELECT o.OderId, o.OderDate, SUM(od.Quantity), CAST(SUM(od.Quantity * od.Price) AS FLOAT) AS TotalPrice, o.Status "
			+ "FROM Oder o JOIN OderDetail od " 
			+ "ON o.OderId = od.OrderId " 
			+ "WHERE o.AccountId = :accountId "
			+ "GROUP BY o.OderId, o.OderDate, o.Status "
			+ "ORDER BY o.OderDate DESC", nativeQuery = true)
	Page<Object[]> findOrdersByAccountId(@Param("accountId") int accountId, Pageable pageable);
	


	@Query(value = "SELECT o.OderId, a.FullName, o.OderDate,CAST(SUM(CAST(od.Quantity * od.Price AS FLOAT)) AS FLOAT), " +
            "o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status  " +
            "FROM Oder o " +
            "JOIN Account a ON o.AccountId = a.AccountId " +
            "JOIN OderDetail od ON o.OderId = od.OrderId " +
            "JOIN order_payment_methods pm ON o.OderId = pm.OderId " +
            "GROUP BY o.OderId, a.FullName, o.OderDate, o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status",  nativeQuery = true)
	Page<Object[]> findOrders(Pageable pageable);
	
	@Query(value = "SELECT o.OderId, a.FullName, o.OderDate,CAST(SUM(CAST(od.Quantity * od.Price AS FLOAT)) AS FLOAT), " +
            "o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status  " +
            "FROM Oder o " +
            "JOIN Account a ON o.AccountId = a.AccountId " +
            "JOIN OderDetail od ON o.OderId = od.OrderId " +
            "JOIN order_payment_methods pm ON o.OderId = pm.OderId " +
            "WHERE o.OderId = :oderId " +
            "GROUP BY o.OderId, a.FullName, o.OderDate, o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status",  nativeQuery = true)
	Page<Object[]> searchOrders(@Param("oderId") Integer oderId, Pageable pageable);
	@Query(value = "SELECT o.OderId, a.FullName, o.OderDate,CAST(SUM(CAST(od.Quantity * od.Price AS FLOAT)) AS FLOAT), " +
            "o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status  " +
            "FROM Oder o " +
            "JOIN Account a ON o.AccountId = a.AccountId " +
            "JOIN OderDetail od ON o.OderId = od.OrderId " +
            "JOIN order_payment_methods pm ON o.OderId = pm.OderId " +
            "WHERE o.Status = :status " +
            "GROUP BY o.OderId, a.FullName, o.OderDate, o.ReceiverAddress, o.ReceiverPhone, pm.paymentMethod, o.Status",  nativeQuery = true)
	Page<Object[]> searchStatusOrders(@Param("status") String status, Pageable pageable);
	
	
	@Modifying
	@Query("UPDATE Oder o SET o.status = :status WHERE o.OderId = :oderId")
	void updateOderStatus(@Param("oderId") int oderId, @Param("status") Status status);
	
	long count();
}
