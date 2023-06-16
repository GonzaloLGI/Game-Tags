package com.gametags.gametags.application.comment.create_comment;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateCommentInput {

  private UUID id;

  private String text;

  private String category;

  private String severity;

  private UUID uploadUser;

  private UUID videogame;
}
