package com.gametags.gametags.application.comment.create_comment;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class CreateCommentUseCase {

  @Autowired
  private CommentService service;

  @Autowired
  private UserService userService;

  @Autowired
  private VideoGameService videogameService;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  public Comment createComment(Comment comment) {
    log.info("[START] Creating comment with data: " + comment.getId() + " " + comment.getCategory() + " " + comment.getSeverity());
    User actualUser = userService.findOneUserById(comment.getUploadUser());
    VideoGame toUpdate = videogameService.findOneVideoGame(comment.getVideogame());
    if(ObjectUtils.isNotEmpty(actualUser) && ObjectUtils.isNotEmpty(toUpdate)){
      List<Comment> updatedList = new ArrayList<>(toUpdate.getComments());
      Comment createdComment = service.createComment(comment);
      updatedList.add(createdComment);
      toUpdate.getComments().removeAll(toUpdate.getComments());
      log.info(""+updatedList.size());
      toUpdate.getComments().addAll(updatedList);
      log.info(""+toUpdate.getComments().size());
      videogameService.updateVideoGame(toUpdate);
      log.info("[STOP] Creating comment with data: " + createdComment.getId() + " " + createdComment.getCategory() + " " + createdComment.getSeverity());
      return createdComment;
    }else{
      throw new NoSuchElementException("User doesn't exist");
    }
  }

}
