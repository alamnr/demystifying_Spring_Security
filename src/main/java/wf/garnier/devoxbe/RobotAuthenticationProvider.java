package wf.garnier.devoxbe;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RobotAuthenticationProvider implements  AuthenticationProvider{

    private final List<String> passwordList;

    
    public RobotAuthenticationProvider(List<String> passwords) {
        this.passwordList = passwords;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var authRequest = (RobotAuthentication)authentication;
        var password = authRequest.getPassword();
        if(!passwordList.contains(password)){
            throw new BadCredentialsException("You are not Ms Robot 😎");
            
        } 
        return RobotAuthentication.authenticated();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthentication.class.isAssignableFrom(authentication);
    }
    
}
