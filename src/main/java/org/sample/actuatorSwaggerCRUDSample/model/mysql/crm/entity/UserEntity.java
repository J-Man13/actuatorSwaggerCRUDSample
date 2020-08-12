package org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
