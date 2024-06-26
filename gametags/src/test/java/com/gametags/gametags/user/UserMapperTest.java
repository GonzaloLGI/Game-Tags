package com.gametags.gametags.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.comment.create_comment.CreateCommentInput;
import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import com.gametags.gametags.infrastructure.mappers.CommentMapper;
import com.gametags.gametags.infrastructure.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {


  private UserMapper mapper = UserMapper.builder().build();

  @Test
  public void mappingWithExistingId() {
    //GIVEN
    UserDTO dto = UserDTO.builder()
        .id(UUID.randomUUID())
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .build();


    //WHEN
    UserDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingWithNonExistingId() {
    //GIVEN
    UserDTO dto = UserDTO.builder()
        .id(null)
        .username("username")
        .email("email")
        .password("password")
        .country("country")
        .build();

    //WHEN
    UserDTO mapped = mapper.toDto(mapper.fromEntityToDomain(mapper.toEntity(mapper.fromDtoToDomain(dto))));

    //THEN
    assertEquals(dto.getId(), mapped.getId());
  }

  @Test
  public void mappingFromUpdateInputToDto() {
    //GIVEN
    CreateUserInput input = CreateUserInput.builder()
            .id(UUID.randomUUID())
            .username("username")
            .email("email")
            .password("password")
            .country("country")
            .build();

    //WHEN
    UserDTO mapped = mapper.fromUpdateInputToDto(input);

    //THEN
    assertEquals(input.getId(), mapped.getId());
  }

  @Test
  public void mappingFromInputToDto() {
    //GIVEN
    CreateUserInput input = CreateUserInput.builder()
            .username("username")
            .email("email")
            .password("password")
            .country("country")
            .build();

    //WHEN
    UserDTO mapped = mapper.fromInputToDto(input);

    //THEN
    assertNotNull(mapped.getId());
  }
}
