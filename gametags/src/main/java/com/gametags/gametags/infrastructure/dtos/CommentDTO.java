package com.gametags.gametags.infrastructure.dtos;

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
public class CommentDTO {

  private UUID id;

  private String text;

  private String category;

  private String severity;

  private LocalDateTime uploadDate;
}
