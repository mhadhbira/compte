package com.ike.compte.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ike.compte.entities.CompteBancaire;
import com.ike.compte.entities.TransactionType;
import com.ike.compte.service.CompteBancaireService;


@RestController
@RequestMapping("/comptes")
public class CompteBancaireResource {

	 @Autowired
	    private CompteBancaireService bancaireService;

	    @PostMapping(consumes = "application/json")
	    public ResponseEntity<CompteBancaire> createCompte(@RequestBody CompteBancaire compteBancaire) {
	    	System.out.println("the balance value: " + compteBancaire.getBalance());
	    	bancaireService.createCompte(compteBancaire);
	        return ResponseEntity.ok(compteBancaire);
	    }
	    // endpoint pour effectuer une trasaction debit/ crédit
	    @PostMapping("/{id}/transaction")
	    public ResponseEntity<CompteBancaire> transaction(@PathVariable Long id,
	    		                                          @RequestParam double amount,
	    		                                          @RequestParam TransactionType type){
	    	CompteBancaire updatedCompte = bancaireService.performTransaction(id, amount, type);
	    	return ResponseEntity.ok(updatedCompte);
	    }
	    
	    //endpoint pour recuperer le solde du compte
	    @GetMapping("/{id}/checksolde")
	    public ResponseEntity<Double> checkSolde(@PathVariable Long id){
	    	double solde = bancaireService.checkBalance(id);
	    	return ResponseEntity.ok(solde);
	    }
}
