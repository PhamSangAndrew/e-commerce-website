package com.bkap.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

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
import com.bkap.entities.Banner;
import com.bkap.service.BannerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BannerController {
	@Autowired
	BannerService bannerService;
	
	private static final String UPLOAD_DERECTORY = "C:\\Users\\Dell\\eclipse-workspace\\TemmePsa-2\\Upload\\";
	
	
	@RequestMapping("/admin/Banner")
	public String listBanner(Model model,@RequestParam(name="pageNo", defaultValue = "1")Integer pageNo) {	
		Page<Banner> listBanner = this.bannerService.getAll(pageNo);
		model.addAttribute("totalPage", listBanner.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listBanner", listBanner);
		return "/admin/pages/BannerA";
	}
	@RequestMapping("/admin/addBanner")
	public String addBanner(Model model) {	
		Banner banner = new Banner();
		model.addAttribute("banner", banner);
		return "/admin/pages/insertBanner";
		
	
	}
	@RequestMapping(value = "/admin/addBanner/submit-banner", method = RequestMethod.POST)
	public String saveproduct(@ModelAttribute("banner") Banner banner, @RequestParam("file") MultipartFile file,
			HttpServletRequest req, Model model, RedirectAttributes redirectAttributes, @RequestParam("CreatedAt") String createdAtStr,
            @RequestParam("UpdatedAt") String updatedAtStr) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("mgs", "no file");
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        banner.setCreatedAt(formatter.parse(createdAtStr));
	        banner.setUpdatedAt(formatter.parse(updatedAtStr));
			final Path directory = Paths.get(UPLOAD_DERECTORY);
			final Path filePath = Paths.get(UPLOAD_DERECTORY + file.getOriginalFilename());

			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}
			
			banner.setImageUrl(file.getOriginalFilename());
			Files.write(filePath, file.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		if (this.bannerService.insert(banner)) {
			model.addAttribute("msg", "Success message");
			return "redirect:/admin/Banner";
		} else {
			model.addAttribute("msg", "Failure message");
			return "redirect:/admin/addBanner";
		}

	}
	@RequestMapping(value = "/admin/banneredit/{id}", method = RequestMethod.GET)
	public String productEdit(Model model, @PathVariable("id") int id) {
		Banner banner = this.bannerService.FindById(id);
		model.addAttribute("banner", banner);
		return "admin/pages/editBanner";
	}
	@RequestMapping(value = "/admin/banneredit/{id}", method = RequestMethod.POST)
	public String productEditSave(Model model, @PathVariable("id") int id, @ModelAttribute("banner") Banner banner,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, String OldImage) {
		banner.setId(id);
		
		
		boolean isFileUploaded = file != null && !file.isEmpty();

		if (!isFileUploaded) {
		    // Không thay đổi file ảnh, giữ nguyên ảnh cũ
		    banner.setImageUrl(OldImage);
		    redirectAttributes.addFlashAttribute("mgs", "No file uploaded, keeping existing image.");
		} else {
		    try {
		        // Có file mới
		        if (OldImage != null && !OldImage.isEmpty()) {
		            Path deleteImg = Paths.get(UPLOAD_DERECTORY + OldImage);
		            Files.deleteIfExists(deleteImg); // Xóa ảnh cũ nếu có
		        }

		        final Path directory = Paths.get(UPLOAD_DERECTORY);
		        final Path filePath = Paths.get(UPLOAD_DERECTORY + file.getOriginalFilename());

		        if (!Files.exists(directory)) {
		            Files.createDirectory(directory);
		        }

		        banner.setImageUrl(file.getOriginalFilename());
		        Files.write(filePath, file.getBytes());
		        redirectAttributes.addFlashAttribute("mgs", "File uploaded and image updated successfully.");
		    } catch (Exception e) {
		        e.printStackTrace();
		        redirectAttributes.addFlashAttribute("mgs", "Error while uploading the file.");
		    }
		}
		if (this.bannerService.update(banner)) {
			return "redirect:/admin/Banner";
		} else {
			return "/admin/pages/productEdit";

		}

	}
	@RequestMapping(value = "/admin/banner-delete/{id}", method = RequestMethod.GET)
	public String deleteProduct(Model model, @PathVariable("id") Integer id) {
		if (this.bannerService.delete(id)) {
			return "redirect:/admin/Banner";
		} else {
			return "redirect:/admin/Banner";
		}
	}


}
