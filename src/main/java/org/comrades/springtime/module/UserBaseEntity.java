package org.comrades.springtime.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class UserBaseEntity {

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
}
