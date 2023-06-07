package com.eteration.simplebanking.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DEPOSIT_TRANSACTION")
@Getter
@Setter
@NoArgsConstructor
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

}
