package org.comrades.springtime.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class UserBaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
}
