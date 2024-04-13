package wf.garnier.devoxbe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    // auth.requestMatchers("/private*").authenticated();
                    // auth.anyRequest().permitAll();
                    auth.requestMatchers("/","/public","/favicon.svg","/css/*","/error").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .addFilterBefore(new RobotFilter(), UsernamePasswordAuthenticationFilter.class)
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
    
}
