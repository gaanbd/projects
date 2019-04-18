package com.tvse.oauth.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.domain.User;
import com.tvse.oauth.repository.UserRepository;

/**
 * @author techmango
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${client-id}")
	private static String clientId = "tvse";

	@Value("${client-secret}")
	private static String clientSecret = "tvse-secret";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userProfileRepository;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new CustomTokenEnhancer();
		converter.setSigningKey("tvse-20t22v19s5e");
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/*
	 * Add custom user principal information to the JWT token
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory().withClient(clientId).secret(clientSecret)
				.authorizedGrantTypes(ApplicationConstants.GRANT_TYPE, ApplicationConstants.AUTHORIZATION_CODE,
						ApplicationConstants.REFRESH_TOKEN, ApplicationConstants.IMPLICIT)
				.scopes(ApplicationConstants.SCOPE_READ, ApplicationConstants.SCOPE_WRITE)
				.accessTokenValiditySeconds(ApplicationConstants.ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(ApplicationConstants.REFRESH_TOKEN_VALIDITY_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients();
	}
	
	class CustomTokenEnhancer extends JwtAccessTokenConverter {
		@Override
		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
			User user = userProfileRepository.findByUserName(authentication.getName());//the value from Username
			Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			info.put("userId", user.getId());
			info.put("username", user.getUserName());
			info.put("email", user.getEmail());
			info.put("userLocale", "en");
			DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);
			return super.enhance(customAccessToken, authentication);
		}
	}
}