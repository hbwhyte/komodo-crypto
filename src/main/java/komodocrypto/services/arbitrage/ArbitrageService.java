package komodocrypto.services.arbitrage;

import komodocrypto.mappers.ArbitrageMapper;
import komodocrypto.model.arbitrage.ArbitrageModel;
import komodocrypto.model.user.User;
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

//    creates a fake user so mvc views can work
    public User tempUser(){
        User user = new User();
        user.setFirst_name("Unicorn");
        user.setLast_name("Badger");
        user.setEmail("UnicornBadger@fake.com");
        user.setPassword("password");
        return user;
    }
}
