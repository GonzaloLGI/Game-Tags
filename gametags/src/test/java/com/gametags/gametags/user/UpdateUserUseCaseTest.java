package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.UpdateUserUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {

  @InjectMocks
  UpdateUserUseCase useCase;

  @Mock
  UserService service;

  @Test
  public void updateUser() {
    //GIVEN
    User oldUser = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .comments(List.of("comment1", "comment2"))
        .build();
    User newUser = User.builder()
        .id(UUID.randomUUID())
        .username("newUsername")
        .email("newEmail")
        .password("newPassword")
        .country("newCountry")
        .comments(List.of("comment1", "comment2"))
        .build();
    when(service.updateUser(newUser)).thenReturn(newUser);
    when(service.findOneUserByUsername(any(String.class))).thenReturn(oldUser);

    //WHEN
    User returnedUser = useCase.updateUser(newUser);

    //THEN
    assertNotEquals(oldUser, returnedUser);
    assertEquals(newUser, returnedUser);

  }
}
