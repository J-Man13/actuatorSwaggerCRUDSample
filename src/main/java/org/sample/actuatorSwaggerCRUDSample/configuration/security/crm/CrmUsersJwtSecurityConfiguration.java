package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;


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

@EnableWebSecurity
@Order(2)
public class CrmUsersJwtSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final String AUTHENTICATION_SIGNATURE_KEY;
    private final String JWT_HEADER_KEY;


    public CrmUsersJwtSecurityConfiguration(final BCryptPasswordEncoder bCryptPasswordEncoder,
                                            final @Qualifier("crmUsersJwtUserDetailsService") UserDetailsService userDetailsService,
                                            final @Value("${local.crm.user.security.authenticated.jwt.signature.key}") String AUTHENTICATION_SIGNATURE_KEY,
                                            final @Value("${local.crm.user.security.jwt.token.activity.period.ms}") Long TOKEN_ACTIVITY_PERIOD_MS,
                                            final @Value("${local.crm.user.security.jwt.header.key}") String JWT_HEADER_KEY){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.AUTHENTICATION_SIGNATURE_KEY=AUTHENTICATION_SIGNATURE_KEY;

        this.JWT_HEADER_KEY=JWT_HEADER_KEY;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        http.antMatcher("/kek/**")
                .authorizeRequests()
                    .anyRequest().permitAll()
                .and()
                .addFilter(getCrmUserJwtBasicAuthenticationFilter())
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

    private CrmUserJwtBasicAuthenticationFilter getCrmUserJwtBasicAuthenticationFilter() throws Exception {
        return new CrmUserJwtBasicAuthenticationFilter
                (
                        authenticationManager(),
                        AUTHENTICATION_SIGNATURE_KEY,
                        JWT_HEADER_KEY
                );
    }
}
