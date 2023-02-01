package com.cognizant.FEMS;
import com.cognizant.FEMS.model.User;
import com.cognizant.FEMS.proxy.UserProxy;
import com.cognizant.FEMS.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserProxy userProxy;

    @InjectMocks
    private UserServiceImpl userService;

    private static User user;

    @BeforeAll
    public static void init() {
        user = new User(
                1,
                "username",
                "profile_img.jpg"
        );
    }

    @Test
    public void getUserById_positiveTest() {
        when(userProxy.getUserById(1))
                .thenReturn(user);
        User actual = userService.getUser(1);
        Assertions.assertThat(actual).isEqualTo(user);
    }

    @Test
    public void getInvalidUser_positiveTest() {
        User actual = userService.getUser(0);
        Assertions.assertThat(actual).isNull();
    }
}
