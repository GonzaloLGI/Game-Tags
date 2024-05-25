package com.gametags.gametags.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterDTO {

  private String username;

  private String password;

  private String country;

  private String email;
}
