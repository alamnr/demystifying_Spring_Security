package wf.garnier.devoxbe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            AuthenticationEventPublisher publisher) throws Exception {

        {
            http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationEventPublisher(publisher);
        }
        var authManager =new ProviderManager( new RobotAuthenticationProvider(List.of("beep-boop","boop-beep")));
        authManager.setAuthenticationEventPublisher(publisher);
        
        return http
                .authorizeHttpRequests(auth -> {
                    // auth.requestMatchers("/private*").authenticated();
                    // auth.anyRequest().permitAll();
                    auth.requestMatchers("/","/public","/favicon.svg","/css/*","/error").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .oauth2Login(withDefaults())
                .addFilterBefore(new RobotFilter(authManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new DanielAuthenticationProvider())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
             User.builder().username("user")
             .password("{noop}password")
             .authorities("ROLE_user")
             .build());
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successListener(){
        return event -> 
            //  LOGGER.info(String.format("😍 Success [%s] %s",event.getAuthentication().getClass().getSimpleName(),
            //    event.getAuthentication().getName() ));
            LOGGER.info("😍 Success [{}] {}",event.getAuthentication().getClass().getSimpleName(),
            event.getAuthentication().getName());
           
        
    }
}
