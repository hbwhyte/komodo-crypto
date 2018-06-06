
/*
package komodocrypto.controllers.api;

import komodocrypto.exceptions.custom_exceptions.IndicatorException;
import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.model.RootResponse;
import komodocrypto.services.signals.IndicatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignalController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IndicatorService indicatorService;

    */
/**
     * Calculates daily technical indicator
     * @param type the indicator ("SMA" for Simple Moving Average, "EMA" for Exponential Moving Average)
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @param trailing the number of trailing days
     * @return a pair with the indicator type and the calculated indicator as Decimal
     *//*

    @GetMapping("/dailyindicator")
    public RootResponse indicator(@RequestParam(value="type") String type,
                                  @RequestParam(value="fromcurrency") String fromCurrency,
                                  @RequestParam(value="tocurrency") String toCurrency,
                                  @RequestParam(value="trailing") int trailing) throws IndicatorException {

        logger.info("API Call: /dailyindicator?type=" + type + "&fromcurrency=" +
                    fromCurrency + "&tocurrency=" + toCurrency + "&count=" + trailing);

        // ensure proper trailing input
        if (trailing <= 0) {
            throw new IndicatorException("trailing days must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        return new RootResponse(HttpStatus.OK, "OK",
                                indicatorService.calculateDailyIndicator(type, fromCurrency, toCurrency, trailing));
    }

}
*/
