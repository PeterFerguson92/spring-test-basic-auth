package com.example.demo.service;


import com.example.demo.model.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<TransactionEntity> getAll();
    TransactionEntity createTransaction(TransactionEntity transactionEntity);
    TransactionEntity updateTransaction(TransactionEntity transactionEntity);
    Optional<TransactionEntity> getTransactionById(Long id);
    void deleteTransaction(Long transactionId);
}
