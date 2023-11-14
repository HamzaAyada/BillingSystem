package com.example.billingsystem.dataLoader;

import com.example.billingsystem.repository.ClientDTO;
import com.example.billingsystem.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final ClientService clientService;

    @Autowired
    public DataLoader(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("data/dataClients.json");
            File file = resource.getFile();

            ClientDTO[] clientDTOArray = objectMapper.readValue(file, ClientDTO[].class);
            List<ClientDTO> clientDTOList = Arrays.asList(clientDTOArray);

            clientService.addClientsFromJson(clientDTOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
