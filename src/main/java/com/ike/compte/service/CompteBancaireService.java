package com.ike.compte.service;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ike.compte.entities.CompteBancaire;
import com.ike.compte.entities.TransactionType;
import com.ike.compte.repository.CompteBancaireRepository;

@Service
public class CompteBancaireService {
	
    private static final Logger logger = LoggerFactory.getLogger(CompteBancaireService.class);

	@Autowired
	private CompteBancaireRepository bancaireRepository;
	
	public CompteBancaire createCompte(CompteBancaire compteBancaire) {
		if(compteBancaire.getBalance()==0) {
			compteBancaire.setBalance(0);
		}
		return bancaireRepository.save(compteBancaire);
		
	}
	
	public Optional<CompteBancaire> getCompteById(Long id) {
		return bancaireRepository.findById(id);
	}

	public CompteBancaire performTransaction(Long compteId, double amount, TransactionType type) {
        logger.info("Credit transaction: Compte ID={}, Amount={}", compteId, amount);
        Optional<CompteBancaire> compte = getCompteById(compteId);
        if(compte.isPresent()) {
        	CompteBancaire compteBancaire = compte.get();
        	
        	//créditer le compte
        	if(type.equals(type.CREDIT)) {
        		compteBancaire.setBalance(compteBancaire.getBalance() + amount);
        	}
        	
        	//débiter le compte
        	else if(type.equals(type.DEBIT)) {
        		if(compteBancaire.getBalance() >= amount) {
        			compteBancaire.setBalance(compteBancaire.getBalance() - amount);
        		}else {
        			  throw new RuntimeException("Fonds insuffisants pour le débit.");
        		}
        		} else {
        			  throw new RuntimeException("Type de transaction invalide.");
        		}
        	return bancaireRepository.save(compteBancaire);
        } else {
        	throw new RuntimeException("Compte non trouvé avec l'ID: " + compteId);
        	}
       
	}

	// récuperer le solde du compte
	public double checkBalance(Long compteId) {
		Optional<CompteBancaire> compte = getCompteById(compteId);
		if(compte.isPresent()) {
		 double balance = compte.get().getBalance();
		 return balance;
		} else {
			throw new RuntimeException("Compte non trouvé avec l'ID: " + compteId);
		}
	}
}
