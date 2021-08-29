package com.example.demo.converter;

import com.example.demo.dto.TransactionDto;
import com.example.demo.model.TransactionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDtoToEntityConverterTest {
    private final Long TRANSACTION_ID = 24242L;
    private final Integer AMOUNT = 12342;
    private final LocalDate TRANSACTION_DATE = LocalDate.now();
    private final String CURRENCY = "Dollars";
    private final String STATUS = "Active";
    private final String DESCRIPTION = "Description";
    
    @InjectMocks
    private TransactionDtoToEntityConverter converter;

    @Test
    public void convert_shouldConvertEntityToDto() {
        TransactionDto t1 = new TransactionDto.Builder()
                .id(TRANSACTION_ID)
                .currency(CURRENCY)
                .amount(AMOUNT)
                .status(STATUS)
                .description(DESCRIPTION)
                .date(TRANSACTION_DATE)
                .build();

        TransactionEntity result = converter.convert(t1);

        assertThat(result.getId()).isEqualTo(TRANSACTION_ID);
        assertThat(result.getCurrency()).isEqualTo(CURRENCY);
        assertThat(result.getAmount()).isEqualTo(AMOUNT);
        assertThat(result.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(result.getStatus()).isEqualTo(STATUS);
        assertThat(result.getDate()).isEqualTo(TRANSACTION_DATE);
    }
}
