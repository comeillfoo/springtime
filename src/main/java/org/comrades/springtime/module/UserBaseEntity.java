package org.comrades.springtime.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class UserBaseEntity {

    public UserBaseEntity() {}

    public UserBaseEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
}
