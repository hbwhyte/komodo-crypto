package komodocrypto.mappers.exchanges;

import komodocrypto.model.exchanges.ExchangeWallet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExchangeWalletMapper {

    String SELECT_ALL_EXCHANGE_WALLETS = "SELECT * FROM `komodoDB`.`exchange_wallet` ORDER BY `exchange_wallet_id` DESC;";
    String SELECT_BY_EXCHANGE_ID = "SELECT * FROM `komodoDB`.`exchange_wallet` WHERE `exchange_id` = #{exchange_id} ORDER BY `exchange_wallet_id` DESC;";
    String SELECT_BY_CURRENCY_ID = "SELECT * FROM `komodoDB`.`exchange_wallet` WHERE `currency_id` = #{currency_id} ORDER BY `exchange_wallet_id` DESC;";
    String SELECT_BY_EXCHANGE_AND_CURRENCY_ID = "SELECT * FROM `komodoDB`.`exchange_wallet` " +
            "WHERE `exchange_id` = #{args0} AND `currency_id` = #{args1} ORDER BY `exchange_wallet_id` DESC;";
    String INSERT_NEW_DATA = "INSERT INTO `komodoDB`.`exchange_wallet` " +
            "(`currency_id`, `deposit_address`, `total`, `available`, `frozen`, `borrowed`, `loaned`, `withdrawing`, `depositing`, `portfolio_id`, `exchange_id`) " +
            "VALUES (#{currency_id}, #{deposit_address}, #{total}, #{available}, #{frozen}, #{borrowed}, #{loaned}, #{withdrawing}, #{depositing}, #{portfolio_id}, #{exchange_id};";

    @Select(SELECT_ALL_EXCHANGE_WALLETS)
    @ResultMap("ExchangeWallet")
    public List<ExchangeWallet> getExchangeWallets();

    @Select(SELECT_BY_EXCHANGE_ID)
    public List<ExchangeWallet> getAllDataByExchangeId(int exchange_id);

    @Select(SELECT_BY_CURRENCY_ID)
    public List<ExchangeWallet> getAllDataByCurrencyId(int currency_id);

    @Select(SELECT_BY_EXCHANGE_AND_CURRENCY_ID)
    public List<ExchangeWallet> getAllDataByExchangeAndCurrencyId(int exchange_id, int currency_id);

    @Insert(INSERT_NEW_DATA)
    public List<ExchangeWallet> insertNewData(ExchangeWallet exchangeWallet);


}
