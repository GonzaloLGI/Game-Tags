package com.gametags.gametags.application.user;

import java.util.NoSuchElementException;
import java.util.Objects;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateUserUseCase {

  @Autowired
  private UserService service;

  public User updateUser(User user) {
    log.info("[START] updateUser");
    User foundUser = service.findOneUserById(user.getId());
    if (Objects.isNull(foundUser.getId())) {
      throw new NoSuchElementException("The user is not registered");
    }
    log.info("[STOP] updateUser");
    return service.updateUser(user);
  }
}
