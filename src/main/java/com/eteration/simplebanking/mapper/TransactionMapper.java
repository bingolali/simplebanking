package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.response.DepositTransactionResponse;
import com.eteration.simplebanking.response.WithdrawalTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 * @author bingolalii
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "amount", source = "transaction.amount")
    @Mapping(target = "date", expression = "java(transaction.getDate().toString())")
    @Mapping(target = "type", constant = "DepositTransaction")
    @Mapping(target = "approvalCode", source = "transaction.approvalCode")
    DepositTransactionResponse mapDepositTransaction(DepositTransaction transaction);

    @Mapping(target = "amount", source = "transaction.amount")
    @Mapping(target = "date", expression = "java(transaction.getDate().toString())")
    @Mapping(target = "type", constant = "WithdrawalTransaction")
    @Mapping(target = "approvalCode", source = "transaction.approvalCode")
    WithdrawalTransactionResponse mapWithdrawalTransaction(WithdrawalTransaction transaction);
}
