package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import io.jsonwebtoken.*;


import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDesriptor;
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
    private final CommonResponseDTO commonResponseDTO;
    private final CommonLogger LOGGER;
    private final IMultiLanguageComponent multiLanguageComponent;

    public CrmUserJwtBasicAuthenticationFilter(final AuthenticationManager authenticationManager,
                                               final String AUTHENTICATION_SIGNATURE_KEY,
                                               final String JWT_HEADER_KEY,
                                               final CommonResponseDTO commonResponseDTO,
                                               final CommonLogger LOGGER,
                                               final IMultiLanguageComponent multiLanguageComponent) {
        super(authenticationManager);
        this.AUTHENTICATION_SIGNATURE_KEY = AUTHENTICATION_SIGNATURE_KEY;
        this.JWT_HEADER_KEY=JWT_HEADER_KEY;
        this.commonResponseDTO=commonResponseDTO;
        this.LOGGER=LOGGER;
        this.multiLanguageComponent=multiLanguageComponent;
    }

    private final String CRM_USERS_SECURITY_TOKEN_EXPIRED="CRM_USERS_SECURITY_TOKEN_EXPIRED";
    private final String CRM_USERS_SECURITY_TOKEN_JWT_MALFORMED = "CRM_USERS_SECURITY_TOKEN_JWT_MALFORMED";
    private final String CRM_USERS_SECURITY_TOKEN_SECURITY_EXCEPTION = "CRM_USERS_SECURITY_TOKEN_SECURITY_EXCEPTION";
    private final String CRM_USERS_SECURITY_TOKEN_PARSING_UNHANDLED_EXCEPTION = "CRM_USERS_SECURITY_TOKEN_PARSING_UNHANDLED_EXCEPTION";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader(JWT_HEADER_KEY);

        if (StringUtils.isEmpty(jwtToken)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = parseToken(jwtToken);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken parseToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(AUTHENTICATION_SIGNATURE_KEY.getBytes())
                    .parseClaimsJws(jwtToken);

            String login = claimsJws.getBody().getSubject();
            List<String> roles = claimsJws.getBody().get("roles", List.class);
            List<GrantedAuthority> grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

            if (StringUtils.isEmpty(login)) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(login, null, grantedAuthorities);
        }
        catch (ExpiredJwtException expiredJwtException) {
            ErrorDesriptor errorDesriptor = new ErrorDesriptor(expiredJwtException.getStackTrace()[0].getClassName(),
                    CRM_USERS_SECURITY_TOKEN_EXPIRED,
                    multiLanguageComponent.getMessageByKey(CRM_USERS_SECURITY_TOKEN_EXPIRED),
                    expiredJwtException.getClass().getCanonicalName());
            commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                    HttpServletResponse.SC_FORBIDDEN,
                    new CommonMessageDTO("error",
                            errorDesriptor.getMessageKey(),
                            errorDesriptor.getMessage()),
                    errorDesriptor);
        }
        catch (MalformedJwtException malformedJwtException){
            ErrorDesriptor errorDesriptor = new ErrorDesriptor(malformedJwtException.getStackTrace()[0].getClassName(),
                    CRM_USERS_SECURITY_TOKEN_JWT_MALFORMED,
                    multiLanguageComponent.getMessageByKey(CRM_USERS_SECURITY_TOKEN_JWT_MALFORMED),
                    malformedJwtException.getClass().getCanonicalName());
            commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                    HttpServletResponse.SC_FORBIDDEN,
                    new CommonMessageDTO("error",
                            errorDesriptor.getMessageKey(),
                            errorDesriptor.getMessage()),
                    errorDesriptor);
        }
        catch (SignatureException signatureException){
            ErrorDesriptor errorDesriptor = new ErrorDesriptor(signatureException.getStackTrace()[0].getClassName(),
                    CRM_USERS_SECURITY_TOKEN_SECURITY_EXCEPTION,
                    multiLanguageComponent.getMessageByKey(CRM_USERS_SECURITY_TOKEN_SECURITY_EXCEPTION),
                    signatureException.getClass().getCanonicalName());
            commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                    HttpServletResponse.SC_FORBIDDEN,
                    new CommonMessageDTO("error",
                            errorDesriptor.getMessageKey(),
                            errorDesriptor.getMessage()),
                    errorDesriptor);
        }
        catch (Exception exception){
            ErrorDesriptor errorDesriptor = new ErrorDesriptor(exception.getStackTrace()[0].getClassName(),
                    CRM_USERS_SECURITY_TOKEN_PARSING_UNHANDLED_EXCEPTION,
                    String.format(multiLanguageComponent.getMessageByKey(CRM_USERS_SECURITY_TOKEN_PARSING_UNHANDLED_EXCEPTION),exception.getMessage()),
                    exception.getClass().getCanonicalName());
            commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                    HttpServletResponse.SC_FORBIDDEN,
                    new CommonMessageDTO("error",
                            errorDesriptor.getMessageKey(),
                            errorDesriptor.getMessage()),
                    errorDesriptor);
        }
        return null;
    }
}
