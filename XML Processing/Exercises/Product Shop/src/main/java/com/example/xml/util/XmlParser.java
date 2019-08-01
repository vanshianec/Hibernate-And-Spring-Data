package com.example.xml.util;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

public interface XmlParser {
    Object parseXml(String xml, Class entityClass) throws JAXBException;

    String parseObject(Object object, Class entityClass) throws JAXBException;
}
