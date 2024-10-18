package com.ike.compte.service;
import com.ike.compte.entities.CompteBancaire;
import com.ike.compte.entities.TransactionType;
import com.ike.compte.repository.CompteBancaireRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

class CompteBancaireServiceTest {

    @InjectMocks
    private CompteBancaireService compteBancaireService;

    @Mock
    private CompteBancaireRepository compteBancaireRepository;
    
    // to reset objects before each test and avoid interference between tests and each test start with fresh setup
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateAccount() {
    	 // Arrange
        Long compteId = 1L;
        CompteBancaire compte = new CompteBancaire();
        compte.setId(compteId);
        compte.setBalance(500); 
    	 
    	when(compteBancaireRepository.findById(compteId)).thenReturn(Optional.of(compte));
        when(compteBancaireRepository.save(compte)).thenReturn(compte);
            
    	CompteBancaire createdCompte = compteBancaireService.createCompte(compte);

         assertEquals(500, createdCompte.getBalance());
    }

    @Test
    public void testCredit() {
        // Arrange
        Long compteId = 1L;
        CompteBancaire compte = new CompteBancaire();
        compte.setId(compteId);
        compte.setBalance(1000.0); 

        when(compteBancaireRepository.findById(compteId)).thenReturn(Optional.of(compte));
        when(compteBancaireRepository.save(compte)).thenReturn(compte);

        // Act
        CompteBancaire updatedCompte = compteBancaireService.performTransaction(compteId, 500.0, TransactionType.CREDIT);

        // Assert
        assertNotNull(updatedCompte);
        assertEquals(1500.0, updatedCompte.getBalance());
        verify(compteBancaireRepository, times(1)).save(updatedCompte);
    }
    
    @Test
    public void testSuccessfulDebit() {
    	// Arrange
    	Long compteId = 1L;
    	CompteBancaire compteBancaire = new CompteBancaire();
    	compteBancaire.setId(compteId);
    	compteBancaire.setBalance(1000.0); // Account has sufficient funds
    	
    	when(compteBancaireRepository.findById(compteId)).thenReturn(Optional.of(compteBancaire));
    	when(compteBancaireRepository.save(compteBancaire)).thenReturn(compteBancaire);
    	
    	//Act 
    	CompteBancaire updatedCompte = compteBancaireService.performTransaction(compteId, 200.0, TransactionType.DEBIT);
    	
    	//Assert
    	assertNotNull(updatedCompte);
    	assertEquals(800.0, updatedCompte.getBalance());
    	verify(compteBancaireRepository, times(1)).save(updatedCompte);
    }

    @Test
    public void testInsufficientFundsDebit() {
        // Arrange
        Long compteId = 1L;
        CompteBancaire compte = new CompteBancaire();
        compte.setId(compteId);
        compte.setBalance(200.0); // Account has insufficient funds

        when(compteBancaireRepository.findById(compteId)).thenReturn(Optional.of(compte));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compteBancaireService.performTransaction(compteId, 300.0, TransactionType.DEBIT);
        });

        assertEquals("Fonds insuffisants pour le débit.", exception.getMessage());
        verify(compteBancaireRepository, times(0)).save(compte); // Ensure save() is not called
    }

    @Test
    public void testDebitAccountNotFound() {
        // Arrange
        Long compteId = 1L;

        when(compteBancaireRepository.findById(compteId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compteBancaireService.performTransaction(compteId, 500.0, TransactionType.DEBIT);
        });

        assertEquals("Compte non trouvé avec l'ID: " + compteId, exception.getMessage());
        verify(compteBancaireRepository, times(0)).save(any(CompteBancaire.class)); // Ensure save() is not called
    }
}
 
