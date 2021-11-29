package com.exchange.reactive.web;

import com.exchange.reactive.dto.User;
import com.exchange.reactive.service.exchange.ExchangeService;
import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import com.exchange.reactive.servicedto.request.UpdateExchangeRequest;
import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.webdto.request.UpdateExchangeWebRequest;
import com.exchange.reactive.webdto.response.BaseWebResponse;
import com.exchange.reactive.webdto.response.ExchangeWebResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
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
    public Single<ResponseEntity<List<ExchangeWebResponse>>> getAllExchanges(@RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                                              @RequestParam(value = "page", defaultValue = "0") int page) {
        return exchangeService.getAllExchanges(limit, page)
                .subscribeOn(Schedulers.io())
                .map(exchangeResponses -> ResponseEntity.ok(toExchangeWebResponseList(exchangeResponses)));
    }

    private List<ExchangeWebResponse> toExchangeWebResponseList(List<ExchangeResponse> exchangeResponseList) {
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

    @GetMapping(
            value = "/{exchangeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<ExchangeWebResponse>>> getExchangeDetail(@PathVariable(value = "exchangeId") String exchangeId) {
        return exchangeService.getExchangeDetail(exchangeId)
                .subscribeOn(Schedulers.io())
                .map(exchangeResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toExchangeWebResponse(exchangeResponse))));
    }

    @PostMapping(
            value = "/{exchangeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> updateExchange(@PathVariable(value = "exchangeId") String exchangeId,
                                                              @RequestBody UpdateExchangeWebRequest updateExchangeWebRequest) {
        return exchangeService.updateExchange(toUpdateExchangeRequest(exchangeId, updateExchangeWebRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    private UpdateExchangeRequest toUpdateExchangeRequest(String exchangeId, UpdateExchangeWebRequest updateExchangeWebRequest) {
        UpdateExchangeRequest updateExchangeRequest = new UpdateExchangeRequest();
        BeanUtils.copyProperties(updateExchangeWebRequest, updateExchangeRequest);
        updateExchangeRequest.setId(exchangeId);
        return updateExchangeRequest;
    }
}
