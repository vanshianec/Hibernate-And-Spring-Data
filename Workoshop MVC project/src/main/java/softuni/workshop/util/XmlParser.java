package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O importXml(Class<O> classObject, String path) throws JAXBException;

    <O> void exportXml(O entity,String path) throws JAXBException;
}
