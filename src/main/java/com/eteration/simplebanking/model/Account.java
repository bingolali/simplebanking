package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@RequiredArgsConstructor
public class Account implements Serializable {

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.withdrawalTransactions = new ArrayList<>();
        this.depositTransactions = new ArrayList<>();
    }

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "OWNER")
    private String owner;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "BALANCE")
    private Double balance = 0.0;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<WithdrawalTransaction> withdrawalTransactions;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<DepositTransaction> depositTransactions;

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance", 103);
        }
        balance -= amount;
    }

    public void post(Transaction transaction) throws InsufficientBalanceException {
        if (transaction instanceof DepositTransaction) {
            deposit((DepositTransaction) transaction);
        } else if (transaction instanceof WithdrawalTransaction) {
            withdraw((WithdrawalTransaction) transaction);
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getClass());
        }
    }

    private void deposit(DepositTransaction transaction) {
        double amount = transaction.getAmount();
        balance += amount;
        if (depositTransactions != null) {
            depositTransactions.add(transaction);
        }
    }

    private void withdraw(WithdrawalTransaction transaction) throws InsufficientBalanceException {
        double amount = transaction.getAmount();
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance", 103);
        }
        balance -= amount;
        if (withdrawalTransactions != null) {
            withdrawalTransactions.add(transaction);
        }
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        if (withdrawalTransactions != null) {
            transactions.addAll(withdrawalTransactions);
        }
        if (depositTransactions != null) {
            transactions.addAll(depositTransactions);
        }
        return transactions;
    }

}
