package com.gametags.gametags.application.user;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindUserByIdUseCase {

  @Autowired
  private UserService service;

  public User findOneUser(UUID id) {
    System.out.println("[START] Searching user with id: " + id);
    //    log.debug("[START] Searching user with id: " + id);
    User user = service.findOneUserById(id);
    if (Objects.isNull(user.getId())) {
      throw new NoSuchElementException("The user is not registered");
    }
    System.out.println("[STOP] findOneUser");
    //    log.debug("[STOP] findOneUser");
    return user;
  }
}
