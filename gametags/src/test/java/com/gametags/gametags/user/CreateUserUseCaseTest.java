package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.create_user.CreateUserUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
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
  public void createUser() {
    //GIVEN
    User user = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .build();
    when(service.createUser(any(User.class))).thenReturn(user);
    when(service.findOneUserByUsername(any(String.class))).thenReturn(User.builder().build());

    //WHEN
    User returnedUser = useCase.createUser(user);

    //THEN
    assertEquals(user, returnedUser);
  }

  @Test
  public void createAlreadyExisingUser() {
    //GIVEN
    User user = User.builder()
            .id(UUID.randomUUID())
            .username("username")
            .email("email")
            .password("password")
            .country("country")
            .build();
    when(service.findOneUserByUsername(any(String.class))).thenReturn(user);

    //WHEN
    User returnedUser = useCase.createUser(user);

    //THEN
    assertEquals(user, returnedUser);
  }

}
