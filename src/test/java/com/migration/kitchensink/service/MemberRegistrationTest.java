package com.migration.kitchensink.service;

import com.migration.kitchensink.model.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@DirtiesContext
@ExtendWith(MockitoExtension.class)
public class MemberRegistrationTest {

    @InjectMocks
    private MemberRegistration memberRegistration;

    @Mock
    private Logger log;

    @Mock
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterMember() throws Exception {
        // Arrange
        Member member = new Member();
        member.setName("John Doe");

        // Act
        memberRegistration.register(member);

        // Assert
        verify(log).info("Registering John Doe"); // Verify logging
        verify(em).persist(member); // Verify that the member is persisted
    }

    @Test
    public void testRegisterMemberThrowsException() {
        // Arrange
        Member member = new Member();
        member.setName("John Doe");

        // Simulate exception during persist
        doThrow(new RuntimeException("Persistence error")).when(em).persist(any(Member.class));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            memberRegistration.register(member);
        });

        assertEquals("Persistence error", exception.getMessage());
    }
}

