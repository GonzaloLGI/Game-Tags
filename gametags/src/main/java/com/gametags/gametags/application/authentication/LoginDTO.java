package com.gametags.gametags.application.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginDTO {

  private String username;

  private String password;
}
