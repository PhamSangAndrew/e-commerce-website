package com.bkap.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.bkap.DTO.OderSummaryDTO;
import com.bkap.DTO.OrderDetailDTO;
import com.bkap.DTO.OrderManagementDTO;
import com.bkap.entities.Basket;
import com.bkap.entities.Oder;
import com.bkap.entities.Oder.Status;
import com.bkap.entities.Oderdetail;
import com.bkap.entities.PaymentMethod;
import com.bkap.reponsistory.FruitsRopossistory;
import com.bkap.reponsistory.OderDetailReposistory;
import com.bkap.reponsistory.OderReposistory;
import com.bkap.reponsistory.PaymentMethodReposistory;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OderServiceImp implements OderService {

	@Autowired
	OderReposistory oderReposistory;
	@Autowired
	OderDetailReposistory detailReposistory;
	@Autowired
	PaymentMethodReposistory paymentMethodReposistory;
	@Autowired
	FruitsRopossistory fruitsRopossistory;

	@Override
	public List<Oder> getAll() {
		// TODO Auto-generated method stub
		return this.oderReposistory.findAll();
	}

	@Override
	public boolean insert(Oder obj) {
		// TODO Auto-generated method stub
		try {
			this.oderReposistory.save(obj);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean insertOrderDetail(Oder order,List<Oderdetail> oderdetails, String paymentMethod) {
	    try {
	        // Lưu đơn hàng vào cơ sở dữ liệu trước
	        this.oderReposistory.save(order);
	        // Chuyển đổi giỏ hàng (Basket) thành chi tiết đơn hàng (Oderdetail)
	      for (Oderdetail oderdetail : oderdetails) {
	    	  oderdetail.setOrder(order);
		  }
	        // Lưu tất cả các chi tiết đơn hàng vào cơ sở dữ liệu
	        this.detailReposistory.saveAll(oderdetails);
	        PaymentMethod payment = new PaymentMethod();
	        payment.setOderId(order.getOderId());
	        payment.setPaymentMethod(paymentMethod);
	        this.paymentMethodReposistory.save(payment);
	        
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Page<OderSummaryDTO> findOrdersByAccountId(int accountId, int pageNo) {
	    Pageable pageable = PageRequest.of(pageNo - 1, 10);
	    Page<Object[]> resultList = this.oderReposistory.findOrdersByAccountId(accountId, pageable);

	    List<OderSummaryDTO> orderSummaryDTOList = resultList.stream()
	        .map(result -> {
	            int OderId = (Integer) result[0];      
	            Date OderDate = (Date) result[1];  
	            int TotalQuantity = (int) result[2];
	            Double TotalPrice = (Double) result[3];
	            return new OderSummaryDTO(OderId, OderDate, TotalQuantity, TotalPrice);
	        })
	        .toList();

	    return new PageImpl<>(orderSummaryDTOList, pageable, resultList.getTotalElements());
	}

	@Override
	public Page<OrderManagementDTO> findOrders(String status,int pageNo, int oderId) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 10);
		 Page<Object[]> resultList;
		 if(oderId != 0) {
		        // Truy vấn có điều kiện tìm theo oderId
		        resultList = this.oderReposistory.searchOrders(oderId, pageable);
		    } else if (status != null && !status.isEmpty()) {
		    	resultList = this.oderReposistory.searchStatusOrders(status, pageable);
		    }else {
		    	// Truy vấn tất cả đơn hàng nếu oderId không được cung cấp
		        resultList = this.oderReposistory.findOrders(pageable);
		    }
		 
		 // Ánh xạ dữ liệu từ Object[] sang OrderManagementDTO
	    List<OrderManagementDTO> dtoList = resultList.stream().map(objects -> {
	        int orderID = (int) objects[0];
	        String customerName = (String) objects[1];
	        Date orderDate = (Date) objects[2];
	        Double totalAmount = (Double) objects[3];
	        String address = (String) objects[4];
	        String phone = (String) objects[5];
	        String paymentMethod = (String) objects[6];
	        String orderStatus = (String) objects[7];

	        // Tạo DTO
	        OrderManagementDTO dto = new OrderManagementDTO();
	        dto.setOrderID(orderID);
	        dto.setCustomername(customerName);
	        dto.setOrderdate(orderDate);
	        dto.setTotalAmount(totalAmount);
	        dto.setAddress(address);
	        dto.setPhone(phone);
	        dto.setPaymentmethod(paymentMethod);
	        dto.setOrderStatus(orderStatus);
	        return dto;
	    }).collect(Collectors.toList());
	    return new PageImpl<>(dtoList, pageable, resultList.getTotalElements());
	}

	@Override
	public void updateOderStatus(int orderId, Status status) {
		// TODO Auto-generated method stub
		this.oderReposistory.updateOderStatus(orderId, status);
	}

	@Override
	public List<OrderDetailDTO> findOrderDetailsByOrderId(int oderId) {
		// TODO Auto-generated method stub
		List<Object[]> resultlist = this.detailReposistory.findOrderDetailsByOrderId(oderId);
		List<OrderDetailDTO> orderDetails = new ArrayList<>();

	    for (Object[] result : resultlist) {
	        OrderDetailDTO dto = new OrderDetailDTO();
	        // Ánh xạ các cột trong Object[] vào các trường trong DTO
	        dto.setOrderId((Integer) result[0]);            // OderId
	        dto.setProductName((String) result[1]);           // FruitName
	        dto.setQuantity((Integer) result[2]);           // Quantity
	        dto.setPrice((Double) result[3]);               // Price
	        dto.setTotalPrice((Double) result[4]);          // Quantity * Price

	        orderDetails.add(dto);
	    }
	    System.out.println(orderDetails);
	    return orderDetails;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return oderReposistory.count();
	}

	
	//check the quantity of products
	


}
