package com.payment.persistence;

import com.payment.model.User;
import com.payment.persistence.entity.UserJpa;
import com.payment.persistence.mapper.UserMapper;
import com.payment.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        UserJpa userJpa =  userRepository.findById(id).orElseThrow(RuntimeException::new);
        return userMapper.toModel(userJpa);
    }

    public User save(User user) {
        UserJpa result = userRepository.save(userMapper.toEntity(user));
        return userMapper.toModel(result);
    }

    public User findByUsername(String username) {
        UserJpa userJpa = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        return userMapper.toModel(userJpa);
    }

}
