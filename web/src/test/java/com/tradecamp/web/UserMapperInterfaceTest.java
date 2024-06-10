package com.tradecamp.web;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@ActiveProfiles("local")
class UserMapperInterfaceTest {
//    @Autowired
//    UserMapper userMapperInterface;
//
//    UserDtoCreate userDtoCreate;
//    User user;
//
//    @BeforeEach
//    void init() {
//        userDtoCreate = UserDtoCreate.builder().name("testName").password("testPassword").build();
//        user = User.builder()
//                .name("testName")
//                .password("testPassword")
//                .build();
//    }
//
//    @Test
//    void toUser() {
//
//        User result = userMapperInterface.toUser(userDtoCreate);
//        assertEquals(userDtoCreate.getName(), result.getName());
//        assertEquals(userDtoCreate.getPassword(), result.getPassword());
//    }
//
//    @Test
//    void toDto() {
//        UserDto result = userMapperInterface.toDto(user);
//        assertEquals(user.getName(), result.getName());
//        assertEquals(user.getPassword(), result.getPassword());
//    }
}
