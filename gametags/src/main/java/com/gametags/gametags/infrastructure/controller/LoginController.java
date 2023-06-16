package com.gametags.gametags.infrastructure.controller;

import com.gametags.gametags.application.authentication.RegisterDTO;
import com.gametags.gametags.application.authentication.RegisterUserUseCase;
import com.gametags.gametags.infrastructure.mappers.AuthenticationMapper;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
import com.gametags.gametags.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RegisterUserUseCase registerUserUseCase;

  @Autowired
  private AuthenticationMapper authenticationMapper;

  @Autowired
  private UserMapper userMapper;

  //  @PostMapping("/login")
  //  public ResponseEntity<UserDTO> loginUser() {
  //
  //  }

  @PostMapping("/register")
  public ResponseEntity<Object> registerUser(@RequestBody RegisterDTO dto) {
    try {
      return new ResponseEntity<>(userMapper.toDto(registerUserUseCase.registerUser(authenticationMapper.fromRegisterDtoToInput(dto)))
          , HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
