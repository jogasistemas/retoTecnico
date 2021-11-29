package com.exchange.reactive.service.exchange;

import com.exchange.reactive.repository.ExchangeRepository;
import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.request.UpdateExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.entity.Exchange;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

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
        if(exchange.getId()==null)
        {
            exchange.setId(UUID.randomUUID().toString());
        }
        double amount= addExchangeRequest.getAmount();
        String  currency = addExchangeRequest.getCurrencyOrigin().toUpperCase();
        if(currency.equals("S")){
            exchange.setAmountExchange(amount / addExchangeRequest.getTypeExchange());
            exchange.setDestinationCurrency("D");
        }else if(currency.equals("D")){
            exchange.setAmountExchange(amount * addExchangeRequest.getTypeExchange());
            exchange.setDestinationCurrency("S");
        }

        return exchange;
    }
    private Exchange toExchange(Exchange exchange) {
        double amount= exchange.getAmount();
        String  currency = exchange.getCurrencyOrigin().toUpperCase();
        if(currency.equals("S")){
            exchange.setAmountExchange(amount / exchange.getTypeExchange());
            exchange.setDestinationCurrency("D");
        }else if(currency.equals("D")){
            exchange.setAmountExchange(amount * exchange.getTypeExchange());
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

    @Override
    public Completable updateExchange(UpdateExchangeRequest updateBookRequest) {
        return updateExchangeToRepository(updateBookRequest);
    }

    private Completable updateExchangeToRepository(UpdateExchangeRequest updateExchangeRequest) {
        return Completable.create(completableSubscriber -> {
            Optional<Exchange> optionalExchange = exchangeRepository.findById(updateExchangeRequest.getId());
            if (!optionalExchange.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                Exchange exchange = optionalExchange.get();
                exchange.setAmount(updateExchangeRequest.getAmount());
                exchange.setCurrencyOrigin(updateExchangeRequest.getCurrencyOrigin());
                exchange.setTypeExchange(updateExchangeRequest.getTypeExchange());
                exchangeRepository.save(toExchange(exchange));
                completableSubscriber.onComplete();
            }
        });
    }


    @Override
    public Single<ExchangeResponse> getExchangeDetail(String id) {
        return findExchangeDetailInRepository(id);
    }

    private Single<ExchangeResponse> findExchangeDetailInRepository(String id) {
        return Single.create(singleSubscriber -> {
            Optional<Exchange> optionalExchange = exchangeRepository.findById(id);
            if (!optionalExchange.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                ExchangeResponse exchangeResponse = toExchangeResponse(optionalExchange.get());
                singleSubscriber.onSuccess(exchangeResponse);
            }
        });
    }
}
