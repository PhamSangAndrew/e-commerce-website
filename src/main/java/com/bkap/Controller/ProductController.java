package com.bkap.Controller;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bkap.entities.Fruits;
import com.bkap.service.CategoryService;
import com.bkap.service.FruitService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductController {
	private static final String UPLOAD_DERECTORY = "C:\\Users\\Dell\\eclipse-workspace\\TemmePsa-2\\Upload\\";

	@Autowired
	private FruitService fruitsService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/admin/productlist")
	public String productlist(Model model, @RequestParam(name="pageNo", defaultValue = "1")Integer pageNo, @RequestParam(name = "keyword", required = false) String keyword) {
		
		Page<Fruits> fruits;
		if (keyword != null && !keyword.trim().isEmpty()) {
	        fruits = this.fruitsService.searchName(keyword, pageNo);
	    } else {
	        fruits = this.fruitsService.getAll(pageNo);
	    }
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalPage", fruits.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("fruits", fruits);
		return "/admin/pages/productlist";
	}

	@RequestMapping("/admin/add-product")
	public String productAdd(Model model) {
		Fruits fruits = new Fruits();
		model.addAttribute("fruits", fruits);
		model.addAttribute("cate", this.categoryService.getAll());
		return "/admin/pages/productAdd";
	}

	@RequestMapping(value = "/admin/add-product/submit-product", method = RequestMethod.POST)
	public String saveproduct(@ModelAttribute("fruits") Fruits fruits, @RequestParam("file") MultipartFile file,
			HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("mgs", "no file");
		}
		try {
			final Path directory = Paths.get(UPLOAD_DERECTORY);
			final Path filePath = Paths.get(UPLOAD_DERECTORY + file.getOriginalFilename());

			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}
			fruits.setImage(file.getOriginalFilename());
			Files.write(filePath, file.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		if (this.fruitsService.insert(fruits)) {
			model.addAttribute("msg", "Success message");
			return "redirect:/admin/productlist";
		} else {
			model.addAttribute("msg", "Failure message");
			return "/admin/pages/productList";
		}

	}

	@RequestMapping(value = "/admin/productedit/{id}", method = RequestMethod.GET)
	public String productEdit(Model model, @PathVariable("id") Integer id) {
		Fruits fruits = this.fruitsService.findById(id);
		model.addAttribute("fruits", fruits);
		model.addAttribute("cate", this.categoryService.getAll());
		return "admin/pages/productEdit";
	}

	@RequestMapping(value = "/admin/productedit/{id}", method = RequestMethod.POST)
	public String productEditSave(Model model, @PathVariable("id") Integer id, @ModelAttribute("fruits") Fruits fruits,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, String oldImage) {
		fruits.setFruitId(id);
		
		
		 if (file == null || file.isEmpty()) {
		        // Không thay đổi file ảnh, giữ nguyên ảnh cũ
			 fruits.setImage(oldImage);
			 redirectAttributes.addFlashAttribute("mgs", "No file uploaded, keeping existing image.");
		        	
		    } else {
		        // Có file mới
		        try {
		            // Nếu sản phẩm đã có ảnh cũ thì xoá
		            if (fruits.getImage() != null && !fruits.getImage().isEmpty()) {
		                Path deleteImg = Paths.get(UPLOAD_DERECTORY + fruits.getImage());
		                Files.deleteIfExists(deleteImg); // Xóa ảnh cũ nếu có
		            }
		          
		            

		            // Đường dẫn lưu ảnh mới
		            final Path directory = Paths.get(UPLOAD_DERECTORY);
		            final Path filePath = Paths.get(UPLOAD_DERECTORY + file.getOriginalFilename());

		            // Tạo thư mục nếu chưa có
		            if (!Files.exists(directory)) {
		                Files.createDirectory(directory);
		            }
		            
		            // Cập nhật ảnh mới
		            fruits.setImage(file.getOriginalFilename());

		            // Lưu ảnh vào thư mục
		            Files.write(filePath, file.getBytes());

		            redirectAttributes.addFlashAttribute("mgs", "File uploaded and image updated successfully.");

		        } catch (Exception e) {
		            e.printStackTrace();
		            redirectAttributes.addFlashAttribute("mgs", "Error while uploading the file.");
		        }
		    }
		


		if (this.fruitsService.update(fruits)) {
			return "redirect:/admin/productlist";
		} else {
			return "/admin/pages/productEdit";

		}

	}

	@RequestMapping(value = "/admin/product-delete/{id}", method = RequestMethod.GET)
	public String deleteProduct(Model model, @PathVariable("id") Integer id) {
		if (this.fruitsService.delete(id)) {
			return "redirect:/admin/productlist";
		} else {
			return "/admin/productlist";
		}
	}

	@RequestMapping("/admin/productCart")
	public String productCart() {
		return "admin/pages/productCart";
	}

	@RequestMapping("/admin/productPayment")
	public String productPayment() {
		return "admin/pages/productPayment";
	}

	@RequestMapping("/admin/productDetail")
	public String productDetail() {
		return "admin/pages/productDetail";
	}
}
