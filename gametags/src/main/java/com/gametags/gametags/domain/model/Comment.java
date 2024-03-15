package com.gametags.gametags.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {

  private UUID id;

  private String text;

  private String category;

  private String severity;

  private LocalDateTime uploadDate;

  @Setter
  private String uploadUser;

  private String videogame;
}
