package com.gametags.gametags.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
