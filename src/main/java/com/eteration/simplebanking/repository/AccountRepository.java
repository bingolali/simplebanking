package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author bingolalii
 */
public interface AccountRepository extends JpaRepository <Account, Long>{

    Optional<Account> findAccountByAccountNumber(String accountNumber);

}
