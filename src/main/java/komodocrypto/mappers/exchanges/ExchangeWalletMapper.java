package komodocrypto.mappers.exchanges;

import komodocrypto.model.exchanges.ExchangeWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExchangeWalletMapper {

    String SELECT_ALL_EXCHANGE_WALLETS = "SELECT * FROM `komodoDB`.`exchange_wallet` ORDER BY `total` DESC;";

    @Select(SELECT_ALL_EXCHANGE_WALLETS)
    @ResultMap("ExchangeWallet")
    public List<ExchangeWallet> getExchangeWallets();
}
