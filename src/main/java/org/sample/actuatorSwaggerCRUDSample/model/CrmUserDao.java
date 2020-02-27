package org.sample.actuatorSwaggerCRUDSample.model;

import java.time.LocalDateTime;

public class CrmUserDao {
    private String id;
    private String name;
    private String surname;
    private LocalDateTime dtstamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(LocalDateTime dtstamp) {
        this.dtstamp = dtstamp;
    }

    @Override
    public String toString() {
        return "CrmUserDao{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dtstamp=" + dtstamp +
                '}';
    }
}
