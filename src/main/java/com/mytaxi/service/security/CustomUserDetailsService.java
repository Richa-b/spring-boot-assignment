package com.mytaxi.service.security;

import com.mytaxi.domainobject.UserDO;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@CommonsLog
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional
    public UserDO loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-> loadUserByUsername(" + username + ")");
        UserDO domainUser = userService.findByUserName(username);
        if (Objects.isNull(domainUser)) {
            log.error("User not found = " + username);
            throw new UsernameNotFoundException("User not found = " + username);
        }
        Hibernate.initialize(domainUser.getRoles());
        log.info("<- User Successfully Found for userName=" + username);
        return domainUser;
    }
}
