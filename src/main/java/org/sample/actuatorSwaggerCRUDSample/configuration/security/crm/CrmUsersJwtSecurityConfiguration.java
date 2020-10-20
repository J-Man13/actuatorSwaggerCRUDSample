package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Order(2)
public class CrmUsersJwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final  CrmUsersJwtUserDetailsService crmUsersJwtUserDetailsService;
    private final String LOGIN_ENDPOINT;
    private final String AUTHENTICATION_SIGNATURE_KEY;

    public CrmUsersJwtSecurityConfiguration(final BCryptPasswordEncoder bCryptPasswordEncoder,
                                            final CrmUsersJwtUserDetailsService crmUsersJwtUserDetailsService,
                                            final @Value("${local.crm.user.security.login.endpoint}")String LOGIN_ENDPOINT,
                                            final @Value("${local.crm.user.security.authenticated.jwt.signature.key}") String AUTHENTICATION_SIGNATURE_KEY){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.crmUsersJwtUserDetailsService = crmUsersJwtUserDetailsService;

        this.AUTHENTICATION_SIGNATURE_KEY=AUTHENTICATION_SIGNATURE_KEY;
        this.LOGIN_ENDPOINT = LOGIN_ENDPOINT;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        http.antMatcher(LOGIN_ENDPOINT)
                .authorizeRequests()
                    .anyRequest().permitAll()
                .and()
                .addFilter(getCrmUserJwtUsernamePasswordAuthenticationFilter())
                .addFilter(getCrmUserJwtBasicAuthenticationFilter())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(crmUsersJwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    private CrmUserJwtUsernamePasswordAuthenticationFilter getCrmUserJwtUsernamePasswordAuthenticationFilter() throws Exception {
        return new CrmUserJwtUsernamePasswordAuthenticationFilter(authenticationManager(),LOGIN_ENDPOINT,AUTHENTICATION_SIGNATURE_KEY);
    }

    private CrmUserJwtBasicAuthenticationFilter getCrmUserJwtBasicAuthenticationFilter() throws Exception {
        return new CrmUserJwtBasicAuthenticationFilter(authenticationManager(),AUTHENTICATION_SIGNATURE_KEY);
    }
}
