package com.gametags.gametags.comment;

import java.util.ArrayList;
import java.util.List;

import com.gametags.gametags.application.comment.filter_comments.FilterByCategoryUseCase;
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
public class FilterByCategoryAndVideogameUseCaseTest {

  @Mock
  CommentService service;

  @InjectMocks
  FilterByCategoryUseCase filterByCategoryUseCase;

  @Test
  public void filterByCategoryAndGame() {
    //GIVEN
    String category = "category";
    String videoGameName = "name";
    List<Comment> comments = new ArrayList<>(List.of(Comment.builder().build()));
    when(service.findAllCommentsByCategoryAndVideoGame(any(),any())).thenReturn(comments);
    //WHEN
    List<Comment> returned = filterByCategoryUseCase.commentsByCategoryAndVideoGame(category,videoGameName);
    //THEN
    assertTrue(returned.size() > 0);
  }

}
