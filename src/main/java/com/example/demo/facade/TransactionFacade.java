package com.example.demo.facade;


import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionFacade {
    List<TransactionDto> getAll(String Status);
    TransactionDto createTransaction(TransactionDto transactionDto);
    Optional<TransactionDto> updateTransaction(TransactionDto transactionDto, Long id);
    Optional<TransactionDto> getTransactionById(Long id);
    void deleteTransaction(Long transactionId);
}
