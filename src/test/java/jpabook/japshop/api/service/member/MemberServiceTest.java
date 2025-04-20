package jpabook.japshop.api.service.member;

import jpabook.japshop.api.service.member.request.MemberCreateRequest;
import jpabook.japshop.api.service.member.response.MemberCreateResponse;
import jpabook.japshop.api.service.member.response.MemberResponse;
import jpabook.japshop.domain.common.Address;
import jpabook.japshop.domain.member.Member;
import jpabook.japshop.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 추가한다.")
    @Test
    void createMember() {
        //given
        Address address = new Address("서울", "양화로", "11111");
        MemberCreateRequest request = new MemberCreateRequest("유저A", address);

        //when
        MemberCreateResponse response = memberService.createMember(request);
        Member savedMember = memberRepository.findById(response.memberId())
            .orElseThrow(() -> new AssertionError("회원이 저장되지 않았습니다."));

        //then
        assertThat(response)
            .extracting("memberId")
            .isNotNull();

        assertThat(savedMember)
            .extracting("name", "address")
            .contains("유저A", address);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAll() {
        //given
        Address addressA = new Address("cityA", "streetA", "11111");
        Address addressB = new Address("cityA", "streetA", "11111");
        Address addressC = new Address("cityA", "streetA", "11111");

        Member memberA = createMember("유저A", addressA);
        Member memberB = createMember("유저B", addressB);
        Member memberC = createMember("유저C", addressC);
        memberRepository.saveAll(List.of(memberA, memberB, memberC));

        //when
        List<MemberResponse> response = memberService.findAll();

        //then
        assertThat(response).hasSize(3)
            .extracting("name", "address")
            .containsExactlyInAnyOrder(
                tuple("유저A", addressA),
                tuple("유저B", addressB),
                tuple("유저C", addressC)
            );
    }

    @DisplayName("회원 ID로 단일 회원을 조회한다.")
    @Test
    void findOne() {
        //given
        Address address = new Address("cityA", "streetA", "11111");
        Member member = createMember("유저A", address);

        Member savedMember = memberRepository.save(member);

        //when
        MemberResponse response = memberService.findOne(savedMember.getId());

        //then
        assertThat(response)
            .extracting("memberId", "name", "address")
            .contains(
                savedMember.getId(),
                member.getName(),
                member.getAddress()
            );
    }

    private Member createMember(String name, Address address) {
        return Member.builder()
            .name(name)
            .address(address)
            .build();
    }
}