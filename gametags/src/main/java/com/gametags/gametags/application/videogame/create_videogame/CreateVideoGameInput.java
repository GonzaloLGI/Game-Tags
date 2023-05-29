package com.gametags.gametags.application.videogame.create_videogame;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateVideoGameInput {

  private UUID id;

  private String name;

  private String developer;

  private LocalDateTime uploadDateTime;

  private List<String> platforms;
}
