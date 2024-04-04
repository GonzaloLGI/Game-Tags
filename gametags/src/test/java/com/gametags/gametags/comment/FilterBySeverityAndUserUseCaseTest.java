package com.gametags.gametags.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.comment.filter_comments.FilterBySeverityUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterBySeverityAndUserUseCaseTest {

  @Mock
  CommentService service;

  @Mock
  UserService userService;

  @InjectMocks
  FilterBySeverityUseCase filterBySeverityUseCase;

  @Mock
  Authentication authentication;

  @BeforeEach
  void initSecurityContextHolder(){
    when(authentication.getName()).thenReturn("username");
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  public void filterBySeverityAndUser() {
    //GIVEN
    String severity = "severity";
    User user = User.builder()
            .id(UUID.randomUUID())
            .username("username2").build();
    List<Comment> comments = new ArrayList<>(List.of(Comment.builder().build(), Comment.builder().build()));

    when(userService.findOneUserByUsername(any())).thenReturn(user);
    when(service.findAllCommentsBySeverityAndUploadUser(any(),any())).thenReturn(comments);

    //WHEN
    List<Comment> returned = filterBySeverityUseCase.commentsBySeverityAndUser(severity);

    //THEN
    assertTrue(returned.size() > 0);
  }

  @Test
  public void cantFilterBecauseUserDoesntExist() {
    //GIVEN
    String severity = "severity";
    when(userService.findOneUserByUsername(any())).thenReturn(User.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> filterBySeverityUseCase.commentsBySeverityAndUser(severity));

    //THEN
    assertEquals("The user doesn't exist",exception.getMessage());
  }

}
