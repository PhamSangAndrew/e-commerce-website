package com.bkap.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkap.service.OderDetailService;
import com.bkap.service.OderService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	OderService oderService;
	@Autowired
	OderDetailService detailService;
	@GetMapping
	public String admin(Model model) {
		long orderCount = oderService.count();
		float sumTotalAmount = detailService.sumTotalAmount();
		
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("sumTotalAmount", sumTotalAmount);
		return "admin/index";
	}
	

}
