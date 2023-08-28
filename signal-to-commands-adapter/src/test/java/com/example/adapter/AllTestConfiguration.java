package com.example.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class AllTestConfiguration {

  @Bean
  ObjectMapper getObjectMapperForTests() {
    return new ObjectMapper();
  }
}
