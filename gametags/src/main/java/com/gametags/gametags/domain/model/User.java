package com.gametags.gametags.domain.model;

import java.util.List;
import java.util.UUID;

import lombok.*;
import org.bson.types.Binary;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

  private UUID id;

  private String username;

  private String email;

  private String password;

  private String country;

  private List<String> roles;

  @Setter
  private Binary profileImageData;
}
