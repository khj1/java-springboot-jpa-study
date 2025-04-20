package jpabook.japshop.api.service.member;

import jpabook.japshop.api.service.member.request.MemberCreateRequest;
import jpabook.japshop.api.service.member.response.MemberCreateResponse;
import jpabook.japshop.api.service.member.response.MemberResponse;
import jpabook.japshop.domain.member.Member;
import jpabook.japshop.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public MemberCreateResponse createMember(MemberCreateRequest request) {
        Member member = request.toEntity();
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
            .map(MemberResponse::of)
            .toList();
    }

    public MemberResponse findOne(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return MemberResponse.of(member);
    }
}
