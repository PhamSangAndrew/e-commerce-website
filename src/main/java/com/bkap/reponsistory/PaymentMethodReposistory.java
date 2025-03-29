package com.bkap.reponsistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkap.entities.PaymentMethod;

public interface PaymentMethodReposistory extends JpaRepository<PaymentMethod, Integer>{

}
