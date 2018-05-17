package komodocrypto.services.signals;

import komodocrypto.model.signals.DailyIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.util.ArrayList;

public class IndicatorService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Calculates daily signals for the given currency pair
     * @param fromCurrency the base currency symbol
     * @param toCurrency the counter currency symbol
     * @return an ArrayList of DailyIndicator data
     */
    public ArrayList<DailyIndicator> getIndicators(String fromCurrency, String toCurrency) {

        // TODO avoid building this every API Call (store timeseries or cache method with ehcache)
        // pull data from DB
        ArrayList<?> data = new ArrayList<>(); // TODO

        // create a TimeSeries to calculate signals on
        TimeSeries series = buildDailyTimeSeries(data);
        ClosePriceIndicator close = new ClosePriceIndicator(series);

        // calculate 30 tick simple moving average
        SMAIndicator sma = new SMAIndicator(close, 30);

        // calculate 30 tick exponential moving average
        EMAIndicator ema = new EMAIndicator(close, 30);

        // build ArrayList of DailyIndicators
        ArrayList<DailyIndicator> indicators = new ArrayList<>();

        // skip first 30 bars (not enough trailing data)
        for (int i = 30; i < series.getBarCount(); i++) {

            DailyIndicator indicator = new DailyIndicator();
            // build object

            indicator.setDaily_id(0); // TODO update
            indicator.setSimpleMovingAverage(sma.getValue(i).doubleValue());
            indicator.setExponentialMovingAverage(ema.getValue(i).doubleValue());

            indicators.add(indicator);
        }

        logger.info("signals calculated");
        return indicators;
    }

    /**
     * Builds a TimeSeries from the given daily data
     * @param data an ArrayList of Daily prices
     * @return a TimeSeries with Bars corresponding to `daily` table data
     */
    private TimeSeries buildDailyTimeSeries(ArrayList<?> data) {
        // TODO
        logger.info("timeseries built");
        return null;
    }
}
