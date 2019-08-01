package com.example.demo.domain.dto;

import com.example.demo.domain.model.Person;
import com.example.demo.domain.model.PhoneNumber;
import com.google.gson.annotations.Expose;

import java.util.Set;
import java.util.stream.Collectors;

public class PersonDto {
    @Expose
    private String name;
    @Expose
    private Set<String> phoneNumbers;
    @Expose
    private AddressDto address;


    public PersonDto(Person p) {
        this.name = p.getFirstName();
        this.phoneNumbers = p.getPhoneNumbers()
                .stream()
                .map(PhoneNumber::getNumber)
                .collect(Collectors.toSet());
        this.address = new AddressDto(p.getAddress());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
