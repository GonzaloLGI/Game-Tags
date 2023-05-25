package com.gametags.gametags.infrastructure.daos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "videogame")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VideoGameDAO {

  @Id
  private UUID id;

  private String name;

  private String developer;

  private LocalDateTime uploadDateTime;

  private List<String> platforms;
}
