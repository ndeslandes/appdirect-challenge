package org.example.appdirectchallenge.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User) {
            String openIdUrl = ((User) authentication.getPrincipal()).getUsername();
            response.sendRedirect(String.format("https://www.appdirect.com/applogout?openid=%s", openIdUrl));
        }
    }
}