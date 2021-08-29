package com.example.demo.configuration;

import com.example.demo.dao.TransactionDao;
import com.example.demo.model.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(TransactionDao transactionDao) {

        return args -> {
            TransactionEntity t1 = new TransactionEntity.Builder().id(1L).date(LocalDate.now()).amount(242343).currency("Pounds").status("Active").description("Description1").build();
            TransactionEntity t2 = new TransactionEntity.Builder().id(2L).date(LocalDate.now().minusDays(1)).amount(1111).currency("Dollars").status("Pending").description("Description2").build();
            TransactionEntity t3 = new TransactionEntity.Builder().id(3L).date(LocalDate.now().minusMonths(1)).amount(45343).currency("Euros").status("Aborted").description("Description3").build();

            log.info("Preloading transaction  " + t1.getId() + " - " + t1.getStatus(), transactionDao.save(t1));
            log.info("Preloading transaction  " + t2.getId() + " - " + t2.getStatus(), transactionDao.save(t2));
            log.info("Preloading transaction  " + t3.getId() + " - " + t3.getStatus(), transactionDao.save(t3));
        };
    }
}
