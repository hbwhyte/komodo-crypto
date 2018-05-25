package komodocrypto.services.asset_tracking;

import komodocrypto.mappers.GroupPortfolioMapper;
import komodocrypto.model.database.Exchange;
import komodocrypto.model.database.GroupPortfolio;
import org.springframework.beans.factory.annotation.Autowired;

public class AssetTrackingService {

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    //updateAssetsUnderManagement() - this method will be called when the Ivan's trade executes
    // - he can pass this method any/all information he has about that trade:
    //                    - the currency
    //                    - the exchanges
    //                    - the prices
    //                    - the buy/sell amounts

    public void updateAssetsUnderManagement(int currency_id, Exchange exchange_name, double price, double amount, int signal){

        /* signal determines buy, sell, or transfer
                if buy
                    amount*price-exchangename.getBuy_fee
                if sell
                    amount*price-exchangename.getSell_fee
                if transfer
                    amount*price-exchangename.getTransfer_fee
         */



    }
}
