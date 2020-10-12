package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class CrmUsersJwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CrmUsersJwtAuthenticationEntryPoint crmUsersJwtAuthenticationEntryPoint;
    private final  CrmUsersJwtUserDetailsService crmUsersJwtUserDetailsService;

    public CrmUsersJwtSecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder,
                                            CrmUsersJwtAuthenticationEntryPoint crmUsersJwtAuthenticationEntryPoint,
                                            CrmUsersJwtUserDetailsService crmUsersJwtUserDetailsService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.crmUsersJwtAuthenticationEntryPoint = crmUsersJwtAuthenticationEntryPoint;
        this.crmUsersJwtUserDetailsService = crmUsersJwtUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        http.authorizeRequests()
                .antMatchers("**/users/**").authenticated()
                .and()
                .addFilter(new CrmUserJwtUsernamePasswordAuthenticationFilter(authenticationManager()))
                .addFilter(new CrmUserJwtBasicAuthenticationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(crmUsersJwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
