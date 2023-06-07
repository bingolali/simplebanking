package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class Transaction implements Serializable {

    public Transaction(double amount) {
        this.amount = amount;
        this.date = new Date();
    }

    @Column(name = "DATE")
    private Date date;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "APPROVAL_CODE")
    private String approvalCode;

}


