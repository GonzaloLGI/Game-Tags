package com.gametags.gametags.infrastructure.dtos;

import java.util.UUID;

import lombok.*;
import org.bson.types.Binary;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClassificationDTO {

  private UUID id;

  private String system;

  private String country;

  private String tag;

  private String url;

  private Binary imageData;
}
