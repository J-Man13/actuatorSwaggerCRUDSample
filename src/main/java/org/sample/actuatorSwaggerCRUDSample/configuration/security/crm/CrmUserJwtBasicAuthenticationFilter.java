package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



public class CrmUserJwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private final String AUTHENTICATION_SIGNATURE_KEY;

    public CrmUserJwtBasicAuthenticationFilter(AuthenticationManager authenticationManager,
                                               final String AUTHENTICATION_SIGNATURE_KEY) {
        super(authenticationManager);
        this.AUTHENTICATION_SIGNATURE_KEY = AUTHENTICATION_SIGNATURE_KEY;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("internal.ldap.authentication.bearer.jwt");

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = parseToken(request);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken parseToken(HttpServletRequest request) {
        String jwt = request.getHeader("internal.ldap.authentication.bearer.jwt");
        if (!StringUtils.isEmpty(jwt)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(AUTHENTICATION_SIGNATURE_KEY.getBytes())
                        .parseClaimsJws(jwt);

                String username = claimsJws.getBody().getSubject();

                if ("".equals(username) || username == null) {
                    return null;
                }

                return new UsernamePasswordAuthenticationToken(username, null, null);
            } catch (JwtException exception) {
                System.out.println(String.format("Some exception : %s failed : %s", jwt, exception.getMessage()));
                exception.printStackTrace();
            }
        }
        return null;
    }
}
