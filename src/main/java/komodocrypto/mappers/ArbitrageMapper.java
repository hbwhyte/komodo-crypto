package komodocrypto.mappers;

import komodocrypto.model.arbitrage.ArbitrageModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ArbitrageMapper {

    String GET_DATA = "SELECT * FROM `KomodoDB`.arbitrage";

    @Select(GET_DATA)
    public ArrayList<ArbitrageModel> getData();
    
}
