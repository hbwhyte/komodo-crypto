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

		SalesforceService.authenticate();
		//SalesforceService.queryKomodoUsers();
		SalesforceService.createKomodoUserRyan();
		//SalesforceService.updateKomodoUser();
	}
}
