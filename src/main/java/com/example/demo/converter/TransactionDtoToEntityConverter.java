package com.example.demo.converter;

import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoToEntityConverter {

    public TransactionEntity convert(TransactionDto source) {
        return new TransactionEntity.Builder().id(source.getId()).amount(source.getAmount())
                .currency(source.getCurrency())
                .status(source.getStatus())
                .description(source.getDescription())
                .date(source.getDate())
                .build();
    }
}
