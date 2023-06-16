package com.gametags.gametags.infrastructure.mappers;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.application.user.create_user.CreateUserInput;
import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.infrastructure.daos.CommentDAO;
import com.gametags.gametags.infrastructure.daos.UserDAO;
import com.gametags.gametags.infrastructure.dtos.CommentDTO;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Builder
public class UserMapper {

  @Autowired
  private CommentMapper commentMapper;

  public UserDTO fromInputToDto(CreateUserInput input) {
    return UserDTO.builder()
        .id(UUID.randomUUID())
        .username(input.getUsername())
        .comments(input.getComments())
        .country(input.getCountry())
        .email(input.getEmail())
        .password(input.getPassword())
        .build();
  }

  public User fromDtoToDomain(UserDTO dto) {
    return User.builder()
        .id(dto.getId())
        .country(dto.getCountry())
        .comments(dto.getComments() == null? new ArrayList<Comment>():dto.getComments().stream().map(commentDTO -> commentMapper.fromDtoToDomain(commentDTO)).collect(Collectors.toList()))
        .username(dto.getUsername())
        .email(dto.getEmail())
        .password(dto.getPassword())
        .build();
  }

  public UserDTO fromUpdateInputToDto(CreateUserInput input) {
    return UserDTO.builder()
        .id(input.getId())
        .username(input.getUsername())
        .password(input.getPassword())
        .email(input.getEmail())
        .country(input.getCountry())
        .comments(input.getComments())
        .build();
  }

  public UserDTO toDto(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .comments(user.getComments() == null? new ArrayList<CommentDTO>():user.getComments().stream().map(comment -> commentMapper.toDto(comment)).collect(Collectors.toList()))
        .country(user.getCountry())
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  public UserDAO toEntity(User user) {
    return UserDAO.builder()
        .id(user.getId())
        .comments(user.getComments() == null? new ArrayList<CommentDAO>():user.getComments().stream().map(comment -> commentMapper.toEntity(comment)).collect(Collectors.toList()))
        .country(user.getCountry())
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  public User fromEntityToDomain(UserDAO dao) {
    return User.builder()
        .id(dao.getId())
        .country(dao.getCountry())
        .password(dao.getPassword())
        .email(dao.getEmail())
        .username(dao.getUsername())
        .comments(dao.getComments() == null? new ArrayList<Comment>():dao.getComments().stream().map(commentDAO -> commentMapper.fromEntityToDomain(commentDAO)).collect(Collectors.toList()))
        .build();
  }
}
