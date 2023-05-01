package com.gametags.gametags.application.user;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@Slf4j
public class UpdateUserUseCase {
    @Autowired
    private UserService service;
    public User updateUser(User user) {
        User foundUser = service.findOneUserByUsername(user.getUsername());
        if(Objects.isNull(foundUser.getId())){
            throw new NoSuchElementException("The user is not registered");
        }
        return service.updateUser(user);
    }
}
