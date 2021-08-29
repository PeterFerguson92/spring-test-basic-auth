package com.example.demo.facade;

import com.example.demo.converter.TransactionDtoToEntityConverter;
import com.example.demo.converter.TransactionEntityToDtoConverter;
import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;
import com.example.demo.service.TransactionService;
import com.example.demo.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionFacadeImpl implements TransactionFacade {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionEntityToDtoConverter transactionEntityToDtoConverter;
    @Autowired
    private TransactionDtoToEntityConverter transactionDtoToEntityConverter;

    @Override
    public List<TransactionDto> getAll(String status) {
       List<TransactionEntity> transactionEntities = transactionService.getAll();
       if(status.equals("All")){
           return transactionEntities.stream()
                   .map(transactionEntity -> transactionEntityToDtoConverter.convert(transactionEntity))
                   .collect(Collectors.toList());
       } else {
           return transactionEntities.stream()
                   .map(transactionEntity -> transactionEntityToDtoConverter.convert(transactionEntity))
                   .filter(t -> status.equals(t.getStatus()))
                   .collect(Collectors.toList());
       }
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = transactionDtoToEntityConverter.convert(transactionDto);
        return transactionEntityToDtoConverter.convert(transactionService.createTransaction(transactionEntity));
    }

    @Override
    public Optional<TransactionDto> updateTransaction(TransactionDto transactionDto, Long id) {
        Optional<TransactionDto> transaction = getTransactionById(id);
        if(transaction.isPresent()){
            TransactionEntity transactionEntity = transactionDtoToEntityConverter.convert(transactionDto);
            TransactionEntity transactionEntityUpdated = transactionService.updateTransaction(transactionEntity);
            return Optional.of(transactionEntityToDtoConverter.convert(transactionEntityUpdated));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TransactionDto> getTransactionById(Long id) {
        Optional<TransactionEntity> optionalTransactionEntity = transactionService.getTransactionById(id) ;
        return optionalTransactionEntity.map(transactionEntity -> transactionEntityToDtoConverter.convert(transactionEntity));
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<TransactionDto> transaction = getTransactionById(transactionId);
        if(transaction.isPresent()){
            transactionService.deleteTransaction(transactionId);
        } else {
            throw new ResourceNotFoundException("error");
        }
    }
}
