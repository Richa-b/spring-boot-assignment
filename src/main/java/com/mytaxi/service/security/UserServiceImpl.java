package com.mytaxi.service.security;

import com.mytaxi.dataaccessobject.UserRepository;
import com.mytaxi.domainobject.UserDO;
import com.mytaxi.service.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserDO, Long> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected JpaRepository<UserDO, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDO findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}
