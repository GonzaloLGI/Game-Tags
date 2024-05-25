package com.gametags.gametags.application.comment.filter_comments;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterByVideoGameUseCase {

  @Autowired
  private CommentService service;

  @Autowired
  private UserService userService;

  @Autowired
  private VideoGameService videogameService;

  public List<Comment> commentsByVideoGameAndUser(String videogameName) {
    log.info("[START] filterByVideoGame " + videogameName);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findOneUserByUsername(username);
    VideoGame videoGame = videogameService.findVideoGameByName(videogameName);
    if(ObjectUtils.isNotEmpty(user.getId()) && ObjectUtils.isNotEmpty(videoGame.getId())){
      log.info("[STOP] filterByVideoGame " + user.getId());
      return service.findAllCommentsByVideoGameAndUploadUser(videoGame.getName(), user.getUsername());
    }else{
      throw new NoSuchElementException("The user or video game doesn't exist");
    }
  }
}
