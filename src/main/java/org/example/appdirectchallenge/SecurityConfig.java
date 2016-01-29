package org.example.appdirectchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InMemoryConsumerDetailsService;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Collections;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityConfig() {
    }

    @Configuration
    @Order(1)
    public static class AppDirectApiSecurityConfig extends WebSecurityConfigurerAdapter {
        @Value("${oauth.consumer.key}")
        private String consumerKey;
        @Value("${oauth.consumer.secret}")
        private String consumerSecret;

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.antMatcher("/api/notification/**").authorizeRequests().anyRequest().authenticated().and()
                    .addFilterAfter(oauthProviderProcessingFilter(), LogoutFilter.class);
        }

        @Bean
        public FilterRegistrationBean registration(final OAuthProviderProcessingFilter filter) {
            // prevents OAuth filter to be registered as a general purpose filter to be applied to all requests;
            // will only be considered by Spring Security filter chain

            final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
            registration.setEnabled(false);

            return registration;
        }

        @Bean
        public OAuthProviderProcessingFilter oauthProviderProcessingFilter() {
            // configures OAuth signed-fetch filter
            final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter();
            filter.setIgnoreMissingCredentials(false);

            return filter;
        }

        @Bean
        public ConsumerDetailsService oauthConsumerDetailsService() {
            final InMemoryConsumerDetailsService service = new InMemoryConsumerDetailsService();
            service.setConsumerDetailsStore(oauthConsumerDetailsStore());

            return service;
        }

        private Map<String, ConsumerDetails> oauthConsumerDetailsStore() {
            return Collections.singletonMap(consumerKey, oauthConsumerDetails());
        }

        private ConsumerDetails oauthConsumerDetails() {
            final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
            consumerDetails.setConsumerKey(consumerKey);
            consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));

            // required so that signed-fetch by AppDirect works;
            // otherwise, ProtectedResourceProcessingFilter fails because of missing OAuth token
            consumerDetails.setRequiredToObtainAuthenticatedToken(false);

            consumerDetails.getAuthorities().add(appdirectAuthority());

            return consumerDetails;
        }

        private SimpleGrantedAuthority appdirectAuthority() {
            return new SimpleGrantedAuthority("APPDIRECT");
        }

        @Bean
        public OAuthProviderTokenServices oauthProviderTokenServices() {
            return new InMemoryProviderTokenServices();
        }

        @Bean
        public ProtectedResourceDetails protectedResourceDetails() {
            final BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
            resource.setConsumerKey(consumerKey);
            resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));

            return resource;
        }
    }
}