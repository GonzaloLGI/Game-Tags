package com.gametags.gametags.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.comment.create_comment.CreateCommentUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCommentUseCaseTest {

  @InjectMocks
  CreateCommentUseCase useCase;

  @Mock
  CommentService service;

  @Mock
  VideoGameService videogameService;

  @Mock
  UserService userService;

  @Test
  public void createComment() {
    //GIVEN
    Comment comment = Comment.builder()
        .id(UUID.randomUUID())
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();
    when(service.createComment(any(Comment.class))).thenReturn(comment);
    when(userService.findOneUserByUsername(any())).thenReturn(User.builder().build());
    when(videogameService.findVideoGameByName(any())).thenReturn(VideoGame.builder().comments(new ArrayList<>(List.of())).build());

    //WHEN
    Comment returnedComment = useCase.createComment(comment);

    //THEN
    assertEquals(comment, returnedComment);

  }
}
