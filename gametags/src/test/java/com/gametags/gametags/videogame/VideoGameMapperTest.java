package com.gametags.gametags.videogame;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.comment.create_comment.CreateCommentInput;
import com.gametags.gametags.application.videogame.create_videogame.CreateVideoGameInput;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.dtos.VideoGameDTO;
import com.gametags.gametags.infrastructure.mappers.VideoGameMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class VideoGameMapperTest {

  private VideoGameMapper mapper = VideoGameMapper.builder().build();

  @Mock
  Authentication authentication;

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

  @Test
  public void mappingFromUpdateInputToDto() {
    //GIVEN
    CreateVideoGameInput input = CreateVideoGameInput.builder()
            .id(UUID.randomUUID())
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .build();
    when(authentication.getName()).thenReturn("username");
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //WHEN
    VideoGameDTO mapped = mapper.fromUpdateInputToDto(input);

    //THEN
    assertEquals(input.getId(), mapped.getId());
  }

  @Test
  public void mappingFromInputToDto() {
    //GIVEN
    CreateVideoGameInput input = CreateVideoGameInput.builder()
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .build();
    when(authentication.getName()).thenReturn("username");
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //WHEN
    VideoGameDTO mapped = mapper.fromInputToDto(input);

    //THEN
    assertNotNull(mapped.getId());
  }
}