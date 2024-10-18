package com.ike.compte.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

	    private Long compteId;
	    private double amount;
	    private TransactionType type; 
}
