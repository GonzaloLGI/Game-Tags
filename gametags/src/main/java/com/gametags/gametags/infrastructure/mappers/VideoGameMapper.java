package com.gametags.gametags.infrastructure.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.videogame.create_videogame.CreateVideoGameInput;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import com.gametags.gametags.infrastructure.daos.CommentDAO;
import com.gametags.gametags.infrastructure.daos.VideoGameDAO;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Builder
public class VideoGameMapper {

  @Autowired
  private ClassificationMapper classificationMapper;

  @Autowired
  private CommentMapper commentMapper;

  public VideoGameDTO fromInputToDto(CreateVideoGameInput input) {
    return VideoGameDTO.builder()
        .id(UUID.randomUUID())
        .name(input.getName())
        .developer(input.getDeveloper())
        .platforms(input.getPlatforms())
        .uploadDateTime(LocalDateTime.now())
        .classifications(input.getClassifications())
        .uploadUser(input.getUploadUser())
        .comments(input.getComments()==null? new ArrayList<CommentDTO>():input.getComments())
        .build();
  }

  public VideoGame fromDtoToDomain(VideoGameDTO dto) {
    return VideoGame.builder()
        .id(dto.getId())
        .developer(dto.getDeveloper())
        .name(dto.getName())
        .platforms(dto.getPlatforms())
        .uploadDateTime(dto.getUploadDateTime())
        .classifications(dto.getClassifications() == null? new ArrayList<Classification>():
            dto.getClassifications().stream().map(classificationDTO -> classificationMapper.fromDtoToDomain(classificationDTO)).collect(Collectors.toList()))
        .uploadUser(dto.getUploadUser())
        .comments(dto.getComments() == null? new ArrayList<Comment>():
            dto.getComments().stream().map(commentDTO -> commentMapper.fromDtoToDomain(commentDTO)).collect(Collectors.toList()))
        .build();
  }

  public VideoGameDTO fromUpdateInputToDto(CreateVideoGameInput input) {
    return VideoGameDTO.builder()
        .id(input.getId())
        .name(input.getName())
        .developer(input.getDeveloper())
        .platforms(input.getPlatforms())
        .uploadDateTime(LocalDateTime.now())
        .classifications(input.getClassifications())
        .uploadUser(input.getUploadUser())
        .comments(input.getComments()==null? new ArrayList<CommentDTO>():input.getComments())
        .build();
  }

  public VideoGameDTO toDto(VideoGame videogame) {
    return VideoGameDTO.builder()
        .id(videogame.getId())
        .developer(videogame.getDeveloper())
        .name(videogame.getName())
        .platforms(videogame.getPlatforms())
        .uploadDateTime(videogame.getUploadDateTime())
        .classifications(videogame.getClassifications() == null? new ArrayList<ClassificationDTO>():
            videogame.getClassifications().stream().map(classification -> classificationMapper.toDto(classification)).collect(Collectors.toList()))
        .uploadUser(videogame.getUploadUser())
        .comments(videogame.getComments() == null? new ArrayList<CommentDTO>():
            videogame.getComments().stream().map(comment -> commentMapper.toDto(comment)).collect(Collectors.toList()))
        .build();
  }

  public VideoGameDAO toEntity(VideoGame videogame) {
    return VideoGameDAO.builder()
        .id(videogame.getId())
        .developer(videogame.getDeveloper())
        .name(videogame.getName())
        .platforms(videogame.getPlatforms())
        .uploadDateTime(videogame.getUploadDateTime())
        .classifications(videogame.getClassifications() == null? new ArrayList<ClassificationDAO>():
            videogame.getClassifications().stream().map(classificationDAO -> classificationMapper.toEntity(classificationDAO)).collect(Collectors.toList()))
        .uploadUser(videogame.getUploadUser())
        .comments(videogame.getComments() == null? new ArrayList<CommentDAO>():
            videogame.getComments().stream().map(commentDAO -> commentMapper.toEntity(commentDAO)).collect(Collectors.toList()))
        .build();
  }

  public VideoGame fromEntityToDomain(VideoGameDAO dao) {
    return VideoGame.builder()
        .id(dao.getId())
        .name(dao.getName())
        .developer(dao.getDeveloper())
        .uploadDateTime(dao.getUploadDateTime())
        .platforms(dao.getPlatforms())
        .classifications(dao.getClassifications() == null? new ArrayList<Classification>():
            dao.getClassifications().stream().map(classificationDAO -> classificationMapper.fromEntityToDomain(classificationDAO)).collect(Collectors.toList()))
        .uploadUser(dao.getUploadUser())
        .comments(dao.getComments() == null? new ArrayList<Comment>():
            dao.getComments().stream().map(commentDAO -> commentMapper.fromEntityToDomain(commentDAO)).collect(Collectors.toList()))
        .build();
  }
}
