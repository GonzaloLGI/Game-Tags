package com.gametags.gametags.application.comment.filter_comments;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterByCategoryUseCase {

  @Autowired
  private CommentService service;

  public List<Comment> commentsByCategoryAndUser(String category) {
    //AQUI DEBERIA HACERSE TODA LA LOGICA DEL USUARIO
    System.out.println("[START] filterByCategory");
    log.debug("[START] filterByCategory");
    System.out.println("[STOP] filterByCategory");
    log.debug("[STOP] filterByCategory");
    return service.findAllCommentsByCategory(category);
  }

  public List<Comment> commentsByCategoryAndVideoGame(String category, UUID videogame) {
    System.out.println("[START] filterByCategoryAndVideoGame");
    log.debug("[START] filterByCategoryAndVideoGame");
    System.out.println("[STOP] filterByCategoryAndVideoGame");
    log.debug("[STOP] filterByCategoryAndVideoGame");
    return service.findAllCommentsByCategoryAndVideoGame(category, videogame);
  }

}
