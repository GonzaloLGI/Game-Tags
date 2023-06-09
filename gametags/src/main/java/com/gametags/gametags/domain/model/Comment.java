package com.gametags.gametags.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  private UUID uploadUser;

  private UUID videogame;
}
