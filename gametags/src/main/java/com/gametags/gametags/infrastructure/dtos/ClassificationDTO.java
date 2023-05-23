package com.gametags.gametags.infrastructure.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
