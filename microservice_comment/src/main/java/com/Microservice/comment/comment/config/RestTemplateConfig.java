package com.Microservice.comment.comment.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //returning rest template object
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}


//Rest Template class can help us to intract with other project