package com.bkap.reponsistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bkap.entities.Account;

public interface AccountReposistory extends JpaRepository<Account, Integer> {

	@Query("FROM Account a WHERE a.UserName = :userName")
	Optional<Account> findByUserName(String userName);
	@Query("SELECT AccountId FROM Account WHERE UserName = :userName")
	Integer getAccountIdByUserName(String userName);
}
