package komodocrypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class KomodoCryptoApplication /*extends SpringBootServletInitializer*/ {

	public static void main(String[] args) {
		SpringApplication.run(KomodoCryptoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

//	@Override
//	protected SpringApplicationBuilder configure (SpringApplicationBuilder application){
//		return application.sources(komodocrypto.KomodoCryptoApplication.class);
//	}
}
