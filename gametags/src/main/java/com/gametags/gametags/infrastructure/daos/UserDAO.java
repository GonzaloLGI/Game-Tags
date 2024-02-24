package com.gametags.gametags.infrastructure.daos;

import java.util.List;
import java.util.UUID;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDAO {

  @Id
  private UUID id;

  private String username;

  private String email;

  private String password;

  private String country;

  private List<String> roles;

  @Setter
  private Binary profileImageData;
}
