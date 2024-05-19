package com.journal.newmongoproject.service;

import com.journal.newmongoproject.entity.User;
import com.journal.newmongoproject.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userService;

//    @BeforeEach
//    static void setUp(){
//      // create csv
//    }
//
//    @AfterAll
//    static void eraseAll(){
//        // delete after the tests.
//    }

//    @Disabled
//    @Test
//    public void testFindByUserName(){
//        // assertEquals(4, 2+2);
//        User user = userService.findByUserName("hassan");
//        assertFalse(user.getJournalEntries().isEmpty());
//    }
//
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "hassan",
//            "aoon",
//            "admin"
//    })
//    public void testUsers(String name){
//        assertNotNull(userService.findByUserName(name), "Failed for => " + name);
//    }
//
//
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "10,2,12",
//            "3,3,9"
//    })
//    public void testSources(int a, int b, int actual){
//        assertEquals(actual, a + b);
//    }
}
