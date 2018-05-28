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


    @Select(GET_DATA)
    public ArrayList<ArbitrageModel> getData();
    
}
