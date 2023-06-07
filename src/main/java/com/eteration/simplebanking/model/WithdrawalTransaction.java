package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WITHDRAWAL_TRANSACTION")
@Getter
@Setter
@NoArgsConstructor
public class WithdrawalTransaction extends Transaction{

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

}


