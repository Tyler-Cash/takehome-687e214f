package dev.tylercash.takehome.interview687e214f.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix="swipe")
public class SwipeConfiguration {
    private String url;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
