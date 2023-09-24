package com.gametags.gametags.application.comment.create_comment;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  public Comment createComment(Comment comment) {
    log.info("[START] Creating comment with data: " + comment.getId() + " " + comment.getCategory() + " " + comment.getSeverity());
    User actualUser = userService.findOneUserById(comment.getUploadUser());
    if(ObjectUtils.isNotEmpty(actualUser)){
      List<Comment> oldComments = actualUser.getComments();
      oldComments.add(comment);
      User updatedUser = User.builder()
              .id(actualUser.getId())
              .roles(actualUser.getRoles())
              .email(actualUser.getEmail())
              .country(actualUser.getCountry())
              .password(actualUser.getPassword())
              .username(actualUser.getUsername())
              .comments(oldComments)
              .build();
      userService.updateUser(updatedUser);
      log.info("[STOP] Creating comment with data: " + comment.getId() + " " + comment.getCategory() + " " + comment.getSeverity());
      return service.createComment(comment);
    }else{
      throw new NoSuchElementException("User doesn't exist");
    }
  }

}
