package com.bkap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkap.reponsistory.OderDetailReposistory;
@Service
public class OderDetailServiceImp implements OderDetailService{

	@Autowired
	OderDetailReposistory detailReposistory;
	@Override
	public float sumTotalAmount() {
		// TODO Auto-generated method stub
		return detailReposistory.sumTotalAmount();
	}
}
