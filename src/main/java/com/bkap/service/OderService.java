package com.bkap.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.bkap.DTO.OderSummaryDTO;
import com.bkap.DTO.OrderDetailDTO;
import com.bkap.DTO.OrderManagementDTO;
import com.bkap.entities.Oder;
import com.bkap.entities.Oder.Status;
import com.bkap.entities.Oderdetail;

public interface OderService {
	List<Oder> getAll();
	boolean insert(Oder obj);
	boolean insertOrderDetail(Oder order, List<Oderdetail> oderdetails, String paymentMethod);
	Page<OderSummaryDTO> findOrdersByAccountId(@Param("accountId") int accountId, int pageNo);
	Page<OrderManagementDTO> findOrders(String status, int pageNo,int oderId);
	void updateOderStatus( int orderId, Status status);
	List<OrderDetailDTO> findOrderDetailsByOrderId(@Param("oderId") int oderId);
	long count();
	
	

}
