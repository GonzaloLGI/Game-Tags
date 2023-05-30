package com.gametags.gametags.infrastructure.daos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDAO {

  @Id
  private UUID id;

  private String text;

  private String category;

  private String severity;

  private LocalDateTime uploadDate;
}
