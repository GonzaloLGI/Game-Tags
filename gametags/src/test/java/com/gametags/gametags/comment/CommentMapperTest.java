package com.gametags.gametags.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.application.comment.create_comment.CreateCommentInput;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.mappers.CommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentMapperTest {

  private CommentMapper mapper = CommentMapper.builder().build();

  @Test
  public void mappingWithExistingId() {
    //GIVEN
    CommentDTO dto = CommentDTO.builder()
        .id(UUID.randomUUID())
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();

    //WHEN
    CommentDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingWithNonExistingId() {
    //GIVEN
    CommentDTO dto = CommentDTO.builder()
        .id(null)
        .text("text")
        .category("category")
        .severity("severity")
        .uploadDate(LocalDateTime.now())
        .build();

    //WHEN
    CommentDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingFromUpdateInputToDto() {
    //GIVEN
    CreateCommentInput input = CreateCommentInput.builder()
            .id(UUID.randomUUID())
            .text("text")
            .category("category")
            .severity("severity")
            .build();

    //WHEN
    CommentDTO mapped = mapper.fromUpdateInputToDto(input);

    //THEN
    assertEquals(input.getId(), mapped.getId());
  }

  @Test
  public void mappingFromInputToDto() {
    //GIVEN
    CreateCommentInput input = CreateCommentInput.builder()
            .text("text")
            .category("category")
            .severity("severity")
            .build();

    //WHEN
    CommentDTO mapped = mapper.fromInputToDto(input);

    //THEN
    assertNotNull(mapped.getId());
  }
}
