//package com.migration.kitchensink.model;
//
//import com.migration.kitchensink.model.Member;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MemberTest {
//
//    private Validator validator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    public void testValidMember() {
//        Member member = new Member();
//        member.setName("John Doe");
//        member.setEmail("john.doe@example.com");
//        member.setPhoneNumber("1234567890");
//
//        Set<ConstraintViolation<Member>> violations = validator.validate(member);
//        assertTrue(violations.isEmpty(), "Expected no constraint violations");
//    }
//
//    @Test
//    public void testInvalidName() {
//        Member member = new Member();
//        member.setName("123"); // Invalid name (contains numbers)
//        member.setEmail("john.doe@example.com");
//        member.setPhoneNumber("1234567890");
//
//        Set<ConstraintViolation<Member>> violations = validator.validate(member);
//        assertFalse(violations.isEmpty(), "Expected constraint violations for name");
//    }
//
//    @Test
//    public void testInvalidEmail() {
//        Member member = new Member();
//        member.setName("John Doe");
//        member.setEmail("invalid-email"); // Invalid email format
//        member.setPhoneNumber("1234567890");
//
//        Set<ConstraintViolation<Member>> violations = validator.validate(member);
//        assertFalse(violations.isEmpty(), "Expected constraint violations for email");
//    }
//
//    @Test
//    public void testInvalidPhoneNumber() {
//        Member member = new Member();
//        member.setName("John Doe");
//        member.setEmail("john.doe@example.com");
//        member.setPhoneNumber("123"); // Invalid phone number (too short)
//
//        Set<ConstraintViolation<Member>> violations = validator.validate(member);
//        assertFalse(violations.isEmpty(), "Expected constraint violations for phone number");
//    }
//
//    @Test
//    public void testEmailUniqueness() {
//        // Assuming that the uniqueness check is handled in the service layer
//        // You would typically test this in the service or repository layer.
//    }
//}
//
