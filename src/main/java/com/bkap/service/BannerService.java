package com.bkap.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bkap.entities.Banner;


public interface BannerService {
	Page<Banner> getAll(int pageNo);
	Banner FindById(int Id);
	Boolean insert(Banner banner);
	Boolean update(Banner banner);
	Boolean delete(Integer Id);

}
