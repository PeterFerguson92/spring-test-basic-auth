package com.example.demo.web;

import com.example.demo.dto.TransactionDto;
import com.example.demo.facade.TransactionFacade;
import com.example.demo.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionFacade transactionFacade;

    @GetMapping("api/v1/transactions")
    public List<TransactionDto> getAllTransactions(@RequestParam(required = false, defaultValue = "All") String status) {
        return transactionFacade.getAll(status);
    }

    @PostMapping("api/v1/transactions")
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionFacade.createTransaction(transactionDto);
    }

    @GetMapping("api/v1/transactions/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return transactionFacade.getTransactionById(id).orElseThrow(() -> new ResourceNotFoundException("No transaction with id: "+ id + " was found"));
    }

    @PutMapping("api/v1/transactions/{id}")
    public TransactionDto updateTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long id) {
        return transactionFacade.updateTransaction(transactionDto, id).orElseThrow(() -> new ResourceNotFoundException("No transaction with id: "+ transactionDto.getId() + " was found"));
    }

    @DeleteMapping("api/v1/transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        try {
            transactionFacade.deleteTransaction(id);
        } catch (ResourceNotFoundException e){
            throw e;
        }
    }

}
