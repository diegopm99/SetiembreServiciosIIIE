package com.idat.SetiembreIIIE.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.idat.SetiembreIIIE.model.Usuario;
import com.idat.SetiembreIIIE.repository.UsuarioRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repository.findByUsuario(username);
		
		if(usuario != null) {
			List<GrantedAuthority> listGranted = new ArrayList<>(); //lista de roles
			GrantedAuthority granted = new SimpleGrantedAuthority(usuario.getRol());
			listGranted.add(granted);
			return new User(
					usuario.getUsuario(), 
					usuario.getContresenia(), 
					listGranted);
		} else {
			throw new UsernameNotFoundException("El usuario: " + username + " no existe.");
		}
	}
	
}
