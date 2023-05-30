package com.gametags.gametags.application.comment.create_comment;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateCommentUseCase {

  @Autowired
  private CommentService service;

  public Comment createComment(Comment comment) {
    System.out.println(
        "[START] Creating comment with data: " + comment.getId() + " " + comment.getCategory() + " " + comment.getSeverity());
    //    log.debug("[START] Creating classification with data: " + user.getId() + user.getUsername() + user.getEmail());

    return service.createComment(comment);
  }

}
