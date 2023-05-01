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
public class FindUserByNameUseCase {
    @Autowired
    private UserService service;
    public User findOneUser(String name) {
        log.info("Searching user with name: " + name);
        User user = service.findOneUserByUsername(name);
        if(Objects.isNull(user.getId())){
            throw new NoSuchElementException("The user is not registered");
        }
        return user;
    }
}
