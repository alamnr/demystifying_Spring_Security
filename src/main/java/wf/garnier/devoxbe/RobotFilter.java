package wf.garnier.devoxbe;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RobotFilter extends OncePerRequestFilter {

    private static final  String ROBOT_HEADER ="x-robot-password";
    private  static final Logger LOGGER = LoggerFactory.getLogger(RobotFilter.class);

    private final AuthenticationManager authManager;

    
    public RobotFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
            
        // 0. Should we execute the filter ?
        if(!Collections.list(request.getHeaderNames()).contains(ROBOT_HEADER))
        {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. Do Common tasks , logging , authentication etc
        LOGGER.info("😎😎 Hello from Robot Filter");
        var password = request.getHeader(ROBOT_HEADER);

        try {
            var authRequest = RobotAuthentication.unAuthenticated(password);
            var authentication = authManager.authenticate(authRequest);
            
            SecurityContext newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
            
        } catch(AuthenticationException e) {

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().write("You are not Ms Robot 😎" + e.getMessage());
        }
        

        /*
        if (!"beep-boop".equals(password)) {

            // no no
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().write("You are not Ms Robot 😎");
            return;
        } 
            
         // ok
         SecurityContext newContext = SecurityContextHolder.createEmptyContext();
         newContext.setAuthentication(new RobotAuthentication());
         SecurityContextHolder.setContext(newContext);
         filterChain.doFilter(request, response);*/
     
 
        // 2.  Do the rest
        
    }
    
}
