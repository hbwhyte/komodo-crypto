package komodocrypto.controllers.mvc;

import komodocrypto.model.arbitrage.ArbitrageModel;
import komodocrypto.services.arbitrage.ArbitrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
@RequestMapping("/komodo")
public class UserController {
    @Autowired
    ArbitrageService arbitrageService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value ={"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model){
/*        ArbitrageModel am = arbitrageService.getArbitrageOps();
            am.getCurrencyPair() will replace the attribute value on the addAttribute method

 */
        ArbitrageModel am = new ArbitrageModel();
        am.setCurrencyPair("BTC/ETH");
        am.setHighBid(BigDecimal.valueOf(0.856740));
        am.setLowAsk(BigDecimal.valueOf(0.754602));
        am.setDifference(BigDecimal.valueOf(0.102138));

        model.addAttribute("currencyPair", am.getCurrencyPair());
        model.addAttribute("highBid", am.getHighBid());
        model.addAttribute("lowAsk", am.getLowAsk());
        model.addAttribute("difference", am.getDifference());
        return "home";
    }
    @RequestMapping(value={"/user"}, method = RequestMethod.GET)
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<ArbitrageModel> arbitrage = arbitrageService.getArbitrageData();
        modelAndView.addObject("arbitrage", arbitrage);
        modelAndView.setViewName("user_dashboard");
        return modelAndView;
    }
}
