package com.expensemanager.model;

import org.junit.jupiter.api.*;

public class UserTest {
    @Test
    void testUserCreation(){
        User user = new User("Alice", "secure123");

        assert (user.getName().equals("Alice"));
        assert (user.getPsw().equals("secure123"));
    }

    @Test
    void testSetPassword(){
        User user = new User("Bob", "oldPass");
        user.setPsw("newPass");
        assert ("newPass".equals(user.getPsw()));
    }
}
