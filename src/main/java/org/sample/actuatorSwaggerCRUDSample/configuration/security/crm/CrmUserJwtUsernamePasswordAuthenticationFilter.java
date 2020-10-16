package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class CrmUserJwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public CrmUserJwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,String loginEndpoint) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(loginEndpoint);
        this.objectMapper=new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        CrmUserLoginRequestDto crmUserLoginRequestDto =null;
        try {
            crmUserLoginRequestDto = objectMapper.readValue(request.getInputStream(),CrmUserLoginRequestDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(crmUserLoginRequestDto.getLogin(),crmUserLoginRequestDto.getPassword());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKeyBytes = "jwt-secret".getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKeyBytes), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "type")
                .setIssuer("Azericard LLC")
                .setAudience("internal.azericard")
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();

        response.addHeader("internal.ldap.authentication.bearer.jwt", token);
    }
}