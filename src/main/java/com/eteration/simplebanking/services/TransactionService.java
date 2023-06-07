package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Transaction;

/**
 * @author bingolalii
 */
public interface TransactionService<T extends Transaction> {

    T save(T transaction);

    T saveAndFlush(T transaction);
}
