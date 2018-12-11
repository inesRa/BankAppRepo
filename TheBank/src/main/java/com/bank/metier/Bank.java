package com.bank.metier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dao.CompteRepository;
import com.bank.dao.OperationRepository;
import com.bank.entities.Compte;
import com.bank.entities.CompteCourant;
import com.bank.entities.Operation;
import com.bank.entities.Retrait;
import com.bank.entities.Versement;

@Service
@Transactional
public class Bank implements IBank {
	
	@Autowired
	 private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte cp = compteRepository.getOne(codeCpte);
		if(cp==null) throw new RuntimeException("compte introuvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp = consulterCompte(codeCpte);
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde()+montant);
		compteRepository.save(cp);
		
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp = consulterCompte(codeCpte);
		double facilitiesCaisse=0;
		if(cp instanceof CompteCourant)
			facilitiesCaisse=((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+facilitiesCaisse<montant) 
			throw new RuntimeException("solde insuffisant");
		Retrait r = new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
		
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		return operationRepository.listOperation(codeCpte, new PageRequest(page, size));
	}

}
