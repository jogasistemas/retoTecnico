package com.exchange.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "exchanges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "amount")
    private double amount;

    @Column(name ="currencyOrigin")
    private String currencyOrigin;

    @Column(name ="destinationCurrency")
    private String destinationCurrency;

    @Column(name = "amountExchange")
    private double amountExchange;

    @Column(name = "typeExchange")
    private double typeExchange;

}
