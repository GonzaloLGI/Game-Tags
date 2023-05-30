package com.gametags.gametags.application.comment;

import java.util.NoSuchElementException;
import java.util.Objects;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateCommentUseCase {

  @Autowired
  private CommentService service;

  public Comment updateComment(Comment comment) {
    System.out.println("[START] updateComment");
    //    log.debug("[START] updateUser");
    Comment foundComment = service.findOneCommentById(comment.getId());
    if (Objects.isNull(foundComment.getId())) {
      throw new NoSuchElementException("The comment is not registered");
    }
    System.out.println("[STOP] updateUser");
    //    log.debug("[STOP] updateUser");
    return service.updateComment(comment);
  }
}
