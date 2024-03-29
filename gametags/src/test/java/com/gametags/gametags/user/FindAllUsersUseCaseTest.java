package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.FindAllUsersUseCase;
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
public class FindAllUsersUseCaseTest {

  @InjectMocks
  FindAllUsersUseCase useCase;

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
  public void findAll() {
    //GIVEN
    User user1 = User.builder()
        .id(UUID.randomUUID())
        .username("username1")
        .email("email1")
        .password("password1")
        .country("country1")
        .build();
    User user2 = User.builder()
        .id(UUID.randomUUID())
        .username("username2")
        .email("email2")
        .password("password2")
        .country("country2")
        .build();
    List<User> list = List.of(user1, user2);
    when(useCase.findAllUsers()).thenReturn(list);

    //WHEN
    List<User> result = useCase.findAllUsers();

    //THEN
    assertEquals(list.size(), result.size());
    assertEquals(list.get(0).getId(), result.get(0).getId());
  }
}
