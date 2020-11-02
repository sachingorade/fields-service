package com.test.fields;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FieldsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FieldsServiceApplication.class, args);
    }

}
