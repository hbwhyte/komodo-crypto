package komodocrypto.mappers.exchanges;

import komodocrypto.model.exchanges.ExchangeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExchangeInfoMapper {

    String SELECT_ALL_EXCHANGES = "SELECT * FROM `komodoDB`.`exchange_info`;";

    @Select(SELECT_ALL_EXCHANGES)
    @ResultMap("ExchangeInfo")
    public List<ExchangeInfo> getExchanges();
}
