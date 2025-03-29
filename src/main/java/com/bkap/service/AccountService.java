package com.bkap.service;

import java.util.List;
import java.util.Optional;

import com.bkap.entities.Account;

public interface AccountService {
	  List<Account> getAll();
	  Optional<Account> findByUserName(String userName);
	  Account findById(Integer AccountId);
	  Boolean insert(Account acc);
	  Boolean update(Account acc);
	  Boolean delete(Integer AccountId);
	  Integer getAccountIdByUserName(String userName);

}
