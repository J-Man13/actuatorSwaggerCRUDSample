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
    private final String AUTHENTICATED_ENDPOINTS_PATTERN;

    public CrmUsersJwtSecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder,
                                            CrmUsersJwtUserDetailsService crmUsersJwtUserDetailsService,
                                            @Value("${local.crm.user.security.login.endpoint}")String LOGIN_ENDPOINT,
                                            @Value("${local.crm.user.security.authenticated.endpoints.pattern}")String AUTHENTICATED_ENDPOINTS_PATTERN){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.crmUsersJwtUserDetailsService = crmUsersJwtUserDetailsService;

        this.LOGIN_ENDPOINT = LOGIN_ENDPOINT;
        this.AUTHENTICATED_ENDPOINTS_PATTERN=AUTHENTICATED_ENDPOINTS_PATTERN;
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
        return new CrmUserJwtUsernamePasswordAuthenticationFilter(authenticationManager(),LOGIN_ENDPOINT);
    }

    private CrmUserJwtBasicAuthenticationFilter getCrmUserJwtBasicAuthenticationFilter() throws Exception {
        return new CrmUserJwtBasicAuthenticationFilter(authenticationManager());
    }
}
