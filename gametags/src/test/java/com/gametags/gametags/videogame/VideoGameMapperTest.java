package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import com.gametags.gametags.infrastructure.mappers.VideoGameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VideoGameMapperTest {

  private VideoGameMapper mapper = VideoGameMapper.builder().build();

  @Test
  public void mappingWithExistingId() {
    //GIVEN
    VideoGameDTO dto = VideoGameDTO.builder()
        .id(UUID.randomUUID())
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();

    //WHEN
    VideoGameDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingWithNonExistingId() {
    //GIVEN
    VideoGameDTO dto = VideoGameDTO.builder()
        .id(null)
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();

    //WHEN
    VideoGameDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

}