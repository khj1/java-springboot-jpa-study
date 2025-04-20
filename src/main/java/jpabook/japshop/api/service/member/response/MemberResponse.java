package jpabook.japshop.api.service.member.response;

import jpabook.japshop.domain.common.Address;
import jpabook.japshop.domain.member.Member;

public record MemberResponse(
    Long memberId,
    String name,
    Address address
) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getName(),
            member.getAddress()
        );
    }
}
