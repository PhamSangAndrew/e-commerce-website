package com.bkap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bkap.entities.Account;
import com.bkap.reponsistory.AccountReposistory;
@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountReposistory accountReposistory;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public List<Account> getAll() {
		// TODO Auto-generated method stub
		return this.accountReposistory.findAll();
	}

	@Override
	public Optional<Account> findByUserName(String userName) {
		// TODO Auto-generated method stub
		if (userName == null || userName.isEmpty()) {
	        return null; // Or some other handling
	    }

	    // Ensure the recursion is well-structured or stopped
	    return accountReposistory.findByUserName(userName);
	}

	@Override
	public Account findById(Integer AccountId) {
		// TODO Auto-generated method stub
		return this.accountReposistory.findById(AccountId).get();
	}

	@Override
	public Boolean insert(Account acc) {
		// TODO Auto-generated method stub
		try {
			String encodedPassword = passwordEncoder.encode(acc.getPassword());
	        acc.setPassword(encodedPassword);  // Gán mật khẩu đã mã hóa vào đối tượng Account
			this.accountReposistory.save(acc);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			 return false;
		}
		
	}

	@Override
	public Boolean update(Account acc) {
		// TODO Auto-generated method stub
		try {
			this.accountReposistory.save(acc);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public Boolean delete(Integer AccountId) {
		// TODO Auto-generated method stub
		try {
			this.accountReposistory.delete(findById(AccountId));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public Integer getAccountIdByUserName(String userName) {
		// TODO Auto-generated method stub
		return this.accountReposistory.getAccountIdByUserName(userName);
	}

}
