package com.gametags.gametags.application.comment;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteCommentUseCase {

  @Autowired
  private CommentService service;

  public Comment deleteComment(UUID id) {
    System.out.println("[START] Deleting comment with id: " + id);
    //    log.debug("[START] Deleting user with id: " + id);
    Comment comment = service.findOneCommentById(id);
    if (!Objects.isNull(comment.getId())) {
      System.out.println("[STOP] Deleting comment with id: " + id);
      //      log.debug("[STOP] Deleting user with id: " + id);
      return service.deleteComment(id);
    } else {
      throw new NoSuchElementException("Non existing comment");
    }
  }
}
