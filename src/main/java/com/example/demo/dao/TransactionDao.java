package com.example.demo.dao;

import com.example.demo.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<TransactionEntity, Long> {

}