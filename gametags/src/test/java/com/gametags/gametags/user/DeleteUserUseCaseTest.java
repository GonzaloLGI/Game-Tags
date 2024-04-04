package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.user.DeleteUserUseCase;
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
public class DeleteUserUseCaseTest {

  @InjectMocks
  DeleteUserUseCase useCase;

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
  public void canDelete() {
    //GIVEN
    User user = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .build();
    when(service.findOneUserById(user.getId())).thenReturn(user);
    when(service.deleteUser(user.getId())).thenReturn(user);

    //WHEN
    User result = useCase.deleteUser(user.getId());

    //THEN
    assertEquals(user, result);
  }

  @Test
  public void cantDeleteBecauseUserDoesntExist() {
    //GIVEN
    when(service.findOneUserById(any())).thenReturn(User.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.deleteUser(UUID.randomUUID()));

    //THEN
    assertEquals("Non existing user",exception.getMessage());
  }
}
