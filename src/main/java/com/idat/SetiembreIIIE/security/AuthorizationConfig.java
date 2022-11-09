package com.idat.SetiembreIIIE.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private TokenStore tokenStore; //Para almacenar la llave
	
	@Autowired
	private AuthenticationManager authenticationManager; //Para la auntenticación

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		super.configure(security);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//Aqui se establece el user y password
		clients.inMemory()
		.withClient("diegoperez") //user
		.secret(new BCryptPasswordEncoder().encode("12345")) //password
		.authorizedGrantTypes("password", "authorization_code", "refresh_token") //roles
		.scopes("read", "write", "trust") //indica que operaciones puede realizar
		.accessTokenValiditySeconds(3600) //tiempo que dura el token
		.refreshTokenValiditySeconds(3600); //en cuanto tiempo se refrezca el token
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
		.authenticationManager(authenticationManager);
	}
	
}
