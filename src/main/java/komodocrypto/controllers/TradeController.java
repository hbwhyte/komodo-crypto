package komodocrypto.controllers;

import komodocrypto.model.arbitrage.ArbitrageOutput;
import komodocrypto.services.arbitrage.ArbitrageService;
import komodocrypto.services.exchanges.generic.KomodoAccountInfo;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    KomodoAccountInfo komodoAccountInfo;

    @Autowired
    ArbitrageService arbitrageService;

    /**
     * [GET] Return account info
     *
     * @return JSON of Account object
     */
//    @GetMapping("/account")
//    public KomodoAccountInfo getExchangeAccountInfo(@RequestParam(value = "exchange") String exchange) {
//        return komodoAccountInfo.getAccountInfo();
//    }

    /**
     * [POST] Arbitrage trade endpoint
     */
    @PostMapping("/arbitrage")
    public ArbitrageOutput makeArbitrageTrade(@RequestBody Exchange exchangeHigh, Exchange exchangeLow,
                                              CurrencyPair currencyPair, BigDecimal amount) {
        return arbitrageService.makeMarketTrade(exchangeHigh,exchangeLow,currencyPair,amount);
    }
}
