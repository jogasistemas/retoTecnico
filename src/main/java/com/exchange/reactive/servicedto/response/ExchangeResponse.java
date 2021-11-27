package com.exchange.reactive.servicedto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {

    private String id;
    private double amount;
    private String currencyOrigin;
    private String destinationCurrency;
    private double amountExchange;
    private double typeExchange;

}
