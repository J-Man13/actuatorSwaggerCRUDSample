package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class CrmUserJwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public CrmUserJwtBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

}
