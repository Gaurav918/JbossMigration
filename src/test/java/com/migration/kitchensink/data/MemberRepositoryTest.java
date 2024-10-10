package com.migration.kitchensink.data;

import com.jayway.jsonpath.Criteria;
import com.migration.kitchensink.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberRepositoryTest {

    @InjectMocks
    private MemberRepository memberRepository;

    @Mock
    private EntityManager entityManager;
    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Member> criteriaQuery;
    @Mock
    private Criteria criteria;

    @Mock
    private TypedQuery<Member> typedQuery;
    @Mock
    private Root<Member> root;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Member.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Member.class)).thenReturn(root);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Member member = new Member();
        member.setId(id);
        when(entityManager.find(Member.class, id)).thenReturn(member);

        // Act
        Member result = memberRepository.findById(id);

        // Assert
        assertEquals(member, result);
        verify(entityManager, times(1)).find(Member.class, id);
    }

//    @Test
//    void testFindByEmail() {
//        // Arrange
//        String email = "test@example.com";
//        String name = "test";
//        String phoneNumber="1234567890";
//        Member member = new Member();
//        member.setEmail(email);
//        member.setPhoneNumber(phoneNumber);
//        member.setName(name);
//        member.setId(Long.parseLong("1"));
////        when(entityManager.getCriteriaBuilder()).thenReturn(mock(CriteriaBuilder.class));
//        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
//        when(typedQuery.getSingleResult()).thenReturn(member);
//        when(memberRepository.findByEmail(email)).thenReturn(member);
//
//        // Act
//        Member result = memberRepository.findByEmail(email);
//
//        // Assert
//        assertEquals(member, result);
//        verify(entityManager, times(1)).createQuery(any(CriteriaQuery.class));
//    }
//
//    @Test
//    void testFindAllOrderedByName() {
//        // Arrange
//        Member member1 = new Member();
//        member1.setName("Alice");
//        Member member2 = new Member();
//        member2.setName("Bob");
//        List<Member> members = Arrays.asList(member1, member2);
//
//        when(entityManager.getCriteriaBuilder()).thenReturn(mock(CriteriaBuilder.class));
//        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
//        when(typedQuery.getResultList()).thenReturn(members);
//        when(memberRepository.findAllOrderedByName()).thenReturn(members);
//
//        // Act
//        List<Member> result = memberRepository.findAllOrderedByName();
//
//        // Assert
//        assertEquals(2, result.size());
//        assertEquals("Alice", result.get(0).getName());
//        assertEquals("Bob", result.get(1).getName());
//        verify(entityManager, times(1)).createQuery(any(CriteriaQuery.class));
//    }
}
