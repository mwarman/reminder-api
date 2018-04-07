package com.example.reminderapi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.example.reminderapi.service.UserService;

@Configuration
public class SecurityConfiguration {

    private static final String CLIENT_ID = "ReminderClient";
    private static final String RESOURCE_ID = "reminder-api";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1800;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 3600;
    private static final String DEFAULT_JWT_SIGNING_KEY = "aBcDeFgHi0123456789";

    @Autowired
    private Environment env;

    @Autowired
    private LocalUserAuthenticationProvider localAuthentictionProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<AuthenticationProvider>();
        authenticationProviders.add(localAuthentictionProvider);

        return new ProviderManager(authenticationProviders);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(
                env.getProperty("jwt.signing.key", DEFAULT_JWT_SIGNING_KEY));
        return tokenConverter;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration
            extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private JwtAccessTokenConverter tokenConverter;

        @Autowired
        private JwtTokenStore tokenStore;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private LocalUserDetailsService localUserDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients)
                throws Exception {
            // @formatter:off
            
            clients
                .inMemory()
                    .withClient(SecurityConfiguration.CLIENT_ID)
                        .authorizedGrantTypes("password", "refresh_token")
                        .scopes("read", "write")
                        .authorities("ROLE_CLIENT")
                        .resourceIds(SecurityConfiguration.RESOURCE_ID)
                        .accessTokenValiditySeconds(SecurityConfiguration.ACCESS_TOKEN_VALIDITY_SECONDS)
                        .refreshTokenValiditySeconds(SecurityConfiguration.REFRESH_TOKEN_VALIDITY_SECONDS);
                    
            
            // @formatter:on
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {

            endpoints.accessTokenConverter(tokenConverter)
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(localUserDetailsService);

        }

    }

    @EnableResourceServer
    protected static class ResourceServerConfiguration
            extends ResourceServerConfigurerAdapter {

        @Autowired
        private JwtTokenStore tokenStore;

        @Autowired
        private UserService userService;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources)
                throws Exception {

            resources.tokenStore(tokenStore)
                    .resourceId(SecurityConfiguration.RESOURCE_ID);

        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            
            http
                .requestMatchers().antMatchers("/api/**")
                
                .and()
                .authorizeRequests()
                
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api").permitAll()
                .antMatchers("/api/**").authenticated()

                .and()
                .csrf()
                    .disable()
                .addFilterAfter(new RequestContextSecurityPersistenceFilter(userService), FilterSecurityInterceptor.class)
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
            // @formatter: on
        }
        
    }

}
