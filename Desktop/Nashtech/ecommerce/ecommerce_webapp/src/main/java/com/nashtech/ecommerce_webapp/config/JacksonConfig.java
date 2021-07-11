package com.nashtech.ecommerce_webapp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    public ObjectMapper configureObjectMapper(){
        //call an object mapper
        ObjectMapper objMapper = new ObjectMapper();
        //configure method
        objMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        return objMapper;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
