package com.migration.kitchensink.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.migration.kitchensink.data.MemberListProducer;
import com.migration.kitchensink.model.Member;
import com.migration.kitchensink.service.MemberRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;
@SpringBootTest
@DirtiesContext
public class MemberControllerTest {

    @InjectMocks
    private MemberController memberController; // Class under test

    @Mock
    private MemberRegistration memberRegistration; // Mock of MemberRegistration

    @Mock
    private MemberListProducer mems; // Mock of MemberListProducer

    @Mock
    private Model model; // Mock of Model

    @Mock
    private Member newMember;


    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void testInitial() throws Exception {
        // Given
        Member mem=new Member();
        mem.setId(Long.parseLong("1"));
        mem.setEmail("123@gmail.com");
        mem.setPhoneNumber("1234567890");
        when(mems.getMembers()).thenReturn(List.of(mem));

        // When & Then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("newMember"))
                .andExpect(model().attributeExists("members"));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        // Given
        Member newMember = new Member();
        newMember.setName("John Doe");
        newMember.setEmail("john@example.com");

        // Mocking the behavior
        when(mems.getMembers()).thenReturn(List.of(newMember));
        doNothing().when(memberRegistration).register(any(Member.class));

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "John Doe")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("Registered!", "Registration successful"));

        verify(memberRegistration, times(1)).register(any(Member.class));
    }

    @Test
    public void testRegisterFailure() throws Exception {
        // Given
        doThrow(new RuntimeException("Registration failed")).when(memberRegistration).register(any(Member.class));

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "John Doe")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("Registration failed", "Registration unsuccessful"));

        verify(memberRegistration, times(1)).register(any(Member.class));
    }
}

