package com.example.demo.web;

import com.example.demo.dto.TransactionDto;
import com.example.demo.facade.TransactionFacade;
import com.example.demo.web.exceptions.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    private final Long TRANSACTION_ID_1 = 11L;
    private final Long TRANSACTION_ID_2 = 12L;
    private final String STATUS = "Test1";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private TransactionFacade transactionFacade;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void getAll_shouldReturnAllTransactions() {
        given(transactionFacade.getAll(STATUS)).willReturn(Arrays.asList(
                new TransactionDto.Builder().id(TRANSACTION_ID_1).build(),
                new TransactionDto.Builder().id(TRANSACTION_ID_2).build()));

        List<TransactionDto> transactionDtos = transactionController.getAllTransactions(STATUS);

        verify(transactionFacade).getAll(STATUS);
        assertThat(transactionDtos.size()).isEqualTo(2);
        assertThat(transactionDtos.get(0).getId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(transactionDtos.get(1).getId()).isEqualTo(TRANSACTION_ID_2);
    }

    @Test
    public void createTransaction_shouldReturnCreatedTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID_1).build();
        given(transactionFacade.createTransaction(transactionDto)).willReturn(transactionDto);

        TransactionDto created = transactionController.createTransaction(transactionDto);

        verify(transactionFacade).createTransaction(transactionDto);
        assertThat(created).isNotNull();
        assertThat(created.getId()).isEqualTo(TRANSACTION_ID_1);
    }

    @Test
    public void getTransactionById_shouldReturnTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID_1).build();
        given(transactionFacade.getTransactionById(TRANSACTION_ID_1)).willReturn(Optional.of(transactionDto));

        TransactionDto retrieved = transactionController.getTransactionById(TRANSACTION_ID_1);

        verify(transactionFacade).getTransactionById(TRANSACTION_ID_1);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getId()).isEqualTo(TRANSACTION_ID_1);
    }

    @Test
    public void getTransactionById_shouldThrowExceptionWhenTransactionToUpdateIsNotFound() {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("No transaction with id: 11 was found");

        transactionController.getTransactionById(TRANSACTION_ID_1);
    }

    @Test
    public void updateTransaction_shouldReturnUpdatedTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID_1).build();
        given(transactionFacade.updateTransaction(transactionDto, TRANSACTION_ID_1)).willReturn(Optional.of(transactionDto));

        TransactionDto updated = transactionController.updateTransaction(transactionDto, TRANSACTION_ID_1);

        verify(transactionFacade).updateTransaction(transactionDto, TRANSACTION_ID_1);
        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(TRANSACTION_ID_1);
    }

    @Test
    public void updateTransaction_shouldThrowExceptionWhenTransactionToUpdateIsNotFound() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID_1).build();
        given(transactionFacade.updateTransaction(transactionDto, TRANSACTION_ID_1)).willReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("No transaction with id: 11 was found");

        transactionController.updateTransaction(transactionDto, TRANSACTION_ID_1);
    }

    @Test
    public void deleteTransaction_shouldCallDeleteTransaction() {
        transactionController.deleteTransaction(TRANSACTION_ID_1);

        verify(transactionFacade).deleteTransaction(TRANSACTION_ID_1);
    }
}
