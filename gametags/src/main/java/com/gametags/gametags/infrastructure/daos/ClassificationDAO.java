package com.gametags.gametags.infrastructure.daos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "classification")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClassificationDAO {

  @Id
  private UUID id;

  private String system;

  private String country;

  private String tag;

  private String url;
}
