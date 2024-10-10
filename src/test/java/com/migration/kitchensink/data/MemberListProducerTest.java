package com.migration.kitchensink.data;

import com.migration.kitchensink.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MemberListProducerTest {

    @InjectMocks
    private MemberListProducer memberListProducer;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllMembersOrderedByName() {
        // Arrange
        Member member1 = new Member();
        member1.setId(Long.parseLong("1"));
        member1.setName("saurav");
        member1.setEmail("123@gmail.com");
        member1.setPhoneNumber("1234567890");
        Member member2 = new Member();
        member2.setId(Long.parseLong("2"));
        member2.setName("bhairav");
        member2.setEmail("321@gmail.com");
        member2.setPhoneNumber("2345678901");
        List<Member> mockMembers = Arrays.asList(member1, member2);

        // Mock the behavior of memberRepository
        when(memberRepository.findAllOrderedByName()).thenReturn(mockMembers);

        // Act
        memberListProducer.retrieveAllMembersOrderedByName();

        // Assert
        List<Member> members = memberListProducer.getMembers();
        assertEquals(2, members.size());
        assertEquals("saurav", members.get(0).getName());
        assertEquals("bhairav", members.get(1).getName());
    }

    @Test
    public void testOnMemberListChanged() {
        // Arrange
        Member member = new Member();
        member.setId(Long.parseLong("1"));
        member.setName("saurav");
        member.setEmail("123@gmail.com");
        member.setPhoneNumber("1234567890");
        List<Member> mockMembers = Arrays.asList(member);
        when(memberRepository.findAllOrderedByName()).thenReturn(mockMembers);

        // Act
        memberListProducer.onMemberListChanged(member);

        // Assert
        List<Member> members = memberListProducer.getMembers();
        assertEquals(1, members.size());
        assertEquals("saurav", members.get(0).getName());
    }
}
