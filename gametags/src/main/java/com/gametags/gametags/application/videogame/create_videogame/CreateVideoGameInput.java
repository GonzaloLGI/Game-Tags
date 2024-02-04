package com.gametags.gametags.application.videogame.create_videogame;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

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

  private List<ClassificationDTO> classifications;

  private String uploadUser;

  private List<CommentDTO> comments;
}
