package com.gametags.gametags.domain.model;

import java.util.UUID;

import lombok.*;
import org.bson.types.Binary;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Classification {

  private UUID id;

  private String system;

  private String country;

  private String tag;

  private String url;

  @Setter
  private Binary imageData;
}
