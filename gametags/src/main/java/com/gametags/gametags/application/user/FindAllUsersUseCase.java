package com.gametags.gametags.application.user;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FindAllUsersUseCase {
    @Autowired
    private UserService service;
    public List<User> findAllUsers() {
        log.info("Searching all users");
        return service.findAllUsers();
    }
}
