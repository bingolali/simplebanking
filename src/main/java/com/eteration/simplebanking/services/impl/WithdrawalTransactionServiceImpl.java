package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.WithdrawalTransactionRepository;
import com.eteration.simplebanking.services.WithdrawalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author bingolalii
 */
@Service
@RequiredArgsConstructor
public class WithdrawalTransactionServiceImpl implements WithdrawalTransactionService {

    private final WithdrawalTransactionRepository withdrawalTransactionRepository;

    @Override
    public WithdrawalTransaction save(WithdrawalTransaction transaction) {
        return withdrawalTransactionRepository.save(transaction);
    }

    @Override
    public WithdrawalTransaction saveAndFlush(WithdrawalTransaction transaction) {
        return withdrawalTransactionRepository.saveAndFlush(transaction);
    }
}
