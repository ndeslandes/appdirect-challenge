package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
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
public class SubscriptionServiceTest {

    private MockMvc mvc;

    @Mock
    private SubscriptionRepository subscriptions;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new SubscriptionService(subscriptions)).build();
    }

    @Test
    public void list() throws Exception {
        when(subscriptions.list()).thenReturn(Collections.singletonList(new Subscription(1L, "", "", "")));

        mvc.perform(MockMvcRequestBuilders.get("/api/subscriptions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"accountId\":1,\"creatorFirstName\":\"\",\"creatorLastName\":\"\",\"edition\":\"\"}]")));
    }

}
