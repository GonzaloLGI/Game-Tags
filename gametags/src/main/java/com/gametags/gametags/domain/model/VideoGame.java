package com.gametags.gametags.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.bson.types.Binary;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VideoGame {

  private UUID id;

  private String name;

  private String developer;

  private LocalDateTime uploadDateTime;

  private List<String> platforms;

  private List<Classification> classifications;

  private String uploadUser;

  private List<Comment> comments;

  @Setter
  private Binary imageData;
}
