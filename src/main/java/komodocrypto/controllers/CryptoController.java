package komodocrypto.controllers;

import komodocrypto.exceptions.TableEmptyException;
import komodocrypto.model.GeneralResponse;
import komodocrypto.services.data_collection.CryptoCompareHistoricalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CryptoController {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    @RequestMapping("/historicaldata")
    public GeneralResponse addPriceHistorical() {
        return historicalService.switchDataOperations();
    }

    @RequestMapping("/socialdata")
    public GeneralResponse addSocialData() { return historicalService.addSocial(); }

    // Adds news by category.
    @PostMapping(value = {"/news/{categories}", "/news/"})
    public GeneralResponse addNews(@PathVariable(value = "categories", required = false) String categories) {
        return historicalService.addNews(categories);
    }

    // Gets news by specified categories. Returns all news items if categories not included.
    @GetMapping(value = {"/news/{categories}", "/news/"})
    public GeneralResponse getNews(@PathVariable(value = "categories", required = false) String categories)
            throws TableEmptyException {
        return historicalService.getNews(categories);
    }
}
