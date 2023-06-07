package com.eteration.simplebanking.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bingolalii
 */
@Getter
@Setter
public class  GetAccountResponse {

    private String accountNumber;

    private String owner;

    private Double balance;

    private String date;

    private List<TransactionsResponse> transactions;

}
