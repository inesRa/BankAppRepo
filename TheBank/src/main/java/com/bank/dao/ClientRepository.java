package com.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entities.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
