package com.exchange.reactive.servicedto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateExchangeRequest {
    private String id;
    private double amount;
    private String currencyOrigin;
    private double typeExchange;
}
