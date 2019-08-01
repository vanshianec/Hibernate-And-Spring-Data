package com.example.xml.util.impl;

import com.example.xml.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlParserImpl implements XmlParser {


    @Override
    public Object parseXml(String xml, Class entityClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return unmarshaller.unmarshal(reader);
    }

    @Override
    public String parseObject(Object object, Class entityClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(entityClass);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(object, writer);
        return writer.toString();
    }
}
