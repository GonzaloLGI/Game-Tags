package com.gametags.gametags.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClassificationMapperTest {

  private ClassificationMapper mapper = ClassificationMapper.builder().build();

  @Test
  public void mappingWithExistingId() {
    //GIVEN
    ClassificationDTO dto = ClassificationDTO.builder()
        .id(UUID.randomUUID())
        .tag("tag")
        .url("url")
        .country("country")
        .system("system")
        .build();

    //WHEN
    ClassificationDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingWithNonExistingId() {
    //GIVEN
    ClassificationDTO dto = ClassificationDTO.builder()
        .id(null)
        .tag("tag")
        .url("url")
        .country("country")
        .system("system")
        .build();

    //WHEN
    ClassificationDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingFromUpdateInputToDto() {
    //GIVEN
    CreateClassificationInput input = CreateClassificationInput.builder()
            .id(UUID.randomUUID())
            .tag("tag")
            .url("url")
            .country("country")
            .system("system")
            .build();

    //WHEN
    ClassificationDTO mapped = mapper.fromUpdateInputToDto(input);

    //THEN
    assertEquals(input.getId(), mapped.getId());
  }

  @Test
  public void mappingFromInputToDto() {
    //GIVEN
    CreateClassificationInput input = CreateClassificationInput.builder()
            .tag("tag")
            .url("url")
            .country("country")
            .system("system")
            .build();

    //WHEN
    ClassificationDTO mapped = mapper.fromInputToDto(input);

    //THEN
    assertNotNull(mapped.getId());
  }
}
