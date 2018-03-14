package com.mytaxi.domainobject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Table(name = "role")
@Entity
public class RoleDO extends BaseDO implements GrantedAuthority {

    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<UserDO> users;

    @Override
    public String getAuthority() {
        return authority;
    }
}