package com.gametags.gametags.application.comment;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindCommentByIdUseCase {

  @Autowired
  private CommentService service;

  public Comment findOneComment(UUID id) {
    log.info("[START] Searching user with id: " + id);
    Comment comment = service.findOneCommentById(id);
    if (Objects.isNull(comment.getId())) {
      throw new NoSuchElementException("The comment is not registered");
    }
    log.info("[STOP] findOneUser");
    return comment;
  }
}
