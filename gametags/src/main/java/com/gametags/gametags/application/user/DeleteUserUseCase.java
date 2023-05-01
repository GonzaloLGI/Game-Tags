package com.gametags.gametags.application.user;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class DeleteUserUseCase {
    @Autowired
    private UserService service;
    public User deleteUser(UUID id) {
        log.info("Deleting user with id: " + id);
        User user = service.findOneUserById(id);
        if(!Objects.isNull(user.getId())){
            return service.deleteUser(id);
        }else{
            throw new NoSuchElementException("Non existing user");
        }

    }
}
