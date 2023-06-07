package com.eteration.simplebanking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author bingolalii
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreditResponse {

    private String status;

    private String approvalCode;

}
