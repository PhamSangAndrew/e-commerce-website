package com.bkap.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bkap.entities.Fruits;
import com.bkap.service.FruitService;


@Controller
public class ShopController {
	@Autowired
	private FruitService fruitsService;
	@Autowired
	private com.bkap.service.CategoryService categoryService;
	@Autowired
	private com.bkap.service.CartService cartService;
	
	@RequestMapping("home/shop")
	private String shop(Model model,@RequestParam(required = false) Float toPrice,
			@RequestParam(name="pageNo", defaultValue = "1")Integer pageNo,
			@AuthenticationPrincipal UserDetails user,
			@RequestParam(name = "keyword", required = false) String keyword) {
		// TODO Auto-generated method stub
		 Page<Fruits> fruits;
		    
		    if (keyword != null && !keyword.trim().isEmpty()) {
		        // Nếu có từ khóa, tìm theo tên
		        fruits = this.fruitsService.searchName(keyword, pageNo);
		    } else if (toPrice != null) {
		        // Nếu có giá, tìm theo giá
		        fruits = this.fruitsService.SearchPrice(toPrice, pageNo);
		    } else {
		        // Nếu không có gì, lấy tất cả sản phẩm
		        fruits = this.fruitsService.getAll(pageNo);
		    }
		model.addAttribute("username", user.getUsername());
		model.addAttribute("totalPage", fruits.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalItems", cartService.getCount());
		model.addAttribute("fruits", fruits);
		model.addAttribute("cate", this.categoryService.getAll());
		model.addAttribute("keyword", keyword);  // Giữ lại giá trị khi tìm kiếm
	    model.addAttribute("toPrice", toPrice);  // Giữ lại giá trị khi tìm kiếm
		return "home/pagesh/shop";

	}
	
	@RequestMapping("shop/shopdetail/{id}")
	private String shopdetail(Model model,@PathVariable("id") Integer id) {
		// TODO Auto-generated method stub
		model.addAttribute("fruits", this.fruitsService.findById(id));
		model.addAttribute("page", "shopdetail");
		return "home/pagesh/shopdetail";

	}
}
