package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.UpdateUserUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
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

  private Comment comment1;
  private Comment comment2;

  @BeforeEach
  void createComments(){
    comment1 = Comment.builder()
        .id(UUID.randomUUID())
        .text("text1")
        .category("category1")
        .severity("severity1")
        .uploadDate(LocalDateTime.now())
        .build();

    comment2 = Comment.builder()
        .id(UUID.randomUUID())
        .text("text2")
        .category("category2")
        .severity("severity2")
        .uploadDate(LocalDateTime.now())
        .build();
  }

  @Test
  public void updateUser() {
    //GIVEN
    User oldUser = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .build();
    User newUser = User.builder()
        .id(UUID.randomUUID())
        .username("newUsername")
        .email("newEmail")
        .password("newPassword")
        .country("newCountry")
        .build();
    when(service.updateUser(newUser)).thenReturn(newUser);
    when(service.findOneUserById(any())).thenReturn(oldUser);

    //WHEN
    User returnedUser = useCase.updateUser(newUser);

    //THEN
    assertNotEquals(oldUser, returnedUser);
    assertEquals(newUser, returnedUser);

  }
}
