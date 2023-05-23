package com.gametags.gametags.application.classification.create_classification;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateClassificationInput {

  private UUID id;

  private String system;

  private String country;

  private String tag;

  private String url;
}
