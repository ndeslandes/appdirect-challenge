package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private MockMvc mvc;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserService(userRepository)).build();
    }

    @Test
    public void getUsers() throws Exception {
        when(userRepository.getUsers()).thenReturn(Collections.singletonList(new User(1, "Tony", "Stark", "tony.stark@starkindustries.com", "1.0")));

        mvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"firstname\":\"Tony\",\"lastname\":\"Stark\",\"email\":\"tony.stark@starkindustries.com\",\"version\":\"1.0\"}]")));
    }

}
