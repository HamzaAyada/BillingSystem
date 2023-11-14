package com.example.billingsystem.dataLoader;

import com.example.billingsystem.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoaderConfig {
    @Bean
    public DataLoader dataLoader(ClientService clientService) {
        return new DataLoader(clientService);
    }
}
