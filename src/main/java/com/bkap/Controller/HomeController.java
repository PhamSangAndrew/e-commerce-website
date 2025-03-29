package com.bkap.Controller;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.bkap.entities.Account;
import com.bkap.entities.Fruits;
import com.bkap.service.AccountService;
import com.bkap.service.CartService;
import com.bkap.service.CategoryService;
import com.bkap.service.FruitService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private FruitService fruitsService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CartService cartService;
	@Autowired
	private AccountService accountService;
	@RequestMapping("/home")
	public String home(Model model, @RequestParam(required = false) Integer cateId, HttpSession session,@AuthenticationPrincipal UserDetails user) {
		
		List<Fruits> fruits;
	    if (cateId == null) {
	        fruits = fruitsService.getAll(); // Lấy tất cả sản phẩm
	    } else {
	        fruits = fruitsService.search(cateId);
	    }
		model.addAttribute("username", user.getUsername());
		model.addAttribute("fruits", fruits);
		model.addAttribute("totalItems", cartService.getCount());
		model.addAttribute("cateId", cateId);
		model.addAttribute("fruitsL", this.fruitsService.Listvergetable());
		model.addAttribute("cate", this.categoryService.getAll());
		model.addAttribute("saleprice", this.fruitsService.ListSalePrice());
		return "home/index";
	}
	
	
	
}
