package com.example.demo.service;

import com.example.demo.dao.TransactionDao;
import com.example.demo.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<TransactionEntity> getAll() {
        return transactionDao.findAll();
    }

    @Override
    public TransactionEntity createTransaction(TransactionEntity transactionEntity) {
            return transactionDao.save(transactionEntity);
    }

    @Override
    public TransactionEntity updateTransaction(TransactionEntity transactionEntity) {
        return transactionDao.save(transactionEntity);
    }

    @Override
    public Optional<TransactionEntity> getTransactionById(Long id) {
        return transactionDao.findById(id);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<TransactionEntity> optionalTransactionEntity = transactionDao.findById(transactionId);
        transactionDao.delete(optionalTransactionEntity.get());
    }

}
