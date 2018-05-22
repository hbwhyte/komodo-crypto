package komodocrypto;

import komodocrypto.services.salesforce.SalesforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KomodoCryptoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KomodoCryptoApplication.class, args);

		// Don't run it this way to test functional POST - run the TestController class in the salesforce/ryan_force_sdk package manually
        // there's a main method in there

//		SalesforceService.authenticate();
//		SalesforceService.queryKomodoUsers();
//		SalesforceService.createKomodoUser();
		//SalesforceService.updateKomodoUser();
	}
}
