package com.gametags.gametags.comment;

import java.util.ArrayList;
import java.util.List;

import com.gametags.gametags.application.comment.FindAllCommentsUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllCommentsOfUserUseCaseTest {

  @Mock
  CommentService service;

  @InjectMocks
  FindAllCommentsUseCase findAllCommentsUseCase;

  @Test
  public void getAllCommentOfUser() {
    //GIVEN
    String userName = "username";
    List<Comment> comments = new ArrayList<>(List.of(Comment.builder().build()));
    when(service.findAllCommentsOfUser(any())).thenReturn(comments);
    //WHEN
    List<Comment> returned = findAllCommentsUseCase.findAllCommentsOfUser(userName);
    //THEN
    assertTrue(returned.size() > 0);
  }

}
