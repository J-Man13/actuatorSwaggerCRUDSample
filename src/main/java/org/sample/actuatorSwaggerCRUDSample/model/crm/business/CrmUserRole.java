package org.sample.actuatorSwaggerCRUDSample.model.crm.business;

import java.time.LocalDateTime;

public class CrmUserRole {
    private Long id;
    private String roleName;
    private String roleDescription;
    private LocalDateTime dtstamp;

    public CrmUserRole(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public LocalDateTime getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(LocalDateTime dtstamp) {
        this.dtstamp = dtstamp;
    }
}
