package komodocrypto.services.exchanges.interfaces;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;


public interface ExchangeAccountService {

    public String getDepositAddress(Currency currency);
    public Balance getCurrencyBalance(Currency currency);
    public String withdrawFunds(Currency currency, BigDecimal quantity, String address);

}
