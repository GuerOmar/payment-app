package com.payment.service.user;

import com.payment.model.User;

public interface UserService {

    User createUser(String username, String password);

    User findUserById(Long id);

    User findUserByUsername(String username);
}
