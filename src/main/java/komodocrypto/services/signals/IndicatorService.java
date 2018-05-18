package komodocrypto.services.signals;

import javafx.util.Pair;
import komodocrypto.exceptions.custom_exceptions.IndicatorException;
import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.mappers.CryptoMapper;
import komodocrypto.model.cryptocompare.historical_data.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.ta4j.core.*;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.*;
import java.util.Set;

@Service
public class IndicatorService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CryptoMapper cryptoMapper;

    /**
     * @param type         the indicator ("SMA" for Simple Moving Average, "EMA" for Exponential Moving Average)
     * @param toCurrency   the base currency ("BTC")
     * @param fromCurrency the counter currency ("ETH")
     * @param trailing     the number of trailing days
     * @return a pair with the indicator type and the calculated indicator as Decimal
     */
    public Pair<String, Decimal> calculateDailyIndicator(String type, String fromCurrency, String toCurrency, int trailing)
            throws IndicatorException, TableEmptyException {

        TimeSeries series = buildDailySeries(fromCurrency, toCurrency);

        // not enough data trailing data
        if (series.getBarCount() < trailing) {
            throw new IndicatorException("insufficient daily historical data", HttpStatus.BAD_REQUEST);
        }

        ClosePriceIndicator last = new ClosePriceIndicator(series);

        Indicator<Decimal> indicator;
        type = type.toUpperCase();

        switch (type) {
            case "SMA":
                indicator = new SMAIndicator(last, trailing);
                break;

            case "EMA":
                indicator = new EMAIndicator(last, trailing);
                break;

            default:
                throw new IllegalArgumentException();
        }

        Decimal output = indicator.getValue(series.getEndIndex());

        logger.info("type + indicator for " + series.getName() + " is " + output);
        return new Pair<>(type, output);
    }

    private TimeSeries buildDailySeries(String fromCurrency, String toCurrency) throws TableEmptyException {

        Data[] dataArray = cryptoMapper.getDataDailyByPairSorted(fromCurrency, toCurrency);

        // no data found
        if (dataArray.length <= 0) {
            throw new TableEmptyException();
        }

        TimeSeries series = new BaseTimeSeries(fromCurrency + toCurrency);

        for (Data data : dataArray) {

            // ensure correct currency pair
            if (data.getToCurrency().equals(toCurrency)) {

                // convert data
                long epoch = data.getTime();
                Instant temp = Instant.ofEpochSecond(epoch);
                ZonedDateTime date = ZonedDateTime.ofInstant(temp, ZoneOffset.UTC);

                Decimal open = Decimal.valueOf(data.getOpen());
                Decimal high = Decimal.valueOf(data.getHigh());
                Decimal low = Decimal.valueOf(data.getLow());
                Decimal close = Decimal.valueOf(data.getClose());
                Decimal volume = Decimal.valueOf(data.getVolumeFrom());

                // create a bar for rate data (date, open, high, low, close, volume)
                Bar bar = new BaseBar(date, open, high, low, close, volume);

                // add bar to series
                series.addBar(bar);

            }

        }

        logger.info("timeseries built for " + series.getName());
        return series;
    }

}
