package org.sample.actuatorSwaggerCRUDSample.configuration.security.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@EnableWebSecurity
@Order(1)
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ActuatorAuthenticationEntryPoint actuatorAuthenticationEntryPoint;
    private final String ACTUATOR_USER;
    private final String ACTUATOR_USER_PASSWORD;
    private final String SECURITY_BASE_PATH_PATTERN;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ActuatorSecurityConfiguration(final ActuatorAuthenticationEntryPoint actuatorAuthenticationEntryPoint,
                                         final @Value("${local.actuator.user}") String ACTUATOR_USER,
                                         final @Value("${local.actuator.password}") String ACTUATOR_USER_PASSWORD,
                                         final @Value("${local.actuator.security.base.path.pattern}") String SECURITY_BASE_PATH_PATTERN,
                                         final BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.actuatorAuthenticationEntryPoint = actuatorAuthenticationEntryPoint;
        this.ACTUATOR_USER = ACTUATOR_USER;
        this.ACTUATOR_USER_PASSWORD = ACTUATOR_USER_PASSWORD;
        this.SECURITY_BASE_PATH_PATTERN = SECURITY_BASE_PATH_PATTERN;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.cors().disable();

        http.antMatcher(SECURITY_BASE_PATH_PATTERN)
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(actuatorAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        //To authorize yourself while making calls to actuator
        //Add this to your headers
        //Authorization: Basic base64(user:password)
        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder).withUser(ACTUATOR_USER).password(bCryptPasswordEncoder.encode(ACTUATOR_USER_PASSWORD)).roles("ACTUATOR");
    }
}
