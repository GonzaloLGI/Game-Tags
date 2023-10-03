package com.gametags.gametags.application.user.create_user;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateUserInput {

  private UUID id;

  private String username;

  private String email;

  private String password;

  private String country;

  private List<String> roles;
}
