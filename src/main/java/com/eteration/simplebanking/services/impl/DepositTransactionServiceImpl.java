package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.repository.DepositTransactionRepository;
import com.eteration.simplebanking.services.DepositTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author bingolalii
 */
@Service
@RequiredArgsConstructor
public class DepositTransactionServiceImpl implements DepositTransactionService {

    private final DepositTransactionRepository depositTransactionRepository;

    @Override
    public DepositTransaction save(DepositTransaction transaction) {
        return depositTransactionRepository.save(transaction);
    }

    @Override
    public DepositTransaction saveAndFlush(DepositTransaction transaction) {
        return depositTransactionRepository.saveAndFlush(transaction);
    }
}
