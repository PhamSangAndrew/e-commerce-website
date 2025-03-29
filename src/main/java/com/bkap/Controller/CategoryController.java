package com.bkap.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bkap.entities.Category;
import com.bkap.service.CategoryService;

@Controller

public class CategoryController {
	String msgFail = "Update failed!";
	String msgSuccess = "Update successfully!";
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/admin/categories")
	public String category(Model model) {
		List<Category> cate = this.categoryService.getAll();
		model.addAttribute("cate", cate);
		return "admin/pages/productCategory";
	}

	@RequestMapping(value = "/admin/categories/add-category", method = RequestMethod.GET)
	public String addcategory(Model model) {
		Category cate = new Category();
		cate.setCateStatus(true);
		model.addAttribute("cate", cate);
		return "admin/pages/addCategory";

	}

	@RequestMapping(value = "/admin/categories/add-category/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("cate") Category cate, Model model) {
	    
	    if (this.categoryService.insert(cate)) {
	        model.addAttribute("msg", "Success message");
	        return "redirect:/admin/categories";
	    } else {
	        model.addAttribute("msg", "Failure message");
	        return "admin/pages/addCategory";
	    }
	}
	@RequestMapping(value = "/admin/categories/edit-category/{id}", method = RequestMethod.GET)
	public String editcategory(Model model , @PathVariable("id") Integer id) {
		Category cate = this.categoryService.findById(id);
		model.addAttribute("cate", cate);
		return "admin/pages/editCategory";
	}
	
	@RequestMapping(value = "/admin/categories/edit-category/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute("cate") Category cate, @PathVariable("id") Integer id, Model model) {
	    
	       cate.setCategoryID(id);

	    if (this.categoryService.update(cate)) {
	        model.addAttribute("msg", "Cập nhật thành công!");
	        return "redirect:/admin/categories";
	    } else {
	        model.addAttribute("msg", "Cập nhật thất bại!");
	        return "admin/pages/editCategory";
	    }
	}
	@RequestMapping(value = "/admin/categories/delete-category/{id}", method = RequestMethod.GET)
	public String deletecategory(Model model , @PathVariable("id") Integer id) {
		 if (this.categoryService.delete(id)) {
		        model.addAttribute("msg", "Success message");
		        return "redirect:/admin/categories";
		    } else {
		        model.addAttribute("msg", "Failure message");
		        return "/admin/categories";
		    }
	}


}