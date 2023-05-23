package com.gametags.gametags.application.user.create_user;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class CreateUserUseCase {
    @Autowired
    private UserService service;
    public User createUser(User user) {
        log.debug("[START] Creating classification with data: " + user.getId() + user.getUsername() + user.getEmail());
        User previous = service.findOneUserByUsername(user.getUsername());
        if(Objects.isNull(previous)){
            return service.createUser(user);
        }
        log.debug("[STOP] createUser");
        return previous;
    }
}
