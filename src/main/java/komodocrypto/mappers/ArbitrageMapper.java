package komodocrypto.mappers;

import komodocrypto.model.arbitrage.ArbitrageModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ArbitrageMapper {

    /*table has fake data for now but will be replaced with the main DB when data is ready*/
    String GET_DATA = "SELECT * FROM `komodoDB`.arbitrage";
    String PERSIST_ARBITRAGE_OPP = "";

    @Select(GET_DATA)
    public ArrayList<ArbitrageModel> getData();

    @Insert(PERSIST_ARBITRAGE_OPP)
    public int addArbitrageData(ArbitrageModel arbitrageOppData);
    
}
