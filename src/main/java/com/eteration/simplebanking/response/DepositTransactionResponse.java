package com.eteration.simplebanking.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bingolalii
 */
@Getter
@Setter
public class DepositTransactionResponse {

    private String date;

    private Double amount;

    private String type;

    private String approvalCode;

}
