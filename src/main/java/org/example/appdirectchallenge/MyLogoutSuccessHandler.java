package org.example.appdirectchallenge;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler /*extends AbstractAuthenticationTargetUrlRequestHandler*/ implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final String logoutUrl = "https://jacefoil.byappdirect.com/applogout?openid=%s";
        final String openId = ((User) authentication.getPrincipal()).getUsername();
        response.sendRedirect(String.format(logoutUrl, openId));
    }
}