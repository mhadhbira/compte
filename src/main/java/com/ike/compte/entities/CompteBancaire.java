package com.ike.compte.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="COMPTE_BANCAIRE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteBancaire {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private double balance;

		public CompteBancaire(double balance) {
			this.balance=balance;
		}

}
