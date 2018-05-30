package komodocrypto.services.signals;

import komodocrypto.exceptions.custom_exceptions.IndicatorException;
import komodocrypto.model.signals.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.Decimal;

import java.util.ArrayList;

@Service
public class SignalService {

    @Autowired
    IndicatorService indicatorService;

    final String[][] TRACKING = {{"ETH", "BTC"},
                                 {"BCH", "BTC"},
                                 {"LTC", "BTC"},
                                 {"XRP", "BTC"}};

    /**
     * Scans currency pairs to find buy/sell signals
     * @return an ArrayList of Signals
     */
    public ArrayList<Signal> scanSignals() throws IndicatorException {

        ArrayList<Signal> signals = new ArrayList<>();

        // iterate through currency pairs
        for (String[] pair : TRACKING) {

            int rating;

            if ((rating = crossGoldenDeath(pair[0], pair[1])) != 0) {
                String name = (rating > 0) ? "Golden Cross" : "Death Cross";

                Signal s = new Signal();
                s.setName(name);
                s.setFromCurrency(pair[0]);
                s.setToCurrency(pair[1]);
                s.setSell_neutral_buy(rating);
                signals.add(s);
            }

            if ((rating = crossMACD(pair[0], pair[1])) != 0) {
                String name = (rating > 0) ? "Bullish MACD" : "Bearish MACD";

                Signal s = new Signal();
                s.setName(name);
                s.setFromCurrency(pair[0]);
                s.setToCurrency(pair[1]);
                s.setSell_neutral_buy(rating);
                signals.add(s);
            }

        }

        return signals;
    }

    /**
     * Determines if there is a buy/sell signal using the Golden Cross and Death Cross
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @return 1 if buy (golden cross), -1 if sell (death cross), 0 if no signal
     */
    private int crossGoldenDeath(String fromCurrency, String toCurrency) throws IndicatorException {

        Decimal prevShortTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 49);
        Decimal shortTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 50);
        Decimal longTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 200);

        // short crosses above long
        if (shortTerm.isGreaterThan(longTerm) && prevShortTerm.isLessThan(longTerm)) {
            return 1;
        }

        // short crosses below long
        if (shortTerm.isLessThan(longTerm) && prevShortTerm.isGreaterThan(longTerm)) {
            return -1;
        }

        return 0;
    }

    /**
     * Determines if there is a buy/sell signal using Moving Average Convergence Divergence
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @return 1 if buy, -1 if sell, 0 if no signal
     */
    private int crossMACD(String fromCurrency, String toCurrency) throws IndicatorException {
        Decimal twentySixDay = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 26);
        Decimal twelveDay = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 12);

        Decimal macd = twentySixDay.minus(twelveDay);
        Decimal signal = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 9);
        Decimal prevSignal = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 8);

        // macd crosses above signal
        if (macd.isGreaterThan(signal) && macd.isLessThan(prevSignal)) {
            return 1;
        }

        // macd crosses below signal
        if (macd.isLessThan(signal) && macd.isGreaterThan(prevSignal)) {
            return -1;
        }

        return 0;
    }
}
