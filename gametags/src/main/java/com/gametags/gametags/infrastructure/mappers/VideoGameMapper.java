package com.gametags.gametags.infrastructure.mappers;

import java.util.UUID;

import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.infrastructure.daos.UserDAO;
import com.gametags.gametags.infrastructure.daos.VideoGameDAO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class VideoGameMapper {
  public VideoGameDTO fromInputToDto(CreateVideoGameInput input) {
    return VideoGameDTO.builder()
        .id(UUID.randomUUID())
        .name(input.getName())
        .developer(input.getDeveloper())
        .platforms(input.getPlatforms())
        .uploadDateTime(input.getUploadDateTime())
        .build();
  }

  public VideoGame fromDtoToDomain(VideoGameDTO dto) {
    return VideoGame.builder()
        .id(dto.getId())
        .developer(dto.getDeveloper())
        .name(dto.getName())
        .platforms(dto.getPlatforms())
        .uploadDateTime(dto.getUploadDateTime())
        .build();
  }

  public VideoGameDTO fromUpdateInputToDto(CreateVideoGameInput input) {
    return VideoGameDTO.builder()
        .id(input.getId())
        .name(input.getName())
        .developer(input.getDeveloper())
        .platforms(input.getPlatforms())
        .uploadDateTime(input.getUploadDateTime())
        .build();
  }

  public VideoGameDTO toDto(VideoGame videogame) {
    return VideoGameDTO.builder()
        .id(videogame.getId())
        .developer(videogame.getDeveloper())
        .name(videogame.getName())
        .platforms(videogame.getPlatforms())
        .uploadDateTime(videogame.getUploadDateTime())
        .build();
  }

  public VideoGameDAO toEntity(VideoGame videogame) {
    return VideoGameDAO.builder()
        .id(videogame.getId())
        .developer(videogame.getDeveloper())
        .name(videogame.getName())
        .platforms(videogame.getPlatforms())
        .uploadDateTime(videogame.getUploadDateTime())
        .build();
  }

  public VideoGame fromEntityToDomain(VideoGameDAO dao) {
    return VideoGame.builder()
        .id(dao.getId())
        .name(dao.getName())
        .developer(dao.getDeveloper())
        .uploadDateTime(dao.getUploadDateTime())
        .platforms(dao.getPlatforms())
        .build();
  }
}
