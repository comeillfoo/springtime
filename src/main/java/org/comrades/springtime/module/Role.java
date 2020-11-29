package org.comrades.springtime.module;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "user_role")
    private RoleEnum role;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<User> userList;

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
