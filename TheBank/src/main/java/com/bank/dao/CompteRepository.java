package com.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte,String> {

}
