package com.exchange.reactive.webdto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExchangeWebResponse {
    private String id;
    private double amount;
    private String currencyOrigin;
    private String destinationCurrency;
    private double amountExchange;
    private double typeExchange;
}
