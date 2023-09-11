package com.gametags.gametags.application.comment.filter_comments;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterBySeverityUseCase {

  @Autowired
  private CommentService service;

  public List<Comment> commentsBySeverityAndUser(String severity) {
    //AQUI DEBERIA HACERSE TODA LA LOGICA DEL USUARIO
    System.out.println("[START] filterBySeverity");
    log.debug("[START] filterBySeverity");
    System.out.println("[STOP] filterBySeverity");
    log.debug("[STOP] filterBySeverity");
    return service.findAllCommentsBySeverity(severity);
  }

  public List<Comment> commentsBySeverityAndVideoGame(String severity, UUID videogame) {
    System.out.println("[START] filterBySeverityAndVideoGame");
    log.debug("[START] filterBySeverityAndVideoGame");
    System.out.println("[STOP] filterBySeverityAndVideoGame");
    log.debug("[STOP] filterBySeverityAndVideoGame");
    return service.findAllCommentsBySeverityAndVideoGame(severity, videogame);
  }
}
