package com.example.billingsystem.service;

import com.example.billingsystem.entity.Client;
import com.example.billingsystem.entity.Invoice;
import com.example.billingsystem.exception.ClientNotFoundException;
import com.example.billingsystem.repository.ClientRepository;
import com.example.billingsystem.utils.EnergyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class BillingService {

    private static final String PARTICULIER = "particulier";
    private static final String PRO = "pro";

    private final ClientRepository clientRepository;

    @Autowired
    public BillingService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Invoice calculateInvoice(String clientRef, double totalConsumedKWh) {
        Optional<Client> clientOptional = clientRepository.findByReferenceClient(clientRef);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            Invoice invoice = new Invoice();
            invoice.setClientId(client.getId());
            invoice.setClientReference(client.getReferenceClient());
            invoice.setClientType(client.getType());
            invoice.setEnergyType(client.getEnergyType());
            invoice.setConsumedKWh(totalConsumedKWh);

            double unitPrice = calculateUnitPrice(client);
            BigDecimal roundedUnitPrice = BigDecimal.valueOf(unitPrice)
                    .setScale(3, RoundingMode.HALF_UP);
            invoice.setUnitPrice(roundedUnitPrice.doubleValue());

            double totalAmount = totalConsumedKWh * unitPrice;
            BigDecimal roundedTotalAmount = BigDecimal.valueOf(totalAmount)
                    .setScale(3, RoundingMode.HALF_UP);

            invoice.setTotalAmount(roundedTotalAmount.doubleValue());
            return invoice;
        } else {
            throw new ClientNotFoundException("Client not found with Reference: " + clientRef);
        }
    }

    private double calculateUnitPrice(Client client) {
        double electricityUnitPrice = 0.0;
        double gasUnitPrice = 0.0;

        // Appliquer les règles de tarification en fonction du type de client et du type d'énergie
        if (PARTICULIER.equals(client.getType())) {
            electricityUnitPrice = 0.133;
            gasUnitPrice = 0.108;
        } else if (PRO.equals(client.getType())) {
            double ca = client.getCa();

            if (ca > 1000000) {
                electricityUnitPrice = 0.110;
                gasUnitPrice = 0.123;
            } else {
                electricityUnitPrice = 0.112;
                gasUnitPrice = 0.117;
            }
        }

        if (EnergyType.ELECTRICITY.toString().equals(client.getEnergyType())) {
            return electricityUnitPrice;
        } else if (EnergyType.GAZ.toString().equals(client.getEnergyType())) {
            return gasUnitPrice;
        } else if (EnergyType.ELECTRICITY_GAZ.toString().equals(client.getEnergyType())) {
            return gasUnitPrice + electricityUnitPrice;
        }

        return 0.0;
    }

}

