
package wf.garnier.devoxbe;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alam
 */

 @RestController
public class JsonController {

    @GetMapping("/public")
    public String publicPage(){
        return "Hello Devoxx!";
    }

    @GetMapping("/private-1")
    public String privatePage(Authentication auth){
        return "This is private. Auth - "+ getName(auth);
    }

    private String getName(Authentication auth) {
        /*
        if(auth.getPrincipal() instanceof  OidscUser oidscUser)
        {
            return oidscUser.getEmail();
        } */
        return auth!=null ? auth.getName() : "";
    }

    // private String getName(Authentication auth) {
    //    return Optional.of(auth.getPrincipal())
    //             .filter(OidcUser.class::isInstance)
    //             .map(OidcUser.class::cast)
    //             .map(OidcUser::getEmail)
    //             .orElse(auth.getName());
    // }
}
