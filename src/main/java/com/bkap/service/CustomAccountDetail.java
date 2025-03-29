package com.bkap.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bkap.entities.Account;

public class CustomAccountDetail implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Account account;

    public CustomAccountDetail(Account account) {
        this.account = account;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		 String role = "ROLE_" + account.getRole(); 
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return account.getUserName();
	}

}
