package komodocrypto.controllers;

import komodocrypto.services.exchanges.bitstamp.BitstampAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class CryptoController {

    @Autowired
    BitstampAccount bitstampAccount;

    @RequestMapping(method = RequestMethod.GET, value = "/bitstamp")
    public void getAccountInfo() throws IOException{
        bitstampAccount.accountInfo();
    }

}
