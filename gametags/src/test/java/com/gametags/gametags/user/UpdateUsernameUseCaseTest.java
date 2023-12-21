package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.application.user.update_username.UpdateUsernameUseCase;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UpdateUsernameUseCaseTest {

  @Mock
  UserService service;

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

  @Test
  public void updateUsername() {
    //GIVEN
    final String existingPassword = "password1";
    final String newUsername = "username2";
    User existingUser = User.builder()
        .password("password1")
        .id(UUID.randomUUID())
        .email("email")
        .roles(List.of())
        .country("country")
        .username("username")
        .build();

    when(principal.getName()).thenReturn("username");
    when(service.findOneUserByUsername(any())).thenReturn(existingUser);
    when(passwordEncoder.matches(any(),any())).thenReturn(true);
    when(jwtGenerator.generateToken(any())).thenReturn("token");
    //WHEN
    AuthResponseDTO returned = updateUsernameUseCase.updateUsername(newUsername,existingPassword,principal);
    //THEN
    assertTrue(!ObjectUtils.isEmpty(returned));
    assertTrue(ObjectUtils.isNotEmpty(returned.getAccessToken()));

  }

}
