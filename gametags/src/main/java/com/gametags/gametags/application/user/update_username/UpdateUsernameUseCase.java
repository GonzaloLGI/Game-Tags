package com.gametags.gametags.application.user.update_username;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@Slf4j
public class UpdateUsernameUseCase {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService service;

  @Autowired
  private CommentService commentService;

  @Autowired
  private VideoGameService videoGameService;

  public AuthResponseDTO updateUsername(String newUsername, String existingPassword, Principal principal) {
    String username = principal.getName();
    log.info("EXISTING USERNAME: " + username);
    User existingUser = service.findOneUserByUsername(username);
    log.info("EXISTING PASSWORD: " + existingPassword);
    log.info("USER SAVED PASSWORD: " + existingUser.getPassword());
    User changedUser = User.builder()
            .id(existingUser.getId())
            .roles(existingUser.getRoles())
            .email(existingUser.getEmail())
            .country(existingUser.getCountry())
            .username(newUsername)
            .password(existingUser.getPassword())
            .profileImageData(existingUser.getProfileImageData())
            .build();
    if(passwordEncoder.matches(existingPassword,existingUser.getPassword())){
      log.info("New username: " + newUsername);
      updateUsernameInOtherObjects(newUsername,username);
      service.updateUser(changedUser);
      Authentication authentication = new UsernamePasswordAuthenticationToken(changedUser.getUsername(), existingPassword);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      authenticationManager.authenticate(authentication);
      String token = jwtGenerator.generateToken(authentication);
      return new AuthResponseDTO(token, changedUser.getUsername());
    }else{
      throw new NotFoundException("Password is not correct");
    }
  }

  private void updateUsernameInOtherObjects(String newUsername, String oldUsername){
    List<VideoGame> userVideogames = videoGameService.findAllVideoGamesByUser(oldUsername);
    userVideogames.forEach(videoGame -> {
      videoGame.setUploadUser(newUsername);
      videoGame.getComments().stream().filter(comment -> StringUtils.equals(comment.getUploadUser(),oldUsername))
              .forEach(comment -> comment.setUploadUser(newUsername));
      videoGameService.updateVideoGame(videoGame);
    });
    List<Comment> userComments = commentService.findAllCommentsOfUser(oldUsername);
    List<Comment> updatedComments = userComments.stream().peek(comment -> comment.setUploadUser(newUsername)).collect(Collectors.toList());
    updatedComments.forEach(comment -> commentService.updateComment(comment));
  }
}
