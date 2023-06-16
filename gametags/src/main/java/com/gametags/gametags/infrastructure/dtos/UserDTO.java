package com.gametags.gametags.infrastructure.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {

  private UUID id;

  private String username;

  private String email;

  private String password;

  private List<CommentDTO> comments;

  private String country;
}
