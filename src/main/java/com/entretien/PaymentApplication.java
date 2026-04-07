package com.entretien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}


// Utiliser logger (choisissez le framework mais il faut justifier le choix, utilisez springboot
// Créer la base de données et rajouter les transactions dans le code via spring
//
// Créer une api rest  pour faire une transaction avec un ack ok ou ko
// Qui est appelé depuis une interface front