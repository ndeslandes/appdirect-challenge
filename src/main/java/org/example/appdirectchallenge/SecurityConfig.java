package org.example.appdirectchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class AppDirectApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Value("${oauth.consumer.key}")
        private String consumerKey;

        @Value("${oauth.consumer.secret}")
        private String consumerSecret;

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.antMatcher("/api/notification/**").authorizeRequests().anyRequest().authenticated();
        }

        @Bean
        public ProtectedResourceDetails createProtectedResourceDetails() {
            BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
            resource.setConsumerKey(consumerKey);
            resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
            return resource;
        }
    }
}