package com.mytaxi.unit.service;

import com.mytaxi.domainobject.RoleDO;
import com.mytaxi.domainobject.UserDO;
import com.mytaxi.service.security.CustomUserDetailsService;
import com.mytaxi.service.security.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CustomUserDetailServiceTest {

    private UserService userService;
    private CustomUserDetailsService userDetailsService;

    @Before
    public void setup() {
        userService = Mockito.mock(UserService.class);
        userDetailsService = new CustomUserDetailsService(userService);
    }

    @Test
    public void loadUserByUserNameTest(){

        Set<RoleDO> authorities = new HashSet<>();
        authorities.add(new RoleDO("ROLE_ADMIN"));
        UserDO userDO = new UserDO("Test1", authorities);
        Mockito.when(userService.findByUserName(anyString())).thenReturn(userDO);
        UserDO returnedUser = userDetailsService.loadUserByUsername("Test1");
        assertEquals(returnedUser.getUsername(), userDO.getUsername());
        // verify that repository method was called once
        verify(userService).findByUserName(anyString());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUserNameTestWhenUserNotFound(){

        Mockito.when(userService.findByUserName(anyString())).thenReturn(null);
        userDetailsService.loadUserByUsername("Test1");
        // verify that repository method was called once
        verify(userService).findByUserName(anyString());
    }
}
