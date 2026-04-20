package com.payment.persistence;

import com.payment.model.User;
import com.payment.persistence.entity.UserJpa;
import com.payment.persistence.mapper.UserMapper;
import com.payment.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserAdapter userAdapter;

    private UserJpa userJpa;
    private User user;

    @BeforeEach
    void setUp() {
        userJpa = UserJpa.builder()
                .id(1L)
                .username("Alice")
                .password("hashed_password")
                .build();

        user = User.builder()
                .id(1L)
                .username("Alice")
                .password("hashed_password")
                .build();
    }

    @Test
    void shouldReturnUser_whenFindById_givenExistingId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userJpa));
        when(userMapper.toModel(userJpa)).thenReturn(user);

        User result = userAdapter.findById(1L);

        assertEquals(user, result);
        verify(userRepository).findById(1L);
        verify(userMapper).toModel(userJpa);
    }

    @Test
    void shouldThrowException_whenFindById_givenNonExistingId() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userAdapter.findById(99L));

        verify(userRepository).findById(99L);
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldReturnSavedUser_whenSave_givenValidUser() {
        when(userMapper.toEntity(user)).thenReturn(userJpa);
        when(userRepository.save(userJpa)).thenReturn(userJpa);
        when(userMapper.toModel(userJpa)).thenReturn(user);

        User result = userAdapter.save(user);

        assertEquals(user, result);
        verify(userMapper).toEntity(user);
        verify(userRepository).save(userJpa);
        verify(userMapper).toModel(userJpa);
    }

    @Test
    void shouldReturnUser_whenFindByUsername_givenExistingUsername() {
        when(userRepository.findByUsername("Alice")).thenReturn(Optional.ofNullable(userJpa));
        when(userMapper.toModel(userJpa)).thenReturn(user);

        User result = userAdapter.findByUsername("Alice");

        assertEquals(user, result);
        verify(userRepository).findByUsername("Alice");
        verify(userMapper).toModel(userJpa);
    }
}