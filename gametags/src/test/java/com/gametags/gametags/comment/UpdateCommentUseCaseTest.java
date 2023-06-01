package com.gametags.gametags.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gametags.gametags.application.comment.UpdateCommentUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateCommentUseCaseTest {

  @InjectMocks
  UpdateCommentUseCase useCase;

  @Mock
  CommentService service;

  @Test
  public void updateComment() {
    //GIVEN
    Comment oldComment = Comment.builder()
        .id(UUID.randomUUID())
        .text("oldText")
        .category("oldCategory")
        .severity("oldSeverity")
        .uploadDate(LocalDateTime.now())
        .build();
    Comment newComment = Comment.builder()
        .id(UUID.randomUUID())
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();
    when(service.updateComment(newComment)).thenReturn(newComment);
    when(service.findOneCommentById(any(UUID.class))).thenReturn(oldComment);

    //WHEN
    Comment returnedComment = useCase.updateComment(newComment);

    //THEN
    assertNotEquals(oldComment, returnedComment);
    assertEquals(newComment, returnedComment);

  }
}
