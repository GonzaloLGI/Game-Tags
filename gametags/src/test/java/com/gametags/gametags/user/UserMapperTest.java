package com.gametags.gametags.user;

import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private UserMapper mapper = UserMapper.builder().build();

    @Test
    public void mappingWithExistingId(){
        //GIVEN
        UserDTO dto = UserDTO.builder()
                .id(UUID.randomUUID())
                .username("username")
                .email("email")
                .password("password")
                .country("country")
                .comments(List.of("comment1","comment2"))
                .build();

        //WHEN
        UserDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

        //THEN
        assertEquals(dto.getId(),mapped.getId());
    }

    @Test
    public void mappingWithNonExistingId(){
        //GIVEN
        UserDTO dto = UserDTO.builder()
                .id(null)
                .username("username")
                .email("email")
                .password("password")
                .country("country")
                .comments(List.of("comment1","comment2"))
                .build();

        //WHEN
        UserDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

        //THEN
        assertEquals(dto.getId(),mapped.getId());
    }
}
