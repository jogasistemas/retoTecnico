package com.exchange.reactive.service.exchange;

import com.exchange.reactive.servicedto.response.ExchangeResponse;
import com.exchange.reactive.entity.Exchange;
import com.exchange.reactive.repository.ExchangeRepository;
import com.exchange.reactive.servicedto.request.AddExchangeRequest;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExchangeServiceImplTest {

    @Mock
    private ExchangeRepository exchangeRepository;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void AddExchange_Success_ReturnSingleOfAdded() {

        when(exchangeRepository.save(any(Exchange.class)))
                .thenReturn(new Exchange("1", 200,"S","D",50.0,4.0));

        Exchange exchange = new  Exchange("1", 200,"S","D",50.0,4.0);
        exchangeService.addExchange(new AddExchangeRequest(200, "S",4.0))
                .test()
                .assertComplete()
                .assertNoErrors()
                .awaitTerminalEvent();

        verify(exchangeRepository, times(1)).save(any(Exchange.class));
    }

    @Test
    public void GetAllExchanges_Success_ReturnSingleOfExchangeResponseList() {
        Exchange exchange1 = new Exchange("1", 200,"S","D",50.0,4.0);
        Exchange exchange2 = new Exchange("2", 300,"D","S",1200.0,4.0);

        when(exchangeRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(
                        Arrays.asList(exchange1, exchange2)));

        TestObserver<List<ExchangeResponse>> testObserver = exchangeService.getAllExchanges(1, 1).test();

        testObserver.awaitTerminalEvent();

        testObserver.assertValue(bookResponses -> bookResponses.get(0).getId().equals("1") && bookResponses.get(1).getId().equals("2"));

        verify(exchangeRepository, times(1)).findAll(any(PageRequest.class));
    }

}