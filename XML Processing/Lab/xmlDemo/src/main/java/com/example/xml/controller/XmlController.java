package com.example.xml.controller;

import com.example.xml.domain.dto.AddressDto;
import com.example.xml.domain.dto.AddressesDto;
import com.example.xml.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Component
public class XmlController implements CommandLineRunner {
    private final AddressService addressService;

    @Autowired
    public XmlController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void run(String... strings) throws Exception {
        //fromClassToXml();
        //fromXmlToClass();
    }

    private void fromClassToXml() throws JAXBException {
        AddressesDto addressesDto = new AddressesDto();
        addressesDto.setAddressDtos(this.addressService.getAddressDtos());
        JAXBContext context = JAXBContext.newInstance(AddressesDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(addressesDto, writer);
        System.out.println(writer);
    }

    private void fromXmlToClass() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(AddressDto.class);
        InputStream inputStream = new FileInputStream("C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Lab\\xmlDemo\\src\\main\\resources\\xmls\\address.xml");
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AddressDto addressDto = (AddressDto) unmarshaller.unmarshal(bfr);
        System.out.println(addressDto);
    }
}
