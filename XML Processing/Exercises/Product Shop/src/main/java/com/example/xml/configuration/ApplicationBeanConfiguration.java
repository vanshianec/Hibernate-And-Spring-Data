package com.example.xml.configuration;

import com.example.xml.util.FileUtilImpl;
import com.example.xml.util.ValidatorUtilImpl;
import com.example.xml.util.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtilImpl fileUtilImpl() {
        return new FileUtilImpl();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtilImpl validatorUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    XmlParserImpl xmlParser() {
        return new XmlParserImpl();
    }
}
