package komodocrypto.services.signals;

import komodocrypto.exceptions.custom_exceptions.IndicatorException;
import komodocrypto.model.signals.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;

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

            float rating;

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

            if ((rating = simpleTrend(pair[0], pair[1])) != 0) {
                String name = (rating > 0) ? "Potential Bullish" : "Potential Bearish";

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
     * Determines if there is a buy/sell signal based on current price compared to fifty day simple moving average
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @return 0.5 if buy, -0.5 if sell, 0 if no signal
     */
    private float simpleTrend(String fromCurrency, String toCurrency) throws IndicatorException {

        TimeSeries series = indicatorService.dailySeries(fromCurrency, toCurrency);
        Decimal fiftyDay =  indicatorService.calculateDailyIndicator("sma", fromCurrency, toCurrency, 50);

        Decimal close = series.getLastBar().getClosePrice();

        // if current price is greater than fifty day Simple Moving Average
        if (close.isGreaterThan(fiftyDay)) {
            return 0.5f;
        }

        // if current price is less than fifty day Simple Moving Average
        if (close.isLessThan(fiftyDay)) {
            return -0.5f;
        }

        return 0;

    }

    /**
     * Determines if there is a buy/sell signal using the Golden Cross and Death Cross
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @return 1 if buy (golden cross), -1 if sell (death cross), 0 if no signal
     */
    private float crossGoldenDeath(String fromCurrency, String toCurrency) throws IndicatorException {

        Decimal prevShortTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 51);
        Decimal shortTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 50);
        Decimal longTerm = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 200);

        // short crosses above long
        if (shortTerm.isGreaterThan(longTerm) && prevShortTerm.isLessThan(longTerm)) {
            return 1.0f;
        }

        // short crosses below long
        if (shortTerm.isLessThan(longTerm) && prevShortTerm.isGreaterThan(longTerm)) {
            return -1.0f;
        }

        return 0;
    }

    /**
     * Determines if there is a buy/sell signal using Moving Average Convergence Divergence
     * @param fromCurrency the base currency
     * @param toCurrency the counter currency
     * @return 1 if buy, -1 if sell, 0 if no signal
     */
    private float crossMACD(String fromCurrency, String toCurrency) throws IndicatorException {
        Decimal twentySixDay = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 26);
        Decimal twelveDay = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 12);

        Decimal macd = twentySixDay.minus(twelveDay);
        Decimal signal = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 9);
        Decimal prevSignal = indicatorService.calculateDailyIndicator("ema", fromCurrency, toCurrency, 10);

        // macd crosses above signal
        if (macd.isGreaterThan(signal) && macd.isLessThan(prevSignal)) {
            return 1.0f;
        }

        // macd crosses below signal
        if (macd.isLessThan(signal) && macd.isGreaterThan(prevSignal)) {
            return -1.0f;
        }

        return 0;
    }
}
