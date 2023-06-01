package com.gametags.gametags.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.comment.FindAllCommentsUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindAllCommentsUseCaseTest {

  @InjectMocks
  FindAllCommentsUseCase useCase;

  @Mock
  private CommentService service;

  @Test
  public void findAll() {
    //GIVEN
    Comment comment1 = Comment.builder()
        .id(UUID.randomUUID())
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();
    Comment comment2 = Comment.builder()
        .id(UUID.randomUUID())
        .text("text2")
        .category("category2")
        .severity("severity2")
        .uploadDate(LocalDateTime.now())
        .build();
    List<Comment> list = List.of(comment1, comment2);
    when(useCase.findAllComments()).thenReturn(list);

    //WHEN
    List<Comment> result = useCase.findAllComments();

    //THEN
    assertEquals(list.size(), result.size());
    assertEquals(list.get(0).getId(), result.get(0).getId());
  }
}
