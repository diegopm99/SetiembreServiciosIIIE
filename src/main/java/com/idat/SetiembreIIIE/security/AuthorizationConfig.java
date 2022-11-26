package com.idat.SetiembreIIIE.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private JwtTokenStore tokenStore; //Para almacenar la llave
	
	@Autowired
	private AuthenticationManager authenticationManager; //Para la auntenticación
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter; //Para convertir el cuerpo del token

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
		
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(new TokenEnhancer() {
			
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				Map<String, Object> informacionAdicional = new HashMap<>();
				informacionAdicional.put("suscribete", "a mi canal");
				informacionAdicional.put("alumno", "diego");				
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
				token.setAdditionalInformation(informacionAdicional);
				
				return token;
			}
		}, accessTokenConverter));
		
		endpoints.tokenStore(tokenStore)
		.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(chain);
	}
	
}
