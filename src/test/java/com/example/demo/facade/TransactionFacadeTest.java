package com.example.demo.facade;

import com.example.demo.converter.TransactionDtoToEntityConverter;
import com.example.demo.converter.TransactionEntityToDtoConverter;
import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;
import com.example.demo.service.TransactionService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFacadeTest {
    private final String ALL_STATUS = "All";
    private final String ACTIVE_STATUS = "Active";
    private final String ABORTED_STATUS = "Aborted";
    private final Long TRANSACTION_ID = 24242L;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private TransactionEntityToDtoConverter transactionEntityToDtoConverter;
    @Mock
    private TransactionDtoToEntityConverter transactionDtoToEntityConverter;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionFacadeImpl transactionFacade;

    @Test
    public void getAll_shouldReturnAllTransactions() {
        TransactionEntity t1 = new TransactionEntity.Builder().status(ACTIVE_STATUS).build();
        TransactionEntity t2 = new TransactionEntity.Builder().status(ABORTED_STATUS).build();

        given(transactionService.getAll()).willReturn(Arrays.asList(t1, t2));

        given(transactionEntityToDtoConverter.convert(t1)).willReturn(new TransactionDto.Builder().status(ACTIVE_STATUS).build());
        given(transactionEntityToDtoConverter.convert(t2)).willReturn(new TransactionDto.Builder().status(ABORTED_STATUS).build());

        List<TransactionDto> transactionDtos = transactionFacade.getAll(ALL_STATUS);

        verify(transactionService).getAll();
        assertThat(transactionDtos.size()).isEqualTo(2);
        assertThat(transactionDtos.get(0).getStatus()).isEqualTo(ACTIVE_STATUS);
        assertThat(transactionDtos.get(1).getStatus()).isEqualTo(ABORTED_STATUS);
    }

    @Test
    public void getAll_shouldReturnAllActiveTransactions() {
        TransactionEntity t1 = new TransactionEntity.Builder().status(ACTIVE_STATUS).build();
        TransactionEntity t2 = new TransactionEntity.Builder().status(ABORTED_STATUS).build();

        given(transactionService.getAll()).willReturn(Arrays.asList(t1, t2));

        given(transactionEntityToDtoConverter.convert(t1)).willReturn(new TransactionDto.Builder().status(ACTIVE_STATUS).build());
        given(transactionEntityToDtoConverter.convert(t2)).willReturn(new TransactionDto.Builder().status(ABORTED_STATUS).build());

        List<TransactionDto> transactionDtos = transactionFacade.getAll(ACTIVE_STATUS);

        verify(transactionService).getAll();
        assertThat(transactionDtos.size()).isEqualTo(1);
        assertThat(transactionDtos.get(0).getStatus()).isEqualTo(ACTIVE_STATUS);
    }

    @Test
    public void createTransaction_shouldReturnCreatedTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().status(ACTIVE_STATUS).build();
        TransactionEntity transactionEntity = new TransactionEntity.Builder().status(ACTIVE_STATUS).build();

        given(transactionDtoToEntityConverter.convert(transactionDto)).willReturn(transactionEntity);
        given(transactionService.createTransaction(transactionEntity)).willReturn(transactionEntity);
        given(transactionEntityToDtoConverter.convert(transactionEntity)).willReturn(transactionDto);

        TransactionDto created = transactionFacade.createTransaction(transactionDto);

        verify(transactionService).createTransaction(transactionEntity);
        assertThat(created).isNotNull();
        assertThat(created.getStatus()).isEqualTo(ACTIVE_STATUS);
    }

    @Test
    public void updateTransaction_shouldReturnUpdatedTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID).build();
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));
        given(transactionService.updateTransaction(transactionEntity)).willReturn(transactionEntity);
        given(transactionDtoToEntityConverter.convert(transactionDto)).willReturn(transactionEntity);
        given(transactionEntityToDtoConverter.convert(transactionEntity)).willReturn(transactionDto);

        Optional<TransactionDto> updated = transactionFacade.updateTransaction(transactionDto, TRANSACTION_ID);

        verify(transactionService).updateTransaction(transactionEntity);
        assertThat(updated).isNotNull();
        assertThat(updated.get().getId()).isEqualTo(TRANSACTION_ID);
    }

    @Test
    public void updateTransaction_shouldReturnEmptyOptionalWhenTransactionToUpdateIsIsNotFound() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID).build();
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.empty());

        Optional<TransactionDto> updated = transactionFacade.updateTransaction(transactionDto, TRANSACTION_ID);

        verify(transactionService, times(0)).updateTransaction(transactionEntity);
        assertThat(updated.isPresent()).isEqualTo(false);
    }

    @Test
    public void getTransactionById_shouldReturnTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID).build();
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));
        given(transactionEntityToDtoConverter.convert(transactionEntity)).willReturn(transactionDto);

        Optional<TransactionDto> retrieved = transactionFacade.getTransactionById(TRANSACTION_ID);

        verify(transactionService).getTransactionById(TRANSACTION_ID);
        assertThat(retrieved.isPresent());
    }

    @Test
    public void getTransactionById_shouldReturnEmptyOptionalWhenTransactionIsNotFound() {
        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.empty());

        Optional<TransactionDto> retrieved = transactionFacade.getTransactionById(TRANSACTION_ID);

        verify(transactionService).getTransactionById(TRANSACTION_ID);
        assertThat(retrieved.isPresent()).isEqualTo(false);
    }

    @Test
    public void deleteTransaction_shouldDeleteTransaction() {
        TransactionDto transactionDto = new TransactionDto.Builder().id(TRANSACTION_ID).build();
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));
        given(transactionEntityToDtoConverter.convert(transactionEntity)).willReturn(transactionDto);

        transactionFacade.deleteTransaction(TRANSACTION_ID);

        verify(transactionService).deleteTransaction(TRANSACTION_ID);
    }

    @Test
    public void deleteTransactiond_shouldThrowExceptionWhenTransactionIsNotFound() {
        TransactionEntity transactionEntity = new TransactionEntity.Builder().id(TRANSACTION_ID).build();

        given(transactionService.getTransactionById(TRANSACTION_ID)).willReturn(Optional.of(transactionEntity));
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("error");

        transactionFacade.deleteTransaction(TRANSACTION_ID);
    }
}
