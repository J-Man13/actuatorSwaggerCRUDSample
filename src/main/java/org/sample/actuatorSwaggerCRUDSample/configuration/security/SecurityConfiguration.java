package org.sample.actuatorSwaggerCRUDSample.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final String actuatorUser;
    private final String actuatorUserPassword;

    public SecurityConfiguration(@Autowired AuthenticationEntryPoint authenticationEntryPoint,
                                 @Value("${local.actuator.user}") String actuatorUser,
                                 @Value("${local.actuator.password}") String actuatorUserPassword){
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.actuatorUser = actuatorUser;
        this.actuatorUserPassword = actuatorUserPassword;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //To authorize yourself while making calls to actuator
        //Add this to your headers
        //Authorization: Basic base64(user:password)
        auth.inMemoryAuthentication().withUser(actuatorUser).password(String.format("{noop}%s",actuatorUserPassword)).roles("ACTUATOR");
    }
}
