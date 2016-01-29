package org.example.appdirectchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InMemoryConsumerDetailsService;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${oauth.consumer.key}")
    private String consumerKey;
    @Value("${oauth.consumer.secret}")
    private String consumerSecret;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/api/notification/**").authorizeRequests().anyRequest().authenticated().and()
                .addFilterAfter(buildOAuthProviderProcessingFilter(), LogoutFilter.class);
    }

    @Bean
    public FilterRegistrationBean disableOAuthFilterForAllRequests(final OAuthProviderProcessingFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public OAuthProviderProcessingFilter buildOAuthProviderProcessingFilter() {
        ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter();
        filter.setIgnoreMissingCredentials(false);
        return filter;
    }

    @Bean
    public ConsumerDetailsService buildConsumerDetailsService() {
        BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(consumerKey);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);
        consumerDetails.getAuthorities().add(new SimpleGrantedAuthority("APPDIRECT"));

        InMemoryConsumerDetailsService service = new InMemoryConsumerDetailsService();
        service.setConsumerDetailsStore(Collections.singletonMap(consumerKey, consumerDetails));
        return service;
    }

    @Bean
    public OAuthProviderTokenServices buildOAuthProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    @Bean
    public ProtectedResourceDetails buildProtectedResourceDetails() {
        BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(consumerKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
        return resource;
    }

}