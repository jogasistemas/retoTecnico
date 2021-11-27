package com.exchange.reactive.service.exchange;

import com.exchange.reactive.repository.ExchangeRepository;
import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.entity.Exchange;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    private final double typeExchange = 4.0;

    @Override
    public Single<ExchangeResponse> addExchange(AddExchangeRequest addExchangeRequest) {
        return Single.create(singleSubscriber -> {
               Exchange exchange = exchangeRepository.save(toExchange(addExchangeRequest));
               ExchangeResponse exchangeResponse = new ExchangeResponse();
            BeanUtils.copyProperties(exchange,exchangeResponse);
                singleSubscriber.onSuccess(exchangeResponse);
        });
    }


    private Exchange toExchange(AddExchangeRequest addExchangeRequest) {
        Exchange exchange = new Exchange();
        BeanUtils.copyProperties(addExchangeRequest, exchange);
        exchange.setId(UUID.randomUUID().toString());
        double amount= addExchangeRequest.getAmount();
        String  currency = addExchangeRequest.getCurrencyOrigin().toUpperCase();
        if(currency.equals("S")){
            exchange.setAmountExchange(amount / typeExchange);
            exchange.setDestinationCurrency("D");
        }else if(currency.equals("D")){
            exchange.setAmountExchange(amount * typeExchange);
            exchange.setDestinationCurrency("S");
        }

        return exchange;
    }

    @Override
    public Single<List<ExchangeResponse>> getAllExchanges(int limit, int page) {
        return findAllExchangeInRepository(limit, page)
                .map(this::toExchangeResponseList);
    }

    private Single<List<Exchange>> findAllExchangeInRepository(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<Exchange> books = exchangeRepository.findAll(PageRequest.of(page, limit)).getContent();
            singleSubscriber.onSuccess(books);
        });
    }

    private List<ExchangeResponse> toExchangeResponseList(List<Exchange> exchangesList) {
        return exchangesList
                .stream()
                .map(this::toExchangeResponse)
                .collect(Collectors.toList());
    }

    private ExchangeResponse toExchangeResponse(Exchange exchange) {
        ExchangeResponse exchangeResponse = new ExchangeResponse();
        BeanUtils.copyProperties(exchange, exchangeResponse);
        return exchangeResponse;
    }

}
