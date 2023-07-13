package org.lion.serviceclient.config;


import org.lion.service.ToDoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfig {

    @Bean
    WebClient webClient(){
        return WebClient.builder().baseUrl("http://localhost:9898").build();
    }

    @Bean
    ToDoService toDoService(){
        HttpServiceProxyFactory httpServiceProxyFactory=HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient()))
                .build();
        return httpServiceProxyFactory.createClient(ToDoService.class);
    }
}
