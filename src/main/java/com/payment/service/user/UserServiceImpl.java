package com.payment.service.user;

import com.payment.model.User;
import com.payment.persistence.UserAdapter;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserAdapter userAdapter;

    public UserServiceImpl(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    @Override
    public User createUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        return userAdapter.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userAdapter.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userAdapter.findByUsername(username);
    }
}
