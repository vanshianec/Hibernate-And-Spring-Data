package com.example.demo.terminal;

import com.example.demo.domain.dto.AddressDto;
import com.example.demo.domain.dto.PersonDto;
import com.example.demo.domain.model.Person;
import com.example.demo.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Terminal implements CommandLineRunner {

    private static final String JSON = "[\n" +
            "  {\n" +
            "    \"country\": \"Bulgaria\",\n" +
            "    \"city\": \"Sofia\",\n" +
            "    \"street\": \"Mladost 4\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"country\": \"Spain\",\n" +
            "    \"city\": \"Barcelona\",\n" +
            "    \"street\": \"Las Ramblas\"\n" +
            "  }\n" +
            "]";

    private final PersonService personService;

    @Autowired
    public Terminal(PersonService personService) {
        this.personService = personService;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        Person person = this.personService.getById(1);
        PersonDto personDto = new PersonDto(person);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        System.out.println(gson.toJson(personDto));


        AddressDto[] addressDtos = gson.fromJson(JSON, AddressDto[].class);
        for (AddressDto addressDto : addressDtos) {
            System.out.printf("%s %s %s%n", addressDto.getCity(), addressDto.getCountry(), addressDto.getStreet());
        }
    }
}
