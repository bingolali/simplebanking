package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.mapper.GetAccountResponseMapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.request.AccountCreditRequest;
import com.eteration.simplebanking.request.AccountDebitRequest;
import com.eteration.simplebanking.response.*;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<AccountCreditResponse> credit(@PathVariable String accountNumber, @Valid @RequestBody AccountCreditRequest accountCreditRequest) {
        final String approvalCode = accountService.creditAccount(accountNumber, accountCreditRequest.getAmount());
        final AccountCreditResponse response = new AccountCreditResponse("OK", approvalCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<AccountDebitResponse> debit(@PathVariable String accountNumber, @Valid @RequestBody AccountDebitRequest accountDebitRequest) {
        final String approvalCode = accountService.debitAccount(accountNumber, accountDebitRequest.getAmount());
        final AccountDebitResponse response = new AccountDebitResponse("OK", approvalCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<GetAccountResponse> getAccount(@PathVariable String accountNumber) {
        final Account account = accountService.findAccount(accountNumber);
        GetAccountResponse response = GetAccountResponseMapper.INSTANCE.mapToResponse(account);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}


