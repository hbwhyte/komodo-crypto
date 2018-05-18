package komodocrypto.controllers;

import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.model.GeneralResponse;
import komodocrypto.model.RootResponse;
import komodocrypto.services.data_collection.CryptoCompareHistoricalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import komodocrypto.services.exchanges.binance.BinanceAccount;
import komodocrypto.services.exchanges.bitstamp.BitstampAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@RestController
public class CryptoController {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    // Get historical data by specified criteria, if any.
    @GetMapping("/historicaldata")
    public GeneralResponse addPriceHistorical() {
        return historicalService.switchDataOperations();
    }

    @GetMapping("/historicaldata/byperiod/{period}")
    public RootResponse getDataByPeriod(@PathVariable(value = "period") String period) {
        return historicalService.getDataByPeriod(period);
    }

    @GetMapping("/historicaldata/bycurrency/{currency}")
    public RootResponse getDataByCurrency(@PathVariable(value = "currency") String currency) {
        return historicalService.getDataByCurrency(currency);
    }

    @GetMapping("/historicaldata/byexchange/{exchange}")
    public RootResponse getDataByExchange(@PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByExchange(exchange);
    }

    @GetMapping("historicaldata/byperiodandcurrency/{period}/{currency}")
    public RootResponse getDataByPeriodAndCurrency(@PathVariable(value = "period") String period,
                                                      @PathVariable(value = "currency") String currency) {
        return historicalService.getDataByPeriodAndCurrency(period, currency);
    }

    @GetMapping("historicaldata/byperiodandexchange/{period}/{exchange}")
    public RootResponse getDataByPeriodAndExchange(@PathVariable(value = "period") String period,
                                                      @PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByPeriodAndExchange(period, exchange);
    }

    @GetMapping("historicaldata/bycurrencyandexchange/{currency}/{exchange}")
    public RootResponse getDataByCurrencyAndExchange(@PathVariable(value = "currency") String currency,
                                                        @PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByCurrencyAndExchange(currency, exchange);
    }


    // Adds and retrieves social media data.
    @RequestMapping("/socialdata")
    public GeneralResponse addSocialData() { return historicalService.addSocial(); }


    // Adds news by category.
    @PostMapping(value = {"/news/{categories}", "/news/"})
    public GeneralResponse addNews(@PathVariable(value = "categories", required = false) String categories) {
        return historicalService.addNews(categories);
    }

    // Gets news by specified categories. Returns all news items if categories not included.
    @GetMapping(value = {"/news/{categories}", "/news/"})
    public GeneralResponse getNews(@PathVariable(value = "categories", required = false) String categories)
            throws TableEmptyException {
        return historicalService.getNews(categories);
    }
    @Autowired
    BitstampAccount bitstampAccount;

    @Autowired
    BinanceAccount binanceAccount;

    @RequestMapping(method = RequestMethod.GET, value = "/bitstamp")
    public void getBitstampAccountInfo() throws IOException{
        bitstampAccount.accountInfo();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/binance/account")
    public void getBinanceAccountInfo() throws IOException{
        binanceAccount.getAccountInfo();
    }
}
