package com.gametags.gametags.application.comment.create_comment;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
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

  public AuthResponseDTO createComment(Comment comment) {
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
      Authentication authentication = new UsernamePasswordAuthenticationToken(actualUser.getUsername(), actualUser.getPassword());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      authenticationManager.authenticate(authentication);
      String token = jwtGenerator.generateToken(authentication);
      service.createComment(comment);
      log.info("[STOP] Creating comment with data: " + comment.getId() + " " + comment.getCategory() + " " + comment.getSeverity());
      return new AuthResponseDTO(token);
    }else{
      throw new NoSuchElementException("User doesn't exist");
    }
  }

}
