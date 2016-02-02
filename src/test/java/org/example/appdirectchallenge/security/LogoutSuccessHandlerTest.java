package org.example.appdirectchallenge.security;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogoutSuccessHandlerTest {

    private LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandlerImpl();

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Test
    public void handler_onLogoutSuccess() throws IOException, ServletException {
        String openIdUrl = "https://example.org/openid/id/openID";
        User user = new User(openIdUrl, "", Collections.emptyList());
        Authentication authentication = new OpenIDAuthenticationToken(user, Collections.emptyList(), "", Collections.emptyList());
        logoutSuccessHandler.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        verify(httpServletResponse).sendRedirect(String.format("https://example.org/applogout?openid=%s", openIdUrl));
    }

}