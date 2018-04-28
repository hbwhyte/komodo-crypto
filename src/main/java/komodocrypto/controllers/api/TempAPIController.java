package komodocrypto.controllers.api;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempAPIController {

    @GetMapping("/test-security")
    public boolean testSecurity() {
        return true;
    }

}
