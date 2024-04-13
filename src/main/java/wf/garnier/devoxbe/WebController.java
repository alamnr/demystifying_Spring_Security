package wf.garnier.devoxbe;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String publicPage(){
        return "public";
    }

    @GetMapping("/private")
    public String privatePage(Model model, Authentication auth) {
        // var contextAuth = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println("Inside Local thread : " + contextAuth.getName());
        // Thread t1 = new Thread(){
        //     @Override
        //     public void run() {
        //         var subThreadAuth = SecurityContextHolder.getContext().getAuthentication();
        //         System.out.println("Inside Sub thread : "+subThreadAuth.getName());
        //     }

        // };
        // t1.start();
        // t1.join();
        model.addAttribute("name", auth != null ? getName(auth) : "❤");
        return "private";
    }

    private String getName(Authentication auth) {


        if(auth.getPrincipal() instanceof  OidcUser oidcUser){
            return oidcUser.getEmail();
        }
        return auth.getName();

        /*
         
            
        return Optional.of(auth.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElse(auth.getName());

         */
    }

    
}
