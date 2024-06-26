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
public class FilterByCategoryUseCase {

  @Autowired
  private CommentService service;

  @Autowired
  private UserService userService;

  public List<Comment> commentsByCategoryAndUser(String category) {
    category = category.substring(1, category.length() - 1);
    log.info("[START] filterByCategory " + category);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findOneUserByUsername(username);
    if(ObjectUtils.isNotEmpty(user.getId())){
      log.info("[STOP] filterByCategory " + user.getId());
      return service.findAllCommentsByCategoryAndUploadUser(category, user.getUsername());
    }else{
      throw new NoSuchElementException("The user doesn't exist");
    }
  }

  public List<Comment> commentsByCategoryAndVideoGame(String category, String videogame) {
    log.info("[START] filterByCategoryAndVideoGame");
    log.info("[STOP] filterByCategoryAndVideoGame");
    return service.findAllCommentsByCategoryAndVideoGame(category, videogame);
  }

}
