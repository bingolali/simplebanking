package com.eteration.simplebanking.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author bingolalii
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDebitRequest {

    @NotNull(message = "Amount can not be null")
    private Double amount;

}
