package com.gametags.gametags.infrastructure.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import com.gametags.gametags.infrastructure.daos.CommentDAO;
import com.gametags.gametags.infrastructure.daos.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

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

  private List<ClassificationDTO> classifications;

  private String uploadUser;

  private List<CommentDTO> comments;

  private Binary imageData;

}
