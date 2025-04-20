package jpabook.japshop.api.service.member.request;

import jpabook.japshop.domain.common.Address;
import jpabook.japshop.domain.member.Member;

public record MemberCreateRequest(
    String name,
    Address address
) {
    public Member toEntity() {
        return Member.builder()
            .name(name)
            .address(address)
            .build();
    }
}
