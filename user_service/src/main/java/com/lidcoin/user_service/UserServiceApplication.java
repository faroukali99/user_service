package com.lidcoin.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Classe principale de l'application User Service.
 * Cette classe sert de point d'entrée pour démarrer le microservice de gestion des utilisateurs.
 * Elle active la configuration automatique de Spring Boot, la découverte des composants,
 * et permet l'utilisation de clients Feign pour la communication inter-services.
 */
@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
