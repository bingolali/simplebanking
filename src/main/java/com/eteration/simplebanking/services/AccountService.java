package com.eteration.simplebanking.services;


import com.eteration.simplebanking.exception.BusinessNotFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private static final String INSUFFICIENT_BALANCE = "Insufficient balance";

    private final AccountRepository accountRepository;
    private final DepositTransactionService depositTransactionService;
    private final WithdrawalTransactionService withdrawalTransactionService;

    public Account findAccount(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessNotFoundException(ACCOUNT_NOT_FOUND, 101));
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public String creditAccount(String accountNumber, Double amount) {
        String approvalCode = UUID.randomUUID().toString();
        Account account = findAccount(accountNumber);
        DepositTransaction depositTransaction = createDepositTransaction(amount, approvalCode, account);
        account.getDepositTransactions().add(depositTransaction);
        Double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);
        save(account);

        return approvalCode;
    }

    private DepositTransaction createDepositTransaction(Double amount, String approvalCode, Account account) {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setApprovalCode(approvalCode);
        depositTransaction.setAccount(account);
        depositTransactionService.save(depositTransaction);
        return depositTransaction;
    }

    public String debitAccount(String accountNumber, Double amount) {

        Account account = findAccount(accountNumber);

        String approvalCode = UUID.randomUUID().toString();

        if (amount > account.getBalance()) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE, 103);
        } else {
            createWithdrawalTransaction(amount, approvalCode, account);

            Double balance = account.getBalance();
            balance -= amount;
            account.setBalance(balance);
            save(account);
        }

        return approvalCode;
    }

    private void createWithdrawalTransaction(Double amount, String approvalCode, Account account) {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
        withdrawalTransaction.setApprovalCode(approvalCode);
        withdrawalTransaction.setAmount(amount);
        withdrawalTransaction.setDate(new Date());
        withdrawalTransaction.setAccount(account);
        withdrawalTransactionService.save(withdrawalTransaction);
    }
}
