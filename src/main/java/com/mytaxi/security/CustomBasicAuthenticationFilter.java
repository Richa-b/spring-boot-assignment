package com.mytaxi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.access.AccessDeniedException;
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
import java.util.Objects;

import static com.mytaxi.security.SecurityConstants.AUTH_TOKEN_KEY_NAME;
import static com.mytaxi.security.SecurityConstants.SECRET;

@CommonsLog
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    CustomBasicAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(AUTH_TOKEN_KEY_NAME);
        if (Objects.isNull(token)) {
            chain.doFilter(req, res);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        log.info("-> Verifying User From JWT Token");
        String token = request.getHeader(AUTH_TOKEN_KEY_NAME);
        String userName;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("", ""));
            // parse the token.
            userName = claimsJws.getBody().getSubject();
            log.info(" User Verified from token");
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Session Expired::", expiredJwtException);
            throw new AccessDeniedException("Session Expired! Please Login Again.");
        } catch (Exception e) {
            log.error("Exception Occurred :", e);
            throw new AccessDeniedException(AUTH_TOKEN_KEY_NAME + " is not valid");
        }

        if (Objects.nonNull(userName)) {
            return new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
        }
        return null;
    }
}
