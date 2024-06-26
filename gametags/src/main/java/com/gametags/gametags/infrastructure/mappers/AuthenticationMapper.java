package com.gametags.gametags.infrastructure.mappers;

import com.gametags.gametags.infrastructure.dtos.LoginDTO;
import com.gametags.gametags.infrastructure.dtos.RegisterDTO;
import com.gametags.gametags.domain.model.LoginInput;
import com.gametags.gametags.domain.model.RegisterInput;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class AuthenticationMapper {

  @Autowired
  private PasswordEncoder passwordEncoder;

  public RegisterInput fromRegisterDtoToInput(RegisterDTO dto) {
    return RegisterInput.builder()
        .userName(dto.getUsername())
        .password(passwordEncoder.encode(dto.getPassword()))
        .country(dto.getCountry())
            .email(dto.getEmail())
        .build();
  }

  public LoginInput fromLoginDtoToInput(LoginDTO dto) {
    return LoginInput.builder()
        .userName(dto.getUsername())
        .password(dto.getPassword())
        .build();
  }
}
