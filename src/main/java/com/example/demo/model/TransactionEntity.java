package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private String status;
    private Integer amount;
    private String currency;
    private String description;

    public TransactionEntity() {
    }

    private TransactionEntity(Builder builder) {
        id = builder.id;
        date = builder.date;
        status = builder.status;
        amount = builder.amount;
        currency = builder.currency;
        description = builder.description;
    }

    public Long getId() {return id;}

    public LocalDate getDate() {return date;}

    public String getStatus() {return status;}

    public Integer getAmount() {return amount;}

    public String getCurrency() {return currency;}

    public String getDescription() {return description;}


    public static final class Builder {
        private Long id;
        private LocalDate date;
        private String status;
        private Integer amount;
        private String currency;
        private String description;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder date(LocalDate val) {
            date = val;
            return this;
        }

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder amount(Integer val) {
            amount = val;
            return this;
        }

        public Builder currency(String val) {
            currency = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public TransactionEntity build() {
            return new TransactionEntity(this);
        }
    }
}
