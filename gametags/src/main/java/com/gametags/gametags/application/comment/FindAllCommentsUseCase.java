package com.gametags.gametags.application.comment;

import java.util.List;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindAllCommentsUseCase {

  @Autowired
  private CommentService service;

  public List<Comment> findAllComments() {
    System.out.println("[START] Searching all comments");
    //    log.debug("[START] Searching all users");
    return service.findAllComments();
  }
}
