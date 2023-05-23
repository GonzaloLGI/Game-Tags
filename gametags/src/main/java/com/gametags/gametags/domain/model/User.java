package com.gametags.gametags.domain.model;

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
public class User {

  private UUID id;

  private String username;

  private String email;

  private String password;

  private List<String> comments;

  private String country;
}
