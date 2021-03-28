package com.utn2019.avanzada2.tp9.config.auth;

import static java.util.Optional.ofNullable;

import com.utn2019.avanzada2.tp9.util.SecurityConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            final UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ignored) {
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        return ofNullable(request)
                .map(req -> req.getHeader(SecurityConstants.HEADER_STRING))
                .map(token -> Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")))
                .map(claims -> new UsernamePasswordAuthenticationToken(claims.getBody().getSubject(), null, new ArrayList<>()))
                .orElse(null);
    }
}
