package com.exchange.reactive.service.exchange;

import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import io.reactivex.Single;

import java.util.List;

public interface ExchangeService {
    Single<ExchangeResponse> addExchange(AddExchangeRequest addExchangeRequest);

    Single<List<ExchangeResponse>> getAllExchanges(int limit, int page);

}
