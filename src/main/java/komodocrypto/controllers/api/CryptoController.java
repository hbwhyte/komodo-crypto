package komodocrypto.controllers.api;

import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.model.RootResponse;
import komodocrypto.services.data_collection.CryptoCompareHistoricalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
public class CryptoController {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    // Gets historical data by period.
    @GetMapping("/historicaldata/byperiod/{period}")
    public RootResponse getDataByPeriod(@PathVariable(value = "period") String period) {
        return historicalService.getDataByPeriod(period);
    }

    // Adds historical data by period and/or number of periods.
    @PostMapping(value = {"historicaldata/byperiod/{period}", "historicaldata/byperiod/{period}/{numRecords}"})
    public RootResponse backloadData(@PathVariable(value = "period") String period,
                                     @PathVariable(value = "numRecords", required = false) Integer numRecords) {
        if (numRecords == null) numRecords = Integer.MAX_VALUE;
        return historicalService.backloadData(period, numRecords);
    }

    // Gets historical data by currency.
    @GetMapping("/historicaldata/bycurrency/{currency}")
    public RootResponse getDataByCurrency(@PathVariable(value = "currency") String currency) {
        return historicalService.getDataByCurrency(currency);
    }

    // Gets historical data by exchange.
    @GetMapping("/historicaldata/byexchange/{exchange}")
    public RootResponse getDataByExchange(@PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByExchange(exchange);
    }

    // Gets historical data by period and by currency.
    @GetMapping("historicaldata/byperiodandcurrency/{period}/{currency}")
    public RootResponse getDataByPeriodAndCurrency(@PathVariable(value = "period") String period,
                                                      @PathVariable(value = "currency") String currency) {
        return historicalService.getDataByPeriodAndCurrency(period, currency);
    }

    // Gets historical data by period and by exchange.
    @GetMapping("historicaldata/byperiodandexchange/{period}/{exchange}")
    public RootResponse getDataByPeriodAndExchange(@PathVariable(value = "period") String period,
                                                      @PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByPeriodAndExchange(period, exchange);
    }

    // Gets historical data by currency and exchange.
    @GetMapping("historicaldata/bycurrencyandexchange/{currency}/{exchange}")
    public RootResponse getDataByCurrencyAndExchange(@PathVariable(value = "currency") String currency,
                                                        @PathVariable(value = "exchange") String exchange) {
        return historicalService.getDataByCurrencyAndExchange(currency, exchange);
    }

    @GetMapping("historicaldata/binance")
    public RootResponse findHistoricalGapsBinance(@RequestParam("period") String period,
                                                  @RequestParam("from") String fromCurrency,
                                                  @RequestParam("to") String toCurrency) {
        return historicalService.findHistoricalGapsBinance(period, fromCurrency, toCurrency);
    }

    // Adds and retrieves social media data.
    @GetMapping("/socialdata")
    public RootResponse addSocialData() { return historicalService.addSocial(); }


    // Adds news by category.
    @PostMapping(value = {"/news/{categories}", "/news/"})
    public RootResponse addNews(@PathVariable(value = "categories", required = false) String categories) {
        return historicalService.addNews(categories);
    }

    // Gets news by specified categories. Returns all news items if categories not included.
    @GetMapping(value = {"/news/{categories}", "/news/"})
    public RootResponse getNews(@PathVariable(value = "categories", required = false) String categories)
            throws TableEmptyException {
        return historicalService.getNews(categories);
    }

}
