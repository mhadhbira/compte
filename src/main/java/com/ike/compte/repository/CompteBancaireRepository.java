package com.ike.compte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ike.compte.entities.CompteBancaire;

@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long>{

}
