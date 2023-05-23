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
public class DeleteUserUseCase {

  @Autowired
  private UserService service;

  public User deleteUser(UUID id) {
    log.debug("[START] Deleting user with id: " + id);
    User user = service.findOneUserById(id);
    if (!Objects.isNull(user)) {
      log.debug("[STOP] Deleting user with id: " + id);
      return service.deleteUser(id);
    } else {
      throw new NoSuchElementException("Non existing user");
    }

  }
}
