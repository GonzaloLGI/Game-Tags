package com.gametags.gametags.domain.services;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.adapters.UserAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Builder
public class UserService {

  private UserAdapter adapter;

  public User updateUser(User user) {
    return adapter.update(user);
  }

  public List<User> findAllUsers() {
    return adapter.findAll();
  }

  public User findOneUserById(UUID id) {
    return adapter.findById(id);
  }

  public User deleteUser(UUID id) {
    return adapter.delete(id);
  }

  public User findOneUserByUsername(String username) {
    return adapter.findByUsername(username);
  }

  public User createUser(User user) {
    return adapter.create(user);
  }
}
