package komodocrypto.controllers;

import komodocrypto.model.GeneralResponse;
import komodocrypto.services.data_collection.CryptoCompareHistoricalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    @RequestMapping("/gethistoricaldata")
    public GeneralResponse addPriceHistorical() {
        return historicalService.switchDataOperations();
    }

    @RequestMapping("/getsocialdata")
    public GeneralResponse addSocialData() { return historicalService.addSocial();}

}
