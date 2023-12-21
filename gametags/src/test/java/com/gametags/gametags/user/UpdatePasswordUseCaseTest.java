package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.user.update_password.UpdatePasswordInput;
import com.gametags.gametags.application.user.update_password.UpdatePasswordUseCase;
import com.gametags.gametags.domain.model.User;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UpdatePasswordUseCaseTest {

  @Mock
  UserService service;

  @InjectMocks
  UpdatePasswordUseCase updatePasswordUseCase;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  JWTGenerator jwtGenerator;

  @Mock
  Authentication authentication;

  @Mock
  AuthenticationManager authenticationManager;

  @BeforeEach
  void initSecurityContextHolder(){
    when(authentication.getName()).thenReturn("username");
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  public void updatePassword() {
    //GIVEN
    UpdatePasswordInput input = UpdatePasswordInput.builder()
        .existingPassword("password1")
        .newPassword("password2")
        .build();
    User existingUser = User.builder()
        .password("password1")
        .id(UUID.randomUUID())
        .email("email")
        .roles(List.of())
        .country("country")
        .username("username")
        .build();

    when(service.findOneUserByUsername(any())).thenReturn(existingUser);
    when(passwordEncoder.encode(any())).thenReturn("encodedpassword2");
    when(passwordEncoder.matches(any(),any())).thenReturn(true);
    when(jwtGenerator.generateToken(any())).thenReturn("token");
    //WHEN
    AuthResponseDTO returned = updatePasswordUseCase.updatePassword(input);
    //THEN
    assertTrue(!ObjectUtils.isEmpty(returned));
    assertTrue(ObjectUtils.isNotEmpty(returned.getAccessToken()));
  }

}
