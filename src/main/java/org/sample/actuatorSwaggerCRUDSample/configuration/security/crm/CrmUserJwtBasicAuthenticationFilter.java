package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class CrmUserJwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private final String AUTHENTICATION_SIGNATURE_KEY;
    private final String JWT_HEADER_KEY;

    public CrmUserJwtBasicAuthenticationFilter(AuthenticationManager authenticationManager,
                                               final String AUTHENTICATION_SIGNATURE_KEY,
                                               final String JWT_HEADER_KEY) {
        super(authenticationManager);
        this.AUTHENTICATION_SIGNATURE_KEY = AUTHENTICATION_SIGNATURE_KEY;
        this.JWT_HEADER_KEY=JWT_HEADER_KEY;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWT_HEADER_KEY);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = parseToken(request);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken parseToken(HttpServletRequest request) {
        String jwt = request.getHeader(JWT_HEADER_KEY);
        if (!StringUtils.isEmpty(jwt)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(AUTHENTICATION_SIGNATURE_KEY.getBytes())
                        .parseClaimsJws(jwt);

                String username = claimsJws.getBody().getSubject();
                List<String> roles = claimsJws.getBody().get("roles",List.class);
                List<GrantedAuthority> grantedAuthorities = roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                if ("".equals(username) || username == null) {
                    return null;
                }

                return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            } catch (JwtException exception) {
                System.out.println(String.format("Some exception : %s failed : %s", jwt, exception.getMessage()));
                exception.printStackTrace();
            }
        }
        return null;
    }
}
