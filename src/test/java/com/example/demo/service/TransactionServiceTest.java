package com.example.demo.service;

import com.example.demo.dao.TransactionDao;
import com.example.demo.model.TransactionEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    private final Long TRANSACTION_ID = 24242L;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private TransactionDao transactionDao;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void getAll_shouldReturnAllTransactions() {
        given(transactionDao.findAll()).willReturn(Collections.singletonList(new TransactionEntity.Builder().id(TRANSACTION_ID).build()));

        List<TransactionEntity> result = transactionService.getAll();

        verify(transactionDao).findAll();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(TRANSACTION_ID);
    }

    @Test
    public void createTransaction_shouldReturnCreatedTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionDao.save(transactionEntity)).willReturn(transactionEntity);

        TransactionEntity result = transactionService.createTransaction(transactionEntity);

        verify(transactionDao).save(transactionEntity);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TRANSACTION_ID);
    }

    @Test
    public void updateTransaction_shouldReturnUpdatedTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionDao.save(transactionEntity)).willReturn(transactionEntity);

        TransactionEntity result = transactionService.updateTransaction(transactionEntity);

        verify(transactionDao).save(transactionEntity);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TRANSACTION_ID);
    }


    @Test
    public void getTransactionById_shouldReturnTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionDao.findById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));

        Optional<TransactionEntity> result = transactionService.getTransactionById(TRANSACTION_ID);

        verify(transactionDao).findById(TRANSACTION_ID);
        assertThat(result).isNotNull();
        assertThat(result.get().getId()).isEqualTo(TRANSACTION_ID);
    }

    @Test
    public void deleteTransaction_shouldDeleteTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionDao.findById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));

       transactionService.deleteTransaction(TRANSACTION_ID);

        verify(transactionDao).findById(TRANSACTION_ID);
        verify(transactionDao).delete(transactionEntity);
    }
}
