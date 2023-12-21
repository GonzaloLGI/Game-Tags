package com.gametags.gametags.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.comment.filter_comments.FilterByCategoryUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterByCategoryAndUserUseCaseTest {

  @Mock
  CommentService service;

  @Mock
  UserService userService;

  @InjectMocks
  FilterByCategoryUseCase filterByCategoryUseCase;

  @Mock
  Authentication authentication;

  @BeforeEach
  void initSecurityContextHolder(){
    when(authentication.getName()).thenReturn("username");
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  public void filterByCategoryAndUser() {
    //GIVEN
    String category = "category";
    User user = User.builder()
            .id(UUID.randomUUID())
            .username("username2").build();
    List<Comment> comments = new ArrayList<>(List.of(Comment.builder().build(), Comment.builder().build()));

    when(userService.findOneUserByUsername(any())).thenReturn(user);
    when(service.findAllCommentsByCategoryAndUploadUser(any(),any())).thenReturn(comments);
    //WHEN
    List<Comment> returned = filterByCategoryUseCase.commentsByCategoryAndUser(category);
    //THEN
    assertTrue(returned.size() > 0);
  }

}
