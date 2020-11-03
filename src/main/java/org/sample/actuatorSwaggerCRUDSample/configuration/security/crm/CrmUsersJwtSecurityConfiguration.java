package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;


import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableWebSecurity
@Order(2)
public class CrmUsersJwtSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final String AUTHENTICATION_SIGNATURE_KEY;
    private final String JWT_HEADER_KEY;
    private final CommonResponseDTO commonResponseDTO;
    private final CommonLogger LOGGER;
    private final  IMultiLanguageComponent multiLanguageComponent;

    public CrmUsersJwtSecurityConfiguration(final BCryptPasswordEncoder bCryptPasswordEncoder,
                                            final @Qualifier("crmUsersJwtUserDetailsService") UserDetailsService userDetailsService,
                                            final @Qualifier("crmUsersJwtSecurityAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                                            final @Value("${local.crm.user.security.authenticated.jwt.signature.key}") String AUTHENTICATION_SIGNATURE_KEY,
                                            final @Value("${local.crm.user.security.jwt.header.key}") String JWT_HEADER_KEY,
                                            final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO,
                                            final @Qualifier("trace-logger") CommonLogger LOGGER,
                                            final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint=authenticationEntryPoint;
        this.AUTHENTICATION_SIGNATURE_KEY=AUTHENTICATION_SIGNATURE_KEY;
        this.JWT_HEADER_KEY=JWT_HEADER_KEY;
        this.commonResponseDTO=commonResponseDTO;
        this.LOGGER=LOGGER;
        this.multiLanguageComponent=multiLanguageComponent;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        http.antMatcher("/customers/**")
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilter(basicAuthenticationFilter())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean("crmUsersJwtSecurityAuthenticationManager")
    public AuthenticationManager crmUsersJwtSecurityAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    private BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
        return new CrmUserJwtBasicAuthenticationFilter
                (
                        authenticationManager(),
                        AUTHENTICATION_SIGNATURE_KEY,
                        JWT_HEADER_KEY,
                        commonResponseDTO,
                        LOGGER,
                        multiLanguageComponent
                );
    }
}
