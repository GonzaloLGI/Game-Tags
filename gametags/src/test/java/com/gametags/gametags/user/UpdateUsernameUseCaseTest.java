package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.application.user.update_username.UpdateUsernameUseCase;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.CommentService;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateUsernameUseCaseTest {

  @Mock
  UserService service;

  @Mock
  CommentService commentService;

  @Mock
  VideoGameService videoGameService;

  @InjectMocks
  UpdateUsernameUseCase updateUsernameUseCase;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  JWTGenerator jwtGenerator;

  @Mock
  Principal principal;

  @Mock
  AuthenticationManager authenticationManager;

  String existingPassword;
  String newUsername;
  User existingUser;
  Comment oldUserComment;
  VideoGame oldUserVideogame;
  Comment newUserComment;
  VideoGame newUserVideogame;
  List<VideoGame> userVideogamesList;
  List<Comment> userCommentList;

  @BeforeEach
  public void initialization(){
    existingPassword = "password1";
    newUsername = "username2";
    existingUser = User.builder()
            .password("password1")
            .id(UUID.randomUUID())
            .email("email")
            .roles(List.of())
            .country("country")
            .username("username")
            .build();

    oldUserComment = Comment.builder().uploadUser("username")
            .build();
    oldUserVideogame = VideoGame.builder()
            .comments(List.of(oldUserComment))
            .build();
    newUserComment = Comment.builder().uploadUser(newUsername)
            .build();
    newUserVideogame = VideoGame.builder()
            .uploadUser(newUsername)
            .comments(List.of(newUserComment))
            .build();
    userVideogamesList = List.of(oldUserVideogame);
    userCommentList = List.of(oldUserComment);
  }

  @Test
  public void updateUsername() {
    //GIVEN
    when(principal.getName()).thenReturn("username");
    when(service.findOneUserByUsername(any())).thenReturn(existingUser);
    when(passwordEncoder.matches(any(),any())).thenReturn(true);
    when(jwtGenerator.generateToken(any())).thenReturn("token");
    when(videoGameService.findAllVideoGamesByUser(any())).thenReturn(userVideogamesList);
    when(commentService.findAllCommentsOfUser(any())).thenReturn(userCommentList);
    when(videoGameService.updateVideoGame(any())).thenReturn(newUserVideogame);
    when(commentService.updateComment(any())).thenReturn(newUserComment);

    //WHEN
    AuthResponseDTO returned = updateUsernameUseCase.updateUsername(newUsername,existingPassword,principal);

    //THEN
    assertTrue(!ObjectUtils.isEmpty(returned));
    assertTrue(ObjectUtils.isNotEmpty(returned.getAccessToken()));

  }

  @Test
  public void cantUpdateBecausePasswordDoesntMatch() {
    //GIVEN
    when(principal.getName()).thenReturn("username");
    when(service.findOneUserByUsername(any())).thenReturn(existingUser);
    when(passwordEncoder.matches(any(),any())).thenReturn(false);

    //WHEN
    Exception exception = assertThrows(NotFoundException.class,() -> updateUsernameUseCase.updateUsername(newUsername,existingPassword,principal));

    //THEN
    assertEquals("Password is not correct",exception.getMessage());

  }

}
