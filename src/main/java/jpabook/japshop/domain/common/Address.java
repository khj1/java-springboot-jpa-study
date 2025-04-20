package jpabook.japshop.domain.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Address {

    private final String city;
    private final String street;
    private final String zipCode;
}
