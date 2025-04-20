package jpabook.japshop.api.service.member.response;

import jpabook.japshop.domain.member.Member;

public record MemberCreateResponse(
    Long memberId
) {
    public static MemberCreateResponse of(Member member) {
        return new MemberCreateResponse(
            member.getId()
        );
    }
}
