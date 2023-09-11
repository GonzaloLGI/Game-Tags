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
public class FilterByVideoGameUseCase {

  @Autowired
  private CommentService service;

  public List<Comment> commentsByVideoGameAndUser(UUID videogame) {
    //AQUI DEBERIA HACERSE TODA LA LOGICA DEL USUARIO
    System.out.println("[START] filterByVideoGame");
    log.debug("[START] filterByVideoGame");
    System.out.println("[STOP] filterByVideoGame");
    log.debug("[STOP] filterByVideoGame");
    return service.findAllCommentsByVideoGame(videogame);
  }
}
