package com.bkap.Controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bkap.entities.Account;
import com.bkap.service.AccountService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/login")
	public String loginHome() {
		return "home/pagesh/loginh";
	}
	@RequestMapping(value ="/register", method = RequestMethod.GET)
	public String register(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "home/pagesh/register";
	}
	
	@RequestMapping(value = "/register/submit", method = RequestMethod.POST)
	public String addRegister(Model model, RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("account") Account account, BindingResult result) {
		 if (result.hasErrors()) {
		        // Nếu có lỗi validate, trả về trang đăng ký và hiển thị thông báo lỗi
		        model.addAttribute("msg", "Registration failed. Please check your input.");
		        return "register";
		    }
	    if (this.accountService.insert(account)) {
	        redirectAttributes.addFlashAttribute("msg", "Registration successful");
	        return "redirect:/login";
	    } else {
	        model.addAttribute("msg", "Registration failed");
	        return "register";
	    }
	}
	@RequestMapping(value = "/login/submit", method = RequestMethod.POST)
	public void logon(HttpServletResponse response, Authentication authentication ) throws IOException {
		String redirectUrl = "/home";
	    if (authentication.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	        redirectUrl = "/admin";
	    } else if (authentication.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
	        redirectUrl = "/home";
	    }
	    response.sendRedirect(redirectUrl);  
			
	}
	@RequestMapping("/admin/account")
	public String AccountL(Model model) {
		List<Account> accountL = this.accountService.getAll();
		System.out.println("Số lượng tài khoản: " + accountL.size());
		model.addAttribute("accountL", accountL);
		return "admin/pages/accountL";
	}

	@RequestMapping(value = "/admin/account-edit/{id}", method = RequestMethod.GET )
	public String Accountedit(Model model , @PathVariable("id") Integer id) {
		Account account = this.accountService.findById(id);
		model.addAttribute("acc", account);
		return "admin/pages/accountEdit";
	}
	@RequestMapping(value = "/admin/account-edit/{id}", method = RequestMethod.POST)
	public String AccounteditSave(Model model, @ModelAttribute("account") Account acc) {
		Account existingAccount = this.accountService.findById(acc.getAccountId());
		if (!acc.getPassword().isEmpty()) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String hashedPassword = passwordEncoder.encode(acc.getPassword());
	        acc.setPassword(hashedPassword);
	    } else {
	        // Nếu không nhập mật khẩu mới, giữ nguyên mật khẩu cũ
	        acc.setPassword(existingAccount.getPassword());
	    }

	    if (this.accountService.update(acc)) {
	        model.addAttribute("msg", "Cập nhật thành công!");
	        return "redirect:/admin/account";
	    } else {
	        model.addAttribute("msg", "Cập nhật thất bại!");
	        return "redirect:/admin/account";
	    }
	}
}
