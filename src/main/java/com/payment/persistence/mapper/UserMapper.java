package com.payment.persistence.mapper;

import com.payment.model.User;
import com.payment.persistence.entity.UserJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserJpa toEntity(User user);

    User toModel(UserJpa userJpa);
}
