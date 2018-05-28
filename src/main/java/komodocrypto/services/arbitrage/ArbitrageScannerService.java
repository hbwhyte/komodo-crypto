package komodocrypto.services.arbitrage;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bitbay.BitbayExchange;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.campbx.CampBXExchange;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.coinbase.CoinbaseExchange;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.coinmate.CoinmateExchange;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchange;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.empoex.EmpoExExchange;
import org.knowm.xchange.gatecoin.GatecoinExchange;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.hitbtc.v2.HitbtcExchange;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.loyalbit.LoyalbitExchange;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.taurus.TaurusExchange;
import org.knowm.xchange.therock.TheRockExchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ArbitrageScannerService {

    private ArrayList<Exchange> defaultExchangeList;
    private ArrayList<Exchange> allExchangesList;


    public ArbitrageScannerService() {
        this.defaultExchangeList = this.getDefaultExchangeList();
        //this.allExchangesList = this.getAllAvailableExchangesList();
    }

    /**
     * Scans a given list of exchanges and returns an array of two exchanges for the given currency pair.
     * [0] is the highest priced exchange, while [1] is the lowest.
     * @param exchangesList the exchanges list to be scanned
     * @param currencyPair the crypto currency pair to be scanned
     * @throws IOException
     */
    public Exchange[] getBestArbitrageExchangesForPair(ArrayList<Exchange> exchangesList, CurrencyPair currencyPair) throws IOException {
        //An array that will contain the highest and lowest price for a given coin.
        //[0] is for the highest, [1] for the lowest
        Exchange[] exchangeArray = new Exchange[2];
        MarketDataService marketDataService;
        Ticker currentTicker    = null;
        BigDecimal lowestAsk = null;
        BigDecimal highestBid = null;

        for (Exchange ex: exchangesList) {

            //If the exchange doesn't support the given pair, skip it.
            if(!exchangeSupportsCurrency(ex, currencyPair))
                continue;

            System.out.print("Scanning " + ex.getExchangeSpecification().getExchangeName()
                    + " for " + currencyPair.toString() + " . . . ");
            try {

                //Get the given market last price for the given pair
                marketDataService = ex.getMarketDataService();
                currentTicker = marketDataService.getTicker(currencyPair);
                System.out.println("Bid|Ask: " + currentTicker.getBid() + " - " + currentTicker.getAsk());

                //If it's the first exchange to compare...
                if(exchangeArray[0] == null && exchangeArray[1] == null){
                    exchangeArray[0] = ex;
                    exchangeArray[1] = ex;
                    highestBid  = currentTicker.getBid();
                    lowestAsk   = currentTicker.getAsk();
                }
                //Compare to the highest and lowest priced exchange and replace them if necessary
                else if (currentTicker.getBid().compareTo(highestBid) == 1){
                    highestBid = currentTicker.getBid();
                    exchangeArray[0] = ex;
                } else if (currentTicker.getAsk().compareTo(lowestAsk) == -1){
                    lowestAsk = currentTicker.getAsk();
                    exchangeArray[1] = ex;
                }

            } catch (Exception e) {
                System.out.println("N/A");
                continue;
            }
        }
        System.out.println("Highest priced bid -> " + exchangeArray[0].getExchangeSpecification().getExchangeName()
                + ": " + exchangeArray[0].getMarketDataService().getTicker(currencyPair).getBid());
        System.out.println("Lowest priced ask -> " + exchangeArray[1].getExchangeSpecification().getExchangeName()
                + ": " + exchangeArray[0].getMarketDataService().getTicker(currencyPair).getAsk());
        return exchangeArray;
    }

    /**
     *
     * @return
     */
    public ArrayList<Exchange> getDefaultExchangeList() {
        ArrayList<Exchange> exchangesList = new ArrayList<Exchange>();
        Exchange binance;  try{ binance  = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName()); }  catch (Exception e ){ binance = null;}
        Exchange bittrex;  try{ bittrex  = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName()); }  catch (Exception e ){ bittrex = null;}
        Exchange kraken;   try{ kraken   = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName()); }   catch (Exception e ){ kraken  = null;}
        Exchange gdax;     try{ gdax     = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName()); }     catch (Exception e ){ gdax  = null;}
        Exchange bitstamp; try{ bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName()); } catch (Exception e ){ bitstamp  = null;}
        if(binance  != null) exchangesList.add(binance);
        if(bittrex  != null) exchangesList.add(bittrex);
        if(kraken   != null) exchangesList.add(kraken);
        if(gdax     != null) exchangesList.add(gdax);
        if(bitstamp != null) exchangesList.add(bitstamp);

        return exchangesList;
    }

    public ArrayList<Exchange> getAllAvailableExchangesList() {
        ArrayList<Exchange> exchangesList = new ArrayList<Exchange>();

        Exchange anxv2;    try{ anxv2       = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName()); }       catch (Exception e ){ anxv2 = null;}
        Exchange binance;  try{ binance     = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName()); }   catch (Exception e ){ binance = null;}
        Exchange bitbay;   try{ bitbay      = ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName()); }    catch (Exception e ){ bitbay = null;}
        Exchange bitfinex; try{ bitfinex    = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName()); }  catch (Exception e ){ bitfinex = null;}
        Exchange bitstamp; try{ bitstamp    = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName()); }  catch (Exception e ){ bitstamp = null;}
        Exchange bittrex;  try{ bittrex     = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName()); }   catch (Exception e ){ bittrex = null;}
        Exchange bleutrade;try{ bleutrade   = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getName()); } catch (Exception e ){ bleutrade = null;}
        Exchange btcchina; try{ btcchina    = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName()); }  catch (Exception e ){ btcchina = null;}
        Exchange campbx;   try{ campbx      = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName()); }    catch (Exception e ){ campbx = null;}
        Exchange cexio;    try{ cexio       = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName()); }     catch (Exception e ){ cexio = null;}
        Exchange coinbase; try{ coinbase    = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName()); }  catch (Exception e ){ coinbase = null;}
        Exchange coinfloor;try{ coinfloor   = ExchangeFactory.INSTANCE.createExchange(CoinfloorExchange.class.getName()); } catch (Exception e ){ coinfloor = null;}
        Exchange coinmate; try{ coinmate    = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName()); }  catch (Exception e ){ coinmate = null;}
        Exchange dsx;      try{ dsx         = ExchangeFactory.INSTANCE.createExchange(DSXExchange.class.getName()); }       catch (Exception e ){ dsx = null;}
        Exchange empoex;   try{ empoex      = ExchangeFactory.INSTANCE.createExchange(EmpoExExchange.class.getName()); }    catch (Exception e ){ empoex = null;}
        Exchange gatecoin; try{ gatecoin    = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName()); }  catch (Exception e ){ gatecoin = null;}
        Exchange gdax;     try{ gdax        = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName()); }      catch (Exception e ){ gdax = null;}
        Exchange hitbtc;   try{ hitbtc      = ExchangeFactory.INSTANCE.createExchange(HitbtcExchange.class.getName()); }    catch (Exception e ){ hitbtc = null;}
        Exchange huobi;    try{ huobi       = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName()); }     catch (Exception e ){ huobi = null;}
        Exchange itbit;    try{ itbit       = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName()); }     catch (Exception e ){ itbit = null;}
        Exchange kraken;   try{ kraken      = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName()); }    catch (Exception e ){ kraken = null;}
        Exchange kucoin;   try{ kucoin      = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName()); }    catch (Exception e ){ kucoin = null;}
        Exchange loyalbit; try{ loyalbit    = ExchangeFactory.INSTANCE.createExchange(LoyalbitExchange.class.getName()); }  catch (Exception e ){ loyalbit = null;}
        Exchange okcoin;   try{ okcoin      = ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName()); }    catch (Exception e ){ okcoin = null;}
        Exchange poloniex; try{ poloniex    = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName()); }  catch (Exception e ){ poloniex = null;}
        Exchange ripple;   try{ ripple      = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName()); }    catch (Exception e ){ ripple = null;}
        Exchange taurus;   try{ taurus      = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName()); }    catch (Exception e ){ taurus = null;}
        Exchange therock;  try{ therock     = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName()); }   catch (Exception e ){ therock = null;}
        Exchange cryptofacilities;   try{ cryptofacilities   = ExchangeFactory.INSTANCE.createExchange(CryptoFacilitiesExchange.class.getName()); }     catch (Exception e ){ cryptofacilities = null;}
        Exchange independentreserve; try{ independentreserve = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class.getName()); }   catch (Exception e ){ independentreserve = null;}
        Exchange mercadobitcoin;     try{ mercadobitcoin     = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName()); }       catch (Exception e ){ mercadobitcoin = null;}

        if(anxv2        != null) exchangesList.add(anxv2);
        if(binance      != null) exchangesList.add(binance);
        if(bitbay       != null) exchangesList.add(bitbay);
        if(bitfinex     != null) exchangesList.add(bitfinex);
        if(bitstamp     != null) exchangesList.add(bitstamp);
        if(bittrex      != null) exchangesList.add(bittrex);
        if(bleutrade    != null) exchangesList.add(bleutrade);
        if(btcchina     != null) exchangesList.add(btcchina);
        if(campbx       != null) exchangesList.add(campbx);
        if(cexio        != null) exchangesList.add(cexio);
        if(coinbase     != null) exchangesList.add(coinbase);
        if(coinfloor    != null) exchangesList.add(coinfloor);
        if(coinmate     != null) exchangesList.add(coinmate);
        if(cryptofacilities     != null) exchangesList.add(cryptofacilities);
        if(dsx          != null) exchangesList.add(dsx);
        if(empoex       != null) exchangesList.add(empoex);
        if(gatecoin         != null) exchangesList.add(gatecoin);
        if(gdax         != null) exchangesList.add(gdax);
        if(hitbtc       != null) exchangesList.add(hitbtc);
        if(huobi        != null) exchangesList.add(huobi);
        if(independentreserve   != null) exchangesList.add(independentreserve);
        if(itbit        != null) exchangesList.add(itbit);
        if(kraken       != null) exchangesList.add(kraken);
        if(kucoin       != null) exchangesList.add(kucoin);
        if(loyalbit     != null) exchangesList.add(loyalbit);
        if(mercadobitcoin   != null) exchangesList.add(mercadobitcoin);
        if(okcoin       != null) exchangesList.add(okcoin);
        if(poloniex     != null) exchangesList.add(poloniex);
        if(ripple       != null) exchangesList.add(ripple);
        if(taurus       != null) exchangesList.add(taurus);
        if(therock      != null) exchangesList.add(therock);

        return exchangesList;

    }

    /**
     * Returns a boolean value indicating if the given exchange suuports the given currency pair.
     * @param ex - An exchange object
     * @param currencyPair - A currency pair object
     * @return true if the currency pair is supported, false if it is not
     */

    public boolean exchangeSupportsCurrency(Exchange ex, CurrencyPair currencyPair){
        if(ex.getExchangeMetaData().getCurrencyPairs().containsKey(currencyPair))
            return true;
        else
            return false;
    }
}
