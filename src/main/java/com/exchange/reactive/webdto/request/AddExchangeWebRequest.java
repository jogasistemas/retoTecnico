package com.exchange.reactive.webdto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddExchangeWebRequest {
    private double amount;
    private String currencyOrigin;
}
