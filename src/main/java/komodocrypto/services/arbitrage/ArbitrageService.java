package komodocrypto.services.arbitrage;

import komodocrypto.mappers.ArbitrageMapper;
import komodocrypto.model.arbitrage.ArbitrageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArbitrageService {
    @Autowired
    ArbitrageMapper arbitrageMapper;

    public ArrayList<ArbitrageModel> getArbitrageData(){
      ArrayList<ArbitrageModel> ad = arbitrageMapper.getData();
      return ad;
    };
}
