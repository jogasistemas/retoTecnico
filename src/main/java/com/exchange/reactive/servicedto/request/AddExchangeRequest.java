package com.exchange.reactive.servicedto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddExchangeRequest {
    private double amount;
    private String currencyOrigin;
    private double typeExchange;
}
