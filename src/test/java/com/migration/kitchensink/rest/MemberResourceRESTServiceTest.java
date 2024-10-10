package com.migration.kitchensink.rest;
import com.migration.kitchensink.data.MemberRepository;
import com.migration.kitchensink.model.Member;
import com.migration.kitchensink.service.MemberRegistration;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.ok;

@ExtendWith(MockitoExtension.class)
public class MemberResourceRESTServiceTest {

    @InjectMocks
    private MemberResourceRESTService memberResource;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberRegistration memberRegistration;

    @Mock
    private Set<ConstraintViolation<Member>> violations;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAllMembers() {
        // Arrange
        Member member1 = new Member();
        member1.setName("John Doe");
        Member member2 = new Member();
        member2.setName("Jane Doe");
        when(memberRepository.findAllOrderedByName()).thenReturn(List.of(member1, member2));

        // Act
        List<Member> members = memberResource.listAllMembers();

        // Assert
        assertNotNull(members);
        assertEquals(2, members.size());
        verify(memberRepository, times(1)).findAllOrderedByName();
    }

    @Test
    public void testLookupMemberByIdFound() {
        // Arrange
        Member member = new Member();
        member.setId(1L);
        member.setName("John Doe");
        when(memberRepository.findById(1L)).thenReturn(member);

        // Act
        ResponseEntity<Member> response = memberResource.lookupMemberById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member, response.getBody());
    }

    @Test
    public void testLookupMemberByIdNotFound() {
        // Arrange
        when(memberRepository.findById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Member> response = memberResource.lookupMemberById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

//    @Test
//    public void testCreateMemberSuccess() {
//        // Arrange
//        Member member = new Member();
//        member.setEmail("test@example.com");
//        member.setName("John Doe");
//
//        // Mock the validation process
////        when(memberRegistration.register(any(Member.class))).thenReturn(null);
//        when(memberRepository.findByEmail(member.getEmail())).thenReturn(null);
//
//        // Act
//        ResponseEntity<?> response = memberResource.createMember(member);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

    @Test
    public void testCreateMemberWithValidationError() throws Exception {
        // Arrange
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setName("John Doe");

        // Simulate validation exception
//        when(memberRegistration.register(any(Member.class))).thenThrow(new ConstraintViolationException(violations));

        // Act
        ResponseEntity<?> response = memberResource.createMember(member);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

//    @Test
//    public void testCreateMemberWithUniqueEmailViolation() {
//        // Arrange
//        Member member = new Member();
//        member.setEmail("taken@example.com");
//        member.setName("Jane Doe");
//
//        // Simulate a unique email violation
//        when(memberRepository.findByEmail(member.getEmail())).thenThrow(new ValidationException("Unique Email Violation"));
//
//        // Act
//        ResponseEntity<?> response = memberResource.createMember(member);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertFalse(response.getBody() instanceof HashSet);
//    }

    @Test
    public void testEmailAlreadyExists() {
        // Arrange
        Member member = new Member();
        member.setEmail("exists@example.com");
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(member);

        // Act
        boolean exists = memberResource.emailAlreadyExists(member.getEmail());

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testEmailNotExists() {
        // Arrange
        String email = "notexists@example.com";
        when(memberRepository.findByEmail(email)).thenReturn(null);

        // Act
        boolean exists = memberResource.emailAlreadyExists(email);

        // Assert
        assertFalse(exists);
    }
}
