package com.eteration.simplebanking.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bingolalii
 */
@Getter
@Setter
public class TransactionsResponse {

    List<WithdrawalTransactionResponse> withdrawalTransactionResponses;

    List<DepositTransactionResponse> depositTransactionResponses;

}
