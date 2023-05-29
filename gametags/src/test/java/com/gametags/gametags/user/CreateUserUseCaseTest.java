package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.create_user.CreateUserUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

  @InjectMocks
  CreateUserUseCase useCase;

  @Mock
  UserService service;

  @Test
  public void createUser() {
    //GIVEN
    User user = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .comments(List.of("comment1", "comment2"))
        .build();
    when(service.createUser(any(User.class))).thenReturn(user);

    //WHEN
    User returnedUser = useCase.createUser(user);

    //THEN
    assertEquals(user, returnedUser);
  }
}
