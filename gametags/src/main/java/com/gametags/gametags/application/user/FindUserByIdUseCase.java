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
    log.info("[START] Searching user with id: " + id);
    User user = service.findOneUserById(id);
    if (Objects.isNull(user.getId())) {
      throw new NoSuchElementException("The user is not registered");
    }
    log.info("[STOP] findOneUser");
    return user;
  }
}
