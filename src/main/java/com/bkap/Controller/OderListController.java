package com.bkap.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bkap.DTO.OderSummaryDTO;
import com.bkap.DTO.OrderDetailDTO;
import com.bkap.DTO.OrderManagementDTO;
import com.bkap.entities.Oder;
import com.bkap.entities.Oder.Status;
import com.bkap.service.AccountService;
import com.bkap.service.OderService;

@Controller
public class OderListController {
	@Autowired
	private OderService oderService;
	@Autowired
	private AccountService accountService;

	@RequestMapping("/OderList")
	public String listoder(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		// Kiểm tra thông tin người dùng đăng nhập
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = null;
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			accountId = accountService.getAccountIdByUserName(userDetails.getUsername());
		}
		if (accountId == null) {
			model.addAttribute("message", "Please log in to place an order.");
			return "redirect:/login";
		}
		Page<OderSummaryDTO> orders = oderService.findOrdersByAccountId(accountId, pageNo);
		model.addAttribute("orders", orders);
		model.addAttribute("totalPage", orders.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		return "home/pagesh/OrderList";

	}

	@RequestMapping(value = "/admin/Order")
	public String listOderAdmin(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "oderId", required = false) Integer oderId, @RequestParam(name = "status", required = false) String status) {

		Page<OrderManagementDTO> listoder;

		if (oderId != null && oderId > 0) {
			// Lọc theo oderId
			listoder = oderService.findOrders(null, pageNo, oderId);
			if (listoder.isEmpty()) {
				model.addAttribute("error", "Không tìm thấy đơn hàng với mã này!");
			}
		} else if (status != null && !status.isEmpty()) {
			// Lọc theo status
			listoder = oderService.findOrders(status, pageNo, 0);
		} else {
			// Lấy tất cả đơn hàng
			listoder = oderService.findOrders(null, pageNo, 0);

		}
		model.addAttribute("listoder", listoder);
		model.addAttribute("totalPage", listoder.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		return "/admin/pages/Order";
	}

	@PostMapping("/admin/Order/updateStatus")
	public String updateOderStatus(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "orderId", required = false) int orderId, @RequestParam(name = "status", required = false) Status status,
			RedirectAttributes redirectAttributes) {
		try {
			oderService.updateOderStatus(orderId, status);
			redirectAttributes.addFlashAttribute("message", "Order status updated successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("error", "Failed to update order status.");
		}
		return "redirect:/admin/Order";
	}
	@GetMapping("/{oderId}/details")
	public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(@PathVariable int oderId) {
	    System.out.println("Received request for orderId: " + oderId);  // Log để kiểm tra
	    List<OrderDetailDTO> orderDetails = oderService.findOrderDetailsByOrderId(oderId);
	    
	    if (orderDetails == null || orderDetails.isEmpty()) {
	        System.out.println("No order details found for orderId: " + oderId);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	    }

	    System.out.println("Returning order details: " + orderDetails);
	    return ResponseEntity.ok(orderDetails);
	}
	@PostMapping("/OderList/cancelOder/{orderId}")
	public String cancelOrder(@PathVariable("orderId") int orderId, RedirectAttributes redirectAttributes) {
	    // Gọi service để huỷ đơn hàng
	    Boolean result = oderService.cancelOrder(orderId);
	    if (result) {
	        redirectAttributes.addFlashAttribute("success", "Huỷ đơn hàng thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Không thể huỷ đơn hàng này.");
	    }
	    return "redirect:/OderList"; // Chuyển hướng về trang danh sách đơn hàng
	}
}
