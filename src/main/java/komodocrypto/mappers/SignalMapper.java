package komodocrypto.mappers;

import komodocrypto.model.signals.Signal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SignalMapper {

    @Insert("INSERT INTO `komodoDB`.`buy_sell_signals` " +
            "(`description`, `time`, `fromCurrency`, `toCurrency`, `sell_neutral_buy`) " +
            "VALUES (#{description}, #{time}, #{fromCurrency}, #{toCurrency}, #{sell_neutral_buy}); ")
    public boolean insertSignal(Signal signal);


    @Select("SELECT * FROM `komodoDB`.`buy_sell_signals` WHERE `time` >= #{arg1} ; ")
    public ArrayList<Signal> getRecentSignals(long time);


}
