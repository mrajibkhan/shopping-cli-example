package com.mrk.shop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Created by Rajib Khan on 26/09/2017
 */

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper yamlObjectMapper() {
        ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory());
        return yamlObjectMapper;
    }
}
