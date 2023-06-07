package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.response.DepositTransactionResponse;
import com.eteration.simplebanking.response.GetAccountResponse;
import com.eteration.simplebanking.response.TransactionsResponse;
import com.eteration.simplebanking.response.WithdrawalTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bingolalii
 */
@Mapper(componentModel = "spring")
public interface GetAccountResponseMapper {

    GetAccountResponseMapper INSTANCE = Mappers.getMapper(GetAccountResponseMapper.class);

    @Mapping(target = "accountNumber", source = "account.accountNumber")
    @Mapping(target = "owner", source = "account.owner")
    @Mapping(target = "balance", source = "account.balance")
    @Mapping(target = "transactions", source = "account")
    GetAccountResponse mapToResponse(Account account);

    default List<TransactionsResponse> mapTransactions(Account account) {
        List<DepositTransaction> depositTransactions = account.getDepositTransactions();
        List<WithdrawalTransaction> withdrawalTransactions = account.getWithdrawalTransactions();
        List<DepositTransactionResponse> depositTransactionResponses = depositTransactions.stream()
                .map(TransactionMapper.INSTANCE::mapDepositTransaction)
                .collect(Collectors.toList());
        List<WithdrawalTransactionResponse> withdrawalTransactionResponses = withdrawalTransactions.stream()
                .map(TransactionMapper.INSTANCE::mapWithdrawalTransaction)
                .collect(Collectors.toList());

        TransactionsResponse transactionsResponse = new TransactionsResponse();
        transactionsResponse.setDepositTransactionResponses(depositTransactionResponses);
        transactionsResponse.setWithdrawalTransactionResponses(withdrawalTransactionResponses);

        return Collections.singletonList(transactionsResponse);

    }
}
