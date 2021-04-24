package dev.ricardovm.demo.spring.customauthentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CustomFilter extends OncePerRequestFilter {

    private static Logger logger = Logger.getLogger(CustomFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (Objects.equals(httpServletRequest.getHeader("backdoor"), "true")) {
            setAuthentication();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthentication() {
        logger.info("Forcing user as authenticated");
        var auth = getAuthenticationData();
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Authentication getAuthenticationData() {
        // Find user in your own authentication system
        return new UsernamePasswordAuthenticationToken(
                "admin",
                "",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
