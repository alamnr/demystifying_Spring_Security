package wf.garnier.devoxbe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {

    private final List<String> passwords = new ArrayList<>();
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Step-1: 
        // this initializes objects
        http.authenticationProvider(new RobotAuthenticationProvider(passwords));
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        // Step-2: 
        // this also initializes objects ,  can   reuse from step-1 and other configurer
        var authManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RobotFilter(authManager), FilterSecurityInterceptor.class);
    }
    public RobotLoginConfigurer password(String password){
        this.passwords.add(password);
        return this;
    }
}
