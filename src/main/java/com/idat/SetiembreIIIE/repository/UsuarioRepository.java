package com.idat.SetiembreIIIE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.SetiembreIIIE.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByUsuario(String usuario);
}
