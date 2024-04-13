package wf.garnier.devoxbe;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RobotFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RobotFilter.class);
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Do Common tasks , logging , authentication etc
        LOGGER.info("😎😎 Hello from Robot Filter");
        var password = request.getHeader("x-robot-password");
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
            filterChain.doFilter(request, response);
            
        // 2.  Do the rest
        
    }
    
}
