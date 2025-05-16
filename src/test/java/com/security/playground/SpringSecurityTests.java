package com.security.playground;

import com.security.playground.model.MyUser;
import com.security.playground.repo.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class SpringSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    @Commit
    void setUp() {
        MyUser myUser = new MyUser();
        myUser.setUsername("user");
        myUser.setPassword(passwordEncoder.encode("password"));
        myUser.setRole("USER");
        usersRepository.save(myUser);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testGetAccessToEndPoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("User"));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/get"))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION,
            userDetailsServiceBeanName = "myUserDetailsService")
    @Test
    void testUserDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("User"));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/get"))
                .andExpect(status().isForbidden());
    }

    @WithAnonymousUser
    @Test
    void testAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get"))
                .andExpect(status().isUnauthorized());
    }
}
