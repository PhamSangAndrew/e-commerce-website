package com.bkap.reponsistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkap.entities.Banner;

public interface BannerReposistory extends JpaRepository<Banner, Integer>{

}
