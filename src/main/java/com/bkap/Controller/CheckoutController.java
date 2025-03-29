package com.bkap.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkap.entities.Account;
import com.bkap.entities.Basket;
import com.bkap.entities.Oder;
import com.bkap.entities.Oder.Status;
import com.bkap.entities.Oderdetail;
import com.bkap.service.AccountService;
import com.bkap.service.CartService;
import com.bkap.service.FruitService;
import com.bkap.service.OderService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {
	@Autowired
	private CartService cartService;
	@Autowired
	AccountService accountService;
	@Autowired
	OderService oderService;
	@Autowired
	FruitService fruitService;

	@RequestMapping(value = "/cart/checkout", method = RequestMethod.GET)
	public String Checkout(Model model, @AuthenticationPrincipal UserDetails user, HttpSession session) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails users = (UserDetails) principal;
			String username = user.getUsername();
			model.addAttribute("username", username);
		} else {
			// Người dùng chưa đăng nhập
			return "redirect:/login"; // Hoặc xử lý khác khi không có người dùng
		}
		Double totalAmount = cartService.getAmount();
		model.addAttribute("baskets", cartService.getBasket());
		model.addAttribute("totalItems", cartService.getCount());
		model.addAttribute("totalAmount", totalAmount);
		// Kiểm tra thông tin người dùng đăng nhập
	    Object principals = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Integer accountId = null;
	    if (principals instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) principals;
	        accountId = accountService.getAccountIdByUserName(userDetails.getUsername());
	    }

	    if (accountId == null) {
	        model.addAttribute("message", "Please log in to place an order.");
	        return "redirect:/login";
	    }
	    // Lấy thông tin tài khoản từ CSDL
	    Account account = accountService.findById(accountId); 
	    if (account == null) {
	        model.addAttribute("message", "Account not found.");
	        return "redirect:/login";
	    }

	    
	    model.addAttribute("account", account);
	    System.out.println("Account retrieved: " + account);
	    System.out.println("Address: " + account.getAddress());
	    System.out.println("Phone: " + account.getPhone());
		
		return "home/pagesh/checkout";
	}


	@PostMapping("/checkout/placeOder")
	public String oder(@RequestParam double totalAmount,Model model,
			@RequestParam("paymentMethod") String paymentMethod, 
			@RequestParam String address, @RequestParam String phone,
			@RequestParam String note, HttpSession session,
			@RequestParam int Quantity,
			@RequestParam int FruitId ) {
		System.out.println("giá" + totalAmount);
		
		
	    // Lấy giỏ hàng từ session
		Map<Integer, Basket> basketMap = (Map<Integer, Basket>) session.getAttribute("baskets");
		 if (basketMap == null || basketMap.isEmpty()) {
		        System.err.println("Basket is empty or null. Cannot insert order details.");
		        model.addAttribute("message", "Your cart is empty. Please add items to your cart before placing an order.");
		        return "redirect:/home/cart"; // Quay lại trang giỏ hàng
		    }
		// Kiểm tra tồn kho trước khi đặt hàng
		 for (Basket basket : basketMap.values()) {
		        int stock = fruitService.getStockByProductId(basket.getFruitId());

		        if (stock < basket.getQuantity()) {
		            // Nếu tồn kho ít hơn số lượng giỏ hàng, cập nhật giỏ hàng
		            basket.setQuantity(stock);
		            if (stock == 0) {
		                basketMap.remove(basket.getFruitId()); // Xóa nếu hết hàng
		            }
		            session.setAttribute("baskets", basketMap);
		            model.addAttribute("message", "Some products are out of stock. Please review your cart.");
		            return "redirect:/home/cart";
		        }
		    }

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
	    // Lấy thông tin tài khoản từ CSDL
	    Account account = accountService.findById(accountId); // Phương thức này cần được định nghĩa trong service
	    if (account == null) {
	        model.addAttribute("message", "Account not found.");
	        return "redirect:/login";
	    }

	    // Sử dụng thông tin tài khoản nếu form không có địa chỉ hoặc số điện thoại
	    if (address == null || address.isBlank()) {
	        address = account.getAddress();
	    }
	    if (phone == null || phone.isBlank()) {
	        phone = account.getPhone();
	    }
	    model.addAttribute("account", account);
	    System.out.println("Account retrieved: " + account);
	    System.out.println("Address: " + account.getAddress());
	    System.out.println("Phone: " + account.getPhone());

	    // Tạo đơn hàng
	    Oder order = new Oder();
	    order.setAccountId(accountId);
	    order.setReceiverAddress(address);
	    order.setReceiverPhone(phone);
	    order.setOderDate(Date.valueOf(LocalDate.now()));
	    order.setStatus(Status.PENDING);
	    order.setNote(note);
	   

	    // Lưu đơn hàng vào cơ sở dữ liệu	    
	    boolean isOrderInserted = oderService.insert(order);
	    if (!isOrderInserted) {
	        model.addAttribute("message", "Failed to place order. Please try again.");
	        
	        return "redirect:/checkout";
	    }
	    System.out.println("Saved order ID: " + order.getOderId());
	    // Tạo chi tiết đơn hàng
	    List<Oderdetail> oderdetails = new ArrayList<Oderdetail>();
        for (Basket basket : basketMap.values()) {
        	Oderdetail oderdetail = new Oderdetail();
            oderdetail.setOrder(order); // Liên kết với đơn hàng vừa tạo
            oderdetail.setFruitId(basket.getFruitId()); // Lấy fruitId từ giỏ hàng
            oderdetail.setQuantity(basket.getQuantity()); // Lấy số lượng
            oderdetail.setPrice(basket.getPrice()); // Lấy giá sản phẩm
            oderdetails.add(oderdetail);
         // Cập nhật số lượng trong kho
            fruitService.updateStock(basket.getFruitId(), basket.getQuantity());
		}
        
	    boolean isOrderDetailsInserted = oderService.insertOrderDetail(order, oderdetails, paymentMethod);
	    if (!isOrderDetailsInserted) {
	    	 System.err.println("Failed to insert order details for OrderId=" + order.getOderId());
		     model.addAttribute("message", "Failed to save order details.");
		     return "redirect:/checkout";
	       
	    }
	    if ("COD".equals(paymentMethod)) {
	        // Điều hướng đến trang đặt hàng
        	cartService.clear();
 	        session.removeAttribute("basket");
 	        model.addAttribute("message", "Order placed successfully!");
 	        return "redirect:/OderList";
	    } else if ("VNPay".equals(paymentMethod)) {
	    	cartService.clear();
 	        session.removeAttribute("basket");
 	        model.addAttribute("message", "Order placed successfully!");
	    	return String.format("redirect:/api/payment/create_payment/%d/%f", order.getOderId(),totalAmount);
	        // Điều hướng đến trang thanh toán trực tuyến VNPay
//	    	return "redirect:/api/payment/create_payment/" + order.getOderId();

	    }else {	             
	    	// Trường hợp không hợp lệ (nếu có), quay lại checkout
	    	        model.addAttribute("message", "Invalid payment method.");
	    	        return "redirect:/checkout";
	    	    }
	}
}
