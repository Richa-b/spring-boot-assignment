package com.mytaxi.domainobject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class UserDO extends BaseDO implements UserDetails {

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username can not be null!")
    protected String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    protected String password;

    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired = false;

    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked = false;

    @Column(columnDefinition = "boolean default false")
    private boolean credentialsExpired = false;

    @Column(columnDefinition = "boolean default true")
    private boolean accountEnabled = true;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<RoleDO> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

    public UserDO(String userName, Set<RoleDO> authorityList) {
        this.username = userName;
        this.roles = authorityList;
    }

    public UserDO() {
    }

}
