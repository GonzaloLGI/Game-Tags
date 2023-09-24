package com.gametags.gametags.application.user;

import java.util.List;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindAllUsersUseCase {

  @Autowired
  private UserService service;

  public List<User> findAllUsers() {
    log.info("[START] Searching all users");
    log.info("[STOP] Searching all users");
    return service.findAllUsers();
  }
}
