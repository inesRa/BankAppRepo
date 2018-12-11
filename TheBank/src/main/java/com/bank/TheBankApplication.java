package com.bank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bank.dao.ClientRepository;
import com.bank.dao.CompteRepository;
import com.bank.dao.OperationRepository;
import com.bank.entities.Client;
import com.bank.entities.Compte;
import com.bank.entities.CompteCourant;
import com.bank.entities.CompteEpargne;
import com.bank.entities.Versement;
import com.bank.metier.Bank;
import com.bank.metier.IBank;

@SpringBootApplication
public class TheBankApplication implements CommandLineRunner{

	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository CompteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private IBank bank;

	public static void main(String[] args) {
		SpringApplication.run(TheBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1= clientRepository.save(new Client("amine","amine@gmail.com"));
		Client c2= clientRepository.save(new Client("imane","imane@gmail.com"));
		
		Compte cp1 = CompteRepository.save(new CompteCourant("c1", new Date(),60000 , c1, 5000));
		Compte cp2 = CompteRepository.save(new CompteEpargne("c2", new Date(),5000 , c2, 5.5));

		bank.verser("c2", 55);
		
	}
}
