package com.example.demo.converter;

import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityToDtoConverter {

    public TransactionDto convert(TransactionEntity source) {
        return new TransactionDto.Builder()
                .id(source.getId())
                .amount(source.getAmount())
                .currency(source.getCurrency())
                .status(source.getStatus())
                .description(source.getDescription())
                .date(source.getDate())
                .build();
    }
}
