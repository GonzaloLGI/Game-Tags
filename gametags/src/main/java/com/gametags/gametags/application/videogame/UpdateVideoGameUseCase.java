package com.gametags.gametags.application.videogame;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateVideoGameUseCase {

  @Autowired
  private VideoGameService service;

  @Autowired
  private CommentService commentService;

  public VideoGame updateVideoGame(VideoGame videogame) {
    log.info("[START] updateVideoGame");
    VideoGame toUpdate = service.findOneVideoGame(videogame.getId());
    if (Objects.isNull(toUpdate.getId())) {
      log.info("[STOP] updateVideoGame");
      throw new NoSuchElementException("The videogame is not registered");
    }
    List<Comment> updatedComments =  videogame.getComments().stream().map(comment -> addNewComment(comment,videogame.getName())).toList();
    videogame.getComments().removeAll(videogame.getComments());
    videogame.getComments().addAll(updatedComments);
    log.info("[STOP] updateVideoGame");
    return service.updateVideoGame(videogame);
  }

  private Comment addNewComment(Comment comment, String videogame){
    log.info("COMMENT: " + comment.getText());
    Comment newComment = Comment.builder()
        .id(UUID.randomUUID())
        .videogame(videogame)
        .uploadUser(comment.getUploadUser())
        .category(comment.getCategory())
        .severity(comment.getSeverity())
        .text(comment.getText())
        .uploadDate(comment.getUploadDate())
        .build();
    return commentService.createComment(newComment);
  }
}
