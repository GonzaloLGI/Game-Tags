package com.gametags.gametags.classification;

import com.gametags.infrastructure.ClassificationDAO;
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
        ClassificationDAO dao = ClassificationDAO.builder()
                .id(UUID.randomUUID())
                .tag("tag")
                .url("url")
                .country("country")
                .system("system")
                .build();

        //WHEN
        ClassificationDAO mapped = mapper.toDomain(mapper.toDto(dao));

        //THEN
        assertEquals(dao.getId(),mapped.getId());
    }

    @Test
    public void mappingWithNonExistingId(){
        //GIVEN
        ClassificationDAO dao = ClassificationDAO.builder()
                .id(null)
                .tag("tag")
                .url("url")
                .country("country")
                .system("system")
                .build();

        //WHEN
        ClassificationDAO mapped = mapper.toDomain(mapper.toDto(dao));

        //THEN
        assertEquals(dao.getId(),mapped.getId());
    }
}
