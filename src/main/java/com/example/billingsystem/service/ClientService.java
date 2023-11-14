package com.example.billingsystem.service;

import com.example.billingsystem.entity.Client;
import com.example.billingsystem.repository.ClientDTO;
import com.example.billingsystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClientsFromJson(List<ClientDTO> clientDTOList) {
        clientDTOList.stream()
                .map(this::convertDtoToEntity)
                .forEach(clientRepository::save);
    }

    private Client convertDtoToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setReferenceClient(clientDTO.getReferenceClient());
        client.setType(clientDTO.getType());
        client.setEnergyType(clientDTO.getEnergyType().toString());

        // Propriétés spécifiques aux clients Pro
        if ("pro".equals(clientDTO.getType())) {
            client.setSiret(clientDTO.getSiret());
            client.setRaisonSociale(clientDTO.getRaisonSociale());
            client.setCa(clientDTO.getCa());
        }

        // Propriétés spécifiques aux particuliers
        if ("particulier".equals(clientDTO.getType())) {
            client.setCivilite(clientDTO.getCivilite());
            client.setNom(clientDTO.getNom());
            client.setPrenom(clientDTO.getPrenom());
        }

        return client;
    }
}
