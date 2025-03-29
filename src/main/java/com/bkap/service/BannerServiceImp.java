package com.bkap.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bkap.entities.Banner;
import com.bkap.reponsistory.BannerReposistory;

@Service
public class BannerServiceImp  implements BannerService{

	@Autowired
	BannerReposistory bannerReposistory;
	
	@Override
	public Page<Banner> getAll(int pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 10);
		return this.bannerReposistory.findAll(pageable);
	}

	@Override
	public Banner FindById(int Id) {
		// TODO Auto-generated method stub
		return this.bannerReposistory.findById(Id).get();
	}

	@Override
	public Boolean insert(Banner banner) {
		// TODO Auto-generated method stub
		try {
			this.bannerReposistory.save(banner);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		 
	}

	@Override
	public Boolean update(Banner banner) {
		// TODO Auto-generated method stub
		try {
			this.bannerReposistory.save(banner);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean delete(Integer Id) {
		// TODO Auto-generated method stub
		try {
			this.bannerReposistory.delete(FindById(Id));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
