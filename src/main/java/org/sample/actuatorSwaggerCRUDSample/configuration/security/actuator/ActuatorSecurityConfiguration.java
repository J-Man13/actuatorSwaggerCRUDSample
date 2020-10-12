package org.sample.actuatorSwaggerCRUDSample.configuration.security.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1)
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ActuatorAuthenticationEntryPoint actuatorAuthenticationEntryPoint;
    private final String actuatorUser;
    private final String actuatorUserPassword;

    public ActuatorSecurityConfiguration(@Autowired ActuatorAuthenticationEntryPoint actuatorAuthenticationEntryPoint,
                                         @Value("${local.actuator.user}") String actuatorUser,
                                         @Value("${local.actuator.password}") String actuatorUserPassword){
        this.actuatorAuthenticationEntryPoint = actuatorAuthenticationEntryPoint;
        this.actuatorUser = actuatorUser;
        this.actuatorUserPassword = actuatorUserPassword;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/actuator/**").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(actuatorAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //To authorize yourself while making calls to actuator
        //Add this to your headers
        //Authorization: Basic base64(user:password)
        auth.inMemoryAuthentication().withUser(actuatorUser).password(String.format("{noop}%s",actuatorUserPassword)).roles("ACTUATOR");
    }
}
