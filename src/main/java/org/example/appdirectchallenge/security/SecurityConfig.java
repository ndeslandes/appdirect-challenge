package org.example.appdirectchallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.openid.OpenIDLoginConfigurer;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.provider.*;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${oauth.consumer.key}")
    private String consumerKey;

    @Value("${oauth.consumer.secret}")
    private String consumerSecret;

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.{js,html}", "/webjars/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler);

        // deactivate Cross-Site Request Forgery
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated()
                .and()
                .openidLogin()
                .authenticationUserDetailsService(userDetailsService)
                .loginProcessingUrl("/login/openid")
                .permitAll()
                .defaultSuccessUrl("/");

        http.openidLogin().attributeExchange("https://www.appdirect.com/.*")
                .attribute("email").type("http://axschema.org/contact/email").required(true)
                .and().attribute("firstname").type("http://axschema.org/namePerson/first").required(true)
                .and().attribute("lastname").type("http://axschema.org/namePerson/last").required(true);

        http.addFilterBefore(oAuthProviderProcessingFilter(), OpenIDAuthenticationFilter.class);
    }

    @Bean
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter() {

            @Override
            protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {

                if (new AntPathRequestMatcher("/api/notification/**").matches(request)) {
                    OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
                    setAuthenticationEntryPoint(authenticationEntryPoint);
                    String realmName = request.getRequestURL().toString();
                    authenticationEntryPoint.setRealmName(realmName);
                    return true;
                }
                return false;
            }
        };
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(inMemoryProviderTokenServices());

        return filter;
    }

    @Bean
    public ConsumerDetailsService consumerDetailsService() {
        final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(consumerKey);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);

        final InMemoryConsumerDetailsService consumerDetailsService = new InMemoryConsumerDetailsService();
        consumerDetailsService.setConsumerDetailsStore(new HashMap<String, ConsumerDetails>() {{
            put(consumerKey, consumerDetails);
        }});
        return consumerDetailsService;
    }

    @Bean
    public InMemoryProviderTokenServices inMemoryProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    @Bean
    public ProtectedResourceDetails protectedResourceDetails() {
        BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(consumerKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
        return resource;
    }
}