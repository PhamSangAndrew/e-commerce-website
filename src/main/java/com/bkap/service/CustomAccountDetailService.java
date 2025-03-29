package com.bkap.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bkap.entities.Account;
import com.bkap.reponsistory.AccountReposistory;

@Service
public class CustomAccountDetailService implements UserDetailsService{
 
	private final AccountReposistory accountReposistory;
	public CustomAccountDetailService(AccountReposistory accountRepository) {
        this.accountReposistory = accountRepository;
    }

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Account account = accountReposistory.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		 String role = "ROLE_" + account.getRole();
		   
		return new User(account.getUserName(), account.getPassword(), Collections.singleton(new SimpleGrantedAuthority(role)));
	}
	 
	
	 
	
	

	
}
