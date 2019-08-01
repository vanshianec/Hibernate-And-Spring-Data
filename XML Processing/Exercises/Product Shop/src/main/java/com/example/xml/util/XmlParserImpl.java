package com.example.xml.util;

import javax.xml.bind.*;
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
