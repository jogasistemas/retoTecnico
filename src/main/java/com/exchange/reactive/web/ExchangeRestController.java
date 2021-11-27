package com.exchange.reactive.web;

import com.exchange.reactive.service.exchange.ExchangeService;
import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.webdto.response.BaseWebResponse;
import com.exchange.reactive.webdto.response.ExchangeWebResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/exchanges")
public class ExchangeRestController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public Single<ResponseEntity<ExchangeResponse>> addExchange(
        @RequestBody AddExchangeRequest addExchangeRequest) {
        return exchangeService.addExchange(addExchangeRequest).subscribeOn(Schedulers.io()).map(
            s -> ResponseEntity.created(URI.create("/api/exchanges/" + s))
                .body(s));
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<List<ExchangeWebResponse>>>> getAllExchanges(@RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                                              @RequestParam(value = "page", defaultValue = "0") int page) {
        return exchangeService.getAllExchanges(limit, page)
                .subscribeOn(Schedulers.io())
                .map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toBookWebResponseList(bookResponses))));
    }

    private List<ExchangeWebResponse> toBookWebResponseList(List<ExchangeResponse> exchangeResponseList) {
        return exchangeResponseList
                .stream()
                .map(this::toExchangeWebResponse)
                .collect(Collectors.toList());
    }

    private ExchangeWebResponse toExchangeWebResponse(ExchangeResponse exchangeResponse) {
        ExchangeWebResponse exchangeWebResponse = new ExchangeWebResponse();
        BeanUtils.copyProperties(exchangeResponse, exchangeWebResponse);
        return exchangeWebResponse;
    }

   /* @DeleteMapping(
            value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> deleteBook(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteBook(bookId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }*/

}
