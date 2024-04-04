package com.gametags.gametags.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.comment.FindCommentByIdUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindCommentByIdUseCaseTest {

  @InjectMocks
  FindCommentByIdUseCase useCase;

  @Mock
  CommentService service;

  @Test
  public void findById() {
    //GIVEN
    Comment comment = Comment.builder()
        .id(UUID.randomUUID())
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();
    when(service.findOneCommentById(any())).thenReturn(comment);

    //WHEN
    Comment result = useCase.findOneComment(UUID.randomUUID());

    //THEN
    assertEquals(result, comment);

  }

  @Test
  public void cantFindCommentBecauseDoesntExist() {
    //GIVEN
    when(service.findOneCommentById(any())).thenReturn(Comment.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.findOneComment(UUID.randomUUID()));

    //THEN
    assertEquals("The comment is not registered",exception.getMessage());
  }
}
