package com.example.xml.domain.dto;
import java.io.Serializable;
import java.util.Set;

public class PersonDto implements Serializable {
    private String name;
    private Set<String> phoneNumbers;
    private AddressDto address;

    public PersonDto() {
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
