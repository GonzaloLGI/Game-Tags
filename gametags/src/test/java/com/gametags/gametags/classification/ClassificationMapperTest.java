package com.gametags.gametags.classification;

import com.gametags.domain.Classification;
import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.ClassificationDTO;
import com.gametags.infrastructure.mappers.ClassificationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClassificationMapperTest {

    private ClassificationMapper mapper = new ClassificationMapper();

    @Test
    public void mappingWithExistingId(){
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
        assertEquals(dto.getId(),mapped.getId());
    }

    @Test
    public void mappingWithNonExistingId(){
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
        assertEquals(dto.getId(),mapped.getId());
    }
}
