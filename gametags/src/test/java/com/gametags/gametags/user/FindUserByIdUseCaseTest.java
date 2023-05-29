package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.user.FindUserByIdUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdUseCaseTest {

  @InjectMocks
  FindUserByIdUseCase useCase;

  @Mock
  UserService service;

  @Test
  public void findById() {
    //GIVEN
    User user = User.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .comments(List.of("comment1", "comment2"))
        .build();
    when(service.findOneUserById(any())).thenReturn(user);

    //WHEN
    User result = useCase.findOneUser(UUID.randomUUID());

    //THEN
    assertEquals(result, user);
  }

  @Test
  public void cantFindUserBecauseDoesntExist() {
    //GIVEN
    when(service.findOneUserById(any())).thenReturn(User.builder().build());

    //WHEN
    assertThrows(NoSuchElementException.class, () -> useCase.findOneUser(UUID.randomUUID()));
  }
}
