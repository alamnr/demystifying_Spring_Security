package wf.garnier.devoxbe;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class DanielAuthenticationProvider implements  AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            var userName = authentication.getName();
            if ("daniel".equals(userName)) {
                return UsernamePasswordAuthenticationToken.authenticated("daniel",
                 null, AuthorityUtils.createAuthorityList("ROLE_admin"));
            }
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
}
