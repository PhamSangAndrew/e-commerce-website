package com.bkap.Controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkap.entities.Basket;
import com.bkap.entities.Fruits;
import com.bkap.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping("/home/cart")
	public String cart(Model model,@AuthenticationPrincipal UserDetails user) {
		List<Basket> baskets = new ArrayList<>();
		model.addAttribute("username", user.getUsername());
		model.addAttribute("baskets", cartService.getBasket());
		model.addAttribute("totalItems", cartService.getCount());
		model.addAttribute("totalAmount", cartService.getAmount());
		return "home/pagesh/cart";
	}

	@GetMapping("/cartadd/{id}")
	public String addToCart(Model model, @PathVariable Integer id, HttpSession session) {
		Map<Integer, Basket> basketMap = (Map<Integer, Basket>) session.getAttribute("baskets");
	    if (basketMap == null) {
	        basketMap = new HashMap<>();
	    }

	    // Thêm sản phẩm vào giỏ hàng
	    Basket basket = basketMap.get(id);
	    if (basket == null) {
	        Basket fruit = cartService.add(id);
	        basket = new Basket(fruit.getFruitId(), fruit.getImage(), fruit.getFruitName(), fruit.getPrice(), 1);
	        basketMap.put(id, basket);
	    } else {
	        basket.setQuantity(basket.getQuantity() + 1);
	    }

	    // Lưu giỏ hàng vào session
	    session.setAttribute("baskets", basketMap);

	    return "redirect:/home/shop";
	}

	@GetMapping("/updateQuantity/{id}/{value}")
	public String updateCart(Model model,@PathVariable("id") Integer FruitId, @PathVariable("value") int Quantity) {
        Basket baskets = this.cartService.update(FruitId, Quantity);
        model.addAttribute("baskets", cartService.getBasket());
		model.addAttribute("totalItems", cartService.getCount());
		model.addAttribute("totalAmount", cartService.getAmount());
        
        return "redirect:/cart";
    }
	@GetMapping("/removeItem/{id}")
    public String removeFromCart(@PathVariable("id") Integer FruitId) {
        cartService.remove(FruitId);                   
        return "redirect:/cart";
    }
}
