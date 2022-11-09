package com.idat.SetiembreIIIE.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(username.equals("diego@gmail.com")) {
			return new User("diego@gmail.com", new BCryptPasswordEncoder().encode("123"), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("El usuario " + username + "no existe.");
		}
		
	}

	
}
