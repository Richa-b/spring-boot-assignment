package com.mytaxi.service.security;

import com.mytaxi.domainobject.UserDO;
import com.mytaxi.service.BaseService;

public interface UserService extends BaseService<UserDO,Long> {
    UserDO findByUserName(String userName);
}
