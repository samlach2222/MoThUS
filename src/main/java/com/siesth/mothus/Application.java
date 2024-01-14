package com.siesth.mothus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main class of the application
 */
@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:env.properties")
@ImportResource(value = "classpath:spring/application-config.xml")
public class Application {
    /**
     * Main method of the application
     * @param args The arguments of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
