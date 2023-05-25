package com.gametags.gametags.infrastructure.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VideoGameDTO {

  private UUID id;

  private String name;

  private String developer;

  private LocalDateTime uploadDateTime;

  private List<String> platforms;
}
