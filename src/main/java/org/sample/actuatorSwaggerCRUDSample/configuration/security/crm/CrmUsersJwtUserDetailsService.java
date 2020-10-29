package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmUsersJwtUserDetailsService implements UserDetailsService {

    private ICrmUserService crmUserService;

    public CrmUsersJwtUserDetailsService(final @Qualifier("crmUserService") ICrmUserService crmUserService){
        this.crmUserService=crmUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CrmUser crmUser = crmUserService.findUserByLogin(username);
        List<GrantedAuthority> grantedAuthorities = crmUser.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new User(crmUser.getLogin(),crmUser.getCryptedPassword(), grantedAuthorities);
    }
}
