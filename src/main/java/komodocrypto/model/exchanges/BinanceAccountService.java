package komodocrypto.model.exchanges;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BinanceAccountService implements AccountService {

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return null;
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        return null;
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return null;
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return null;
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        return null;
    }
}
