package com.example.billingsystem;

import com.example.billingsystem.entity.Invoice;
import com.example.billingsystem.exception.ClientNotFoundException;
import com.example.billingsystem.repository.ClientRepository;
import com.example.billingsystem.service.BillingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class BillingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner startBillingService(BillingService billingService) {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                // Lire l'ID du client et la consommation totale en kWh depuis l'utilisateur
                System.out.print("Entrez la reference du client : ");
                String clientRef = scanner.next();

                System.out.print("Entrez la consommation totale en kWh : ");
                double totalConsumedKWh = scanner.nextDouble();

                // Appeler la méthode calculateInvoice pour générer la facture
                Invoice invoice = billingService.calculateInvoice(clientRef, totalConsumedKWh);
                System.out.println("Facture générée avec succès :");
                ObjectMapper objectMapper = new ObjectMapper();
                String invoiceJson = objectMapper.writeValueAsString(invoice);
                System.out.println(invoiceJson);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        };
    }

//        Scanner scanner = new Scanner(System.in);
//
//        // Lire l'ID du client et la consommation totale en kWh depuis l'utilisateur
//        System.out.print("Entrez l'ID du client : ");
//        String clientRef = scanner.next();
//
//        System.out.print("Entrez la consommation totale en kWh : ");
//        double totalConsumedKWh = scanner.nextDouble();
//
//        // Initialiser Spring context avec le repository
//        ApplicationContext context = new AnnotationConfigApplicationContext(ClientRepository.class);
//
//        // Créer une instance de BillingService en injectant le ClientRepository
//        BillingService billingService = context.getBean(BillingService.class);
//
//        // Appeler la méthode calculateInvoice pour générer la facture
//        try {
//            Invoice invoice = billingService.calculateInvoice(clientRef, totalConsumedKWh);
//            System.out.println("Facture générée avec succès :");
//            ObjectMapper objectMapper = new ObjectMapper();
//            String invoiceJson = objectMapper.writeValueAsString(invoice);
//            System.out.println(invoiceJson);
//        } catch (ClientNotFoundException | JsonProcessingException e) {
//            System.out.println("Erreur : " + e.getMessage());
//        }
//    }

}
