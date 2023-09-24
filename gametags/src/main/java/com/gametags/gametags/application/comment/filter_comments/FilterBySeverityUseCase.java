package com.gametags.gametags.application.comment.filter_comments;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterBySeverityUseCase {

  @Autowired
  private CommentService service;

  @Autowired
  private UserService userService;

  public List<Comment> commentsBySeverityAndUser(String severity) {
    log.info("[START] filterBySeverity");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findOneUserByUsername(username);
    if(ObjectUtils.isNotEmpty(user)){
      log.info("[STOP] filterBySeverity");
      return service.findAllCommentsBySeverityAndUploadUser(severity, user.getId());
    }else{
      throw new NoSuchElementException("The user doesn't exist");
    }
  }

  public List<Comment> commentsBySeverityAndVideoGame(String severity, UUID videogame) {
    log.info("[START] filterBySeverityAndVideoGame");
    log.info("[STOP] filterBySeverityAndVideoGame");
    return service.findAllCommentsBySeverityAndVideoGame(severity, videogame);
  }
}
